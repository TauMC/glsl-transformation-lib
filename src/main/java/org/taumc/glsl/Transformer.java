package org.taumc.glsl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Transformer {

    private final GLSLParser.Translation_unitContext root;

    public Transformer(GLSLParser.Translation_unitContext root) {
        this.root = root;
    }

    /**
     * Injects at the end of the "external_declaration_list", before functions
     * @param code the to inject code
     */
    public void injectVariable(String code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.external_declaration();

        AtomicReference<GLSLParser.External_declarationContext> ref = new AtomicReference<>();
        ParseTreeWalker.DEFAULT.walk(new VariableInjector(ref), root);

        var left = ref.get();
        if (left != null) {
            var parent = left.getParent();
            int i = parent.children.indexOf(left);
            parent.children.add(i, insert);
            insert.setParent(parent);
        }
    }

    public void injectFunction(String code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.external_declaration();

        AtomicReference<GLSLParser.External_declarationContext> ref = new AtomicReference<>();
        ParseTreeWalker.DEFAULT.walk(new FunctionInjector(ref), root);

        var left = ref.get();
        if (left != null) {
            var parent = left.getParent();
            int i = parent.children.indexOf(left);
            parent.children.add(i, insert);
            insert.setParent(parent);
        }
    }

    public void rename(String oldName, String newName) {
        rename(Collections.singletonMap(oldName, newName));
    }

    public void rename(Map<String, String> names) {
        ParseTreeWalker.DEFAULT.walk(new Renamer(names), root);
    }

    public void replaceExpression(String oldCode, String newCode) {
        ParseTreeWalker.DEFAULT.walk(new ReplaceExpression(oldCode, newCode), root);
    }

    public void prependMain(String code) {
        ParseTreeWalker.DEFAULT.walk(new PrependFunction("main", code), root);
    }

    public void removeVariable(String code) {
        AtomicReference<ParserRuleContext> top = new AtomicReference<>();
        ParseTreeWalker.DEFAULT.walk(new RemoveVariable(code, top), root);
        if (top.get() != null) {
            if (top.get().getParent() instanceof GLSLParser.Init_declarator_listContext listContext) {
                int i = listContext.children.indexOf(top.get());
                listContext.children.remove(i-1);
                listContext.children.remove(i-1);
            } else if (top.get().parent instanceof GLSLParser.Single_declarationContext singleContext) {
                singleContext.getParent().getParent().getParent().getParent().children.remove(singleContext.getParent().getParent().getParent());
            }
        }
    }

    public int findType(String code) {
        AtomicInteger type = new AtomicInteger();
        FastTreeWalker.walk(new TypeFinder(code, type), root);
        return type.get();
    }

    public void appendMain(String code) {
        ParseTreeWalker.DEFAULT.walk(new AppendFunction("main", code), root);
    }

    public boolean containsCall(String name) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        FastTreeWalker.walk(new IdentifierCollector(id -> {
            if(id.equals(name)) {
                atomicBoolean.set(true);
                return false;
            } else {
                return true;
            }
        }), root);
        return atomicBoolean.get();
    }

    public boolean hasVariable(String name) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParseTreeWalker.DEFAULT.walk(new HasVariable(name, atomicBoolean), root);
        return atomicBoolean.get();
    }

    public void renameArray(String oldName, String newName, Set<Integer> found) {
        ParseTreeWalker.DEFAULT.walk(new ArrayExpressionRewriteListener(Collections.singletonMap(oldName, newName), found), root);
    }

    /**
     * Mutate the underlying tree directly. All caches will be invalidated after your mutations, so this
     * should only be used in cases where no better transform is built-in.
     * @param contextConsumer a consumer that accepts the tree
     */
    public void mutateTree(Consumer<GLSLParser.Translation_unitContext> contextConsumer) {
        contextConsumer.accept(root);
    }

    public void renameFunctionCall(String oldName, String newName) {
        renameFunctionCall(Collections.singletonMap(oldName, newName));
    }

    public void renameFunctionCall(Map<String, String> names) {
        ParseTreeWalker.DEFAULT.walk(new ExpressionRenamer(names), root);
    }

    public void renameAndWrapShadow(String oldName, String newName) {
        ParseTreeWalker.DEFAULT.walk( new FunctionCallWrapper(oldName, "vec4"), root);
        renameFunctionCall(oldName, newName);
    }

    public List<String> collectFunctions(GLSLParser.Translation_unitContext root) {
        List<String> result = new ArrayList<>();
        ParseTreeWalker.DEFAULT.walk(new FunctionCollector(result), root);
        return result;
    }

    public void removeFunction(String name) {
        AtomicReference<GLSLParser.External_declarationContext> context = new AtomicReference<>();
        FastTreeWalker.walk(new FunctionRemover(name, context), root);
        if (context.get() != null) {
            context.get().getParent().children.remove(context.get());
        }
    }

    public void removeUnusedFunctions(GLSLParser.Translation_unitContext root) {
        List<String> result = collectFunctions(root);
        Set<String> usedIdentifiers = new HashSet<>();
        FastTreeWalker.walk(new IdentifierCollector(id -> {
            usedIdentifiers.add(id);
            return true;
        }), root);
        List<String> functionsToRemove = result.stream().filter(name -> !usedIdentifiers.contains(name) && !name.equals("main")).collect(Collectors.toList());
        // TODO - remove all the functions in one walk of the tree
        for (String name : functionsToRemove) {
            removeFunction(name);
        }
    }

    public Map<String, List<String>> findConstParameter(GLSLParser.Translation_unitContext root) {
        Map<String, List<String>> functions = new HashMap<>();
        ParseTreeWalker.DEFAULT.walk(new ConstParameterFinder(functions), root);
        return functions;
    }

    public void removeConstAssignment(Map<String, List<String>> functions) {
        for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
            ParseTreeWalker.DEFAULT.walk(new ConstAssignmentRemover(entry.getKey(), entry.getValue()), root);
        }
    }

    public void removeConstAssignment(GLSLParser.Translation_unitContext root) {
        Map<String, List<String>> functions = findConstParameter(root);
        removeConstAssignment(functions);
    }

    public void initialize(GLSLParser.Single_declarationContext declarationContext, String name) {
        if (declarationContext.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node && node.getSymbol() instanceof CommonToken token) {
            String insert = token.getText() + " " + name + " = " + BuiltinFunction.getByType(token.getType()).getInitializer() + ";" ;
            prependMain(insert);
        }
    }

    public void makeOutDeclaration(GLSLParser.Single_declarationContext inDeclarationContext, String name) {
        String insert = Main.getFormattedShader(inDeclarationContext.fully_specified_type()) + name + ";"; //TODO, find a different way to make the out declaration
        insert = insert.replaceFirst("in", "out");
        injectVariable(insert);
    }

    public Map<String, GLSLParser.Single_declarationContext> findQualifiers(int type) {
        Map<String, GLSLParser.Single_declarationContext> result = new HashMap<>();
        ParseTreeWalker.DEFAULT.walk(new QualifierFinder(type, result), root);
        return result;
    }

    public boolean hasAssigment(String name) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParseTreeWalker.DEFAULT.walk(new AssigmentChecker(name, atomicBoolean), root);
        return atomicBoolean.get();
    }

    public void rewriteStructArrays(GLSLParser.Translation_unitContext root) {
        ParseTreeWalker.DEFAULT.walk(new StructArrayRewriter(), root);
    }

}
