package org.taumc.glsl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Util {

    /**
     * Injects at the end of the "external_declaration_list", before functions
     * @param root the root parser
     * @param code the to inject code
     */
    public static void injectVariable(GLSLParser.Translation_unitContext root, String... code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(String.join("\n", code)));
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

    public static void injectFunction(GLSLParser.Translation_unitContext root, String... code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(String.join("\n", code)));
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

    public static void rename(GLSLParser.Translation_unitContext root, String oldName, String newName) {
        ParseTreeWalker.DEFAULT.walk(new Renamer(Map.of(oldName, newName)), root);
    }

    public static void replaceExpression(GLSLParser.Translation_unitContext root, String oldCode, String newCode) {
        ParseTreeWalker.DEFAULT.walk(new ReplaceExpression(oldCode, newCode), root);
    }

    public static void prependMain(GLSLParser.Translation_unitContext root, String code) {
        ParseTreeWalker.DEFAULT.walk(new PrependFunction("main", code), root);
    }

    public static void removeVariable(GLSLParser.Translation_unitContext root, String code) {
        AtomicReference<ParserRuleContext> top = new AtomicReference<>();
        ParseTreeWalker.DEFAULT.walk(new RemoveVariable(code, top), root);
        if (top.get() != null) {
            top.get().getParent().children.remove(top.get());
        }
    }

    public static int findType(GLSLParser.Translation_unitContext root, String code) {
        AtomicInteger type = new AtomicInteger();
        ParseTreeWalker.DEFAULT.walk(new TypeFinder(code, type), root);
        return type.get();
    }

    public static void appendMain(GLSLParser.Translation_unitContext root, String code) {
        ParseTreeWalker.DEFAULT.walk(new AppendFunction("main", code), root);
    }

    public static boolean containsCall(GLSLParser.Translation_unitContext root, String name) {
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

    public static boolean hasVariable(GLSLParser.Translation_unitContext root, String name) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParseTreeWalker.DEFAULT.walk(new HasVariable(name, atomicBoolean), root);
        return atomicBoolean.get();
    }

    public static void renameArray(GLSLParser.Translation_unitContext root, String oldName, String newName, Set<Integer> found) {
        ParseTreeWalker.DEFAULT.walk(new ArrayExpressionRewriteListener(Map.of(oldName, newName), found), root);
    }

    public static void renameFunctionCall(GLSLParser.Translation_unitContext root, String oldName, String newName) {
        ParseTreeWalker.DEFAULT.walk(new ExpressionRenamer(Map.of(oldName, newName)), root);
    }

    public static void renameAndWrapShadow(GLSLParser.Translation_unitContext root, String oldName, String newName) {
        ParseTreeWalker.DEFAULT.walk( new FunctionCallWrapper(oldName, "vec4"), root);
        renameFunctionCall(root, oldName, newName);
    }

    public static List<String> collectFunctions(GLSLParser.Translation_unitContext root) {
        List<String> result = new ArrayList<>();
        ParseTreeWalker.DEFAULT.walk(new FunctionCollector(result), root);
        return result;
    }

    public static void removeFunction(GLSLParser.Translation_unitContext root, String name) {
        AtomicReference<GLSLParser.External_declarationContext> context = new AtomicReference<>();
        FastTreeWalker.walk(new FunctionRemover(name, context), root);
        if (context.get() != null) {
            context.get().getParent().children.remove(context.get());
        }
    }

    public static void removeUnusedFunctions(GLSLParser.Translation_unitContext root) {
        List<String> result = Util.collectFunctions(root);
        Set<String> usedIdentifiers = new HashSet<>();
        FastTreeWalker.walk(new IdentifierCollector(id -> {
            usedIdentifiers.add(id);
            return true;
        }), root);
        List<String> functionsToRemove = result.stream().filter(name -> !usedIdentifiers.contains(name) && !name.equals("main")).toList();
        // TODO - remove all the functions in one walk of the tree
        for (String name : functionsToRemove) {
            removeFunction(root, name);
        }
    }

    public static Map<String, List<String>> findConstParameter(GLSLParser.Translation_unitContext root) {
        Map<String, List<String>> functions = new HashMap<>();
        ParseTreeWalker.DEFAULT.walk(new ConstParameterFinder(functions), root);
        return functions;
    }

    public static void removeConstAssignment(GLSLParser.Translation_unitContext root, Map<String, List<String>> functions) {
        for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
            ParseTreeWalker.DEFAULT.walk(new ConstAssignmentRemover(entry.getKey(), entry.getValue()), root);
        }
    }

    public static void removeConstAssignment(GLSLParser.Translation_unitContext root) {
        Map<String, List<String>> functions = Util.findConstParameter(root);
        Util.removeConstAssignment(root, functions);
    }

    public static void initialize(GLSLParser.Translation_unitContext root, GLSLParser.Single_declarationContext declarationContext, String name) {
        if (declarationContext.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node && node.getSymbol() instanceof CommonToken token) {
            String insert = token.getText() + " " + name + " = " + BuiltinFunction.getByType(token.getType()).getInitializer() + ";" ;
            Util.prependMain(root, insert);
        }
    }

    public static void makeOutDeclaration(GLSLParser.Translation_unitContext root, GLSLParser.Single_declarationContext inDeclarationContext, String name) {
        String insert = Main.getFormattedShader(inDeclarationContext.fully_specified_type()) + name + ";"; //TODO, find a different way to make the out declaration
        insert = insert.replaceFirst("in", "out");
        Util.injectVariable(root, insert);
    }

    public static Map<String, GLSLParser.Single_declarationContext> findQualifiers(GLSLParser.Translation_unitContext root, int type) {
        Map<String, GLSLParser.Single_declarationContext> result = new HashMap<>();
        ParseTreeWalker.DEFAULT.walk(new QualifierFinder(type, result), root);
        return result;
    }

    public static boolean hasAssigment(GLSLParser.Translation_unitContext root, String name) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ParseTreeWalker.DEFAULT.walk(new AssigmentChecker(name, atomicBoolean), root);
        return atomicBoolean.get();
    }

    public static void rewriteStructArrays(GLSLParser.Translation_unitContext root) {
        ParseTreeWalker.DEFAULT.walk(new StructArrayRewriter(), root);
    }

}
