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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Transformer {

    private final List<String> VARIABLES = new ArrayList<>();
    private final List<String> CALLS = new ArrayList<>();
    private final Map<String, String> RENAMES = new HashMap<>();
    private final GLSLParser.Translation_unitContext root;
    private GLSLParser.External_declarationContext variableInjection = null;
    private GLSLParser.External_declarationContext functionInjection = null;

    public Transformer(GLSLParser.Translation_unitContext root) {
        this.root = root;
        collectCalls();
        collectVariables();
        getVariableInjection();
        getFunctionInjection();
    }

    public void apply() {
        applyRenames();
        VARIABLES.clear();
        CALLS.clear();
        RENAMES.clear();
    }

    public void getVariableInjection() {
        AtomicReference<GLSLParser.External_declarationContext> ref = new AtomicReference<>();
        FastTreeWalker.walk(new VariableInjector(ref), root);

        variableInjection = ref.get();
    }

    /**
     * Injects at the end of the "external_declaration_list", before functions
     * @param code the to inject code
     */
    public void injectVariable(String code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.external_declaration();

        if (variableInjection != null) {
            var parent = variableInjection.getParent();
            int i = parent.children.indexOf(variableInjection);
            parent.children.add(i, insert);
            insert.setParent(parent);
            if (insert.function_definition() != null) { //detect function insert
                variableInjection = insert;
            }
            else if (insert.declaration() != null && insert.declaration().type_qualifier() != null) { //detect storage insert
                for (var single : insert.declaration().type_qualifier().single_type_qualifier()) {
                    if (single.storage_qualifier() != null) {
                        functionInjection = insert;
                        break;
                    }
                }
            }
        }
    }

    public void getFunctionInjection() {
        AtomicReference<GLSLParser.External_declarationContext> ref = new AtomicReference<>();
        FastTreeWalker.walk(new FunctionInjector(ref), root);

        functionInjection = ref.get();
    }

    public void injectFunction(String code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.external_declaration();

        if (functionInjection != null) {
            var parent = functionInjection.getParent();
            int i = parent.children.indexOf(functionInjection);
            parent.children.add(i, insert);
            insert.setParent(parent);
            if (insert.function_definition() != null) {
                functionInjection = insert;
            }
        }
    }

    public void rename(String oldName, String newName) {
        RENAMES.put(oldName, newName);
    }

    private void applyRenames() {
        ParseTreeWalker.DEFAULT.walk(new Renamer(RENAMES), root);
    }

    public void replaceExpression(String oldCode, String newCode) {
        ParseTreeWalker.DEFAULT.walk(new ReplaceExpression(oldCode, newCode), root);
    }

    public void prependMain(String code) {
        FastTreeWalker.walk(new PrependFunction("main", code), root);
    }

    public void removeVariable(String code) {
        AtomicReference<ParserRuleContext> top = new AtomicReference<>();
        ParseTreeWalker.DEFAULT.walk(new RemoveVariable(code, top), root);
        if (top.get() != null) {
            top.get().getParent().children.remove(top.get());
        }
    }

    public int findType(String code) {
        AtomicInteger type = new AtomicInteger();
        FastTreeWalker.walk(new TypeFinder(code, type), root);
        return type.get();
    }

    public void appendMain(String code) {
        FastTreeWalker.walk(new AppendFunction("main", code), root);
    }

    public void collectCalls() {
        ParseTreeWalker.DEFAULT.walk(new CallCollector(CALLS), root);
    }

    public boolean containsCall(String name) {
        return CALLS.contains(name);
    }

    public void collectVariables() {
        ParseTreeWalker.DEFAULT.walk(new VariableCollector(VARIABLES), root);
    }

    public boolean hasVariable(String name) {
        return VARIABLES.contains(name);
    }

    public void renameArray(String oldName, String newName, Set<Integer> found) {
        ParseTreeWalker.DEFAULT.walk(new ArrayExpressionRewriteListener(Map.of(oldName, newName), found), root);
    }

    public void renameFunctionCall(String oldName, String newName) {
        ParseTreeWalker.DEFAULT.walk(new ExpressionRenamer(Map.of(oldName, newName)), root);
    }

    public void renameAndWrapShadow(String oldName, String newName) {
        ParseTreeWalker.DEFAULT.walk( new FunctionCallWrapper(oldName, "vec4"), root);
        renameFunctionCall(oldName, newName);
    }

    public List<String> collectFunctions() {
        List<String> result = new ArrayList<>();
        ParseTreeWalker.DEFAULT.walk(new FunctionCollector(result), root);
        return result;
    }

    public void removeFunction(String name) {
        List<GLSLParser.External_declarationContext> context = new ArrayList<>();
        FastTreeWalker.walk(new FunctionRemover(List.of(name), context), root);
        for (var entry : context) {
            entry.getParent().children.remove(entry);
        }
    }

    public void removeFunctions(List<String> name) {
        List<GLSLParser.External_declarationContext> context = new ArrayList<>();
        FastTreeWalker.walk(new FunctionRemover(name, context), root);
        for (var entry : context) {
            entry.getParent().children.remove(entry);
        }
    }

    public void removeUnusedFunctions() {
        List<String> result = collectFunctions();
        result.remove("main");
        List<String> functionsToRemove = result.stream().filter(name -> !CALLS.contains(name)).toList();
        removeFunctions(functionsToRemove);
    }

    public Map<String, List<String>> findConstParameter() {
        Map<String, List<String>> functions = new HashMap<>();
        ParseTreeWalker.DEFAULT.walk(new ConstParameterFinder(functions), root);
        return functions;
    }

    public void removeConstAssignment(Map<String, List<String>> functions) {
        for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
            ParseTreeWalker.DEFAULT.walk(new ConstAssignmentRemover(entry.getKey(), entry.getValue()), root);
        }
    }

    public void removeConstAssignment() {
        Map<String, List<String>> functions = findConstParameter();
        removeConstAssignment(functions);
    }

    public void initialize(GLSLParser.Single_declarationContext declarationContext, String name) {
        if (declarationContext.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node && node.getSymbol() instanceof CommonToken token) {
            String insert = token.getText() + " " + name + " = " + BuiltinFunction.getByType(token.getType()).getInitializer() + ";" ;
            prependMain(insert);
        }
    }

    public void makeOutDeclaration(GLSLParser.Single_declarationContext inDeclarationContext) {
        String insert = inDeclarationContext.getText() + ";";
        insert = insert.replaceFirst("in", "out");
        prependMain(insert);
    }

    public Map<String, GLSLParser.Single_declarationContext> findQualifiers(int type) {
        Map<String, GLSLParser.Single_declarationContext> result = new HashMap<>();
        ParseTreeWalker.DEFAULT.walk(new QualifierFinder(type, result), root);
        return result;
    }

    public boolean hasAssigment(String name) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        FastTreeWalker.walk(new AssigmentChecker(name, atomicBoolean), root);
        return atomicBoolean.get();
    }

    public void rewriteStructArrays() {
        ParseTreeWalker.DEFAULT.walk(new StructArrayRewriter(), root);
    }

}
