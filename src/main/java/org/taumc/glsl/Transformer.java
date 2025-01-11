package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transformer {
    private final GLSLParser.Translation_unitContext root;
    final List<ParserRuleContext>[] cachedContexts;

    public GLSLParser.External_declarationContext variable = null;
    public GLSLParser.External_declarationContext function = null;

    @SuppressWarnings("unchecked")
    private <T extends ParserRuleContext> List<T> getContextsForRule(int ruleIndex) {
        return ruleIndex >= 0 && ruleIndex < this.cachedContexts.length ? (List<T>)this.cachedContexts[ruleIndex] : Collections.emptyList();
    }

    public Transformer(GLSLParser.Translation_unitContext root) {
        this.root = root;
        this.cachedContexts = new List[GLSLParser.ruleNames.length];
        for (int i = 0; i < this.cachedContexts.length; i++) {
            this.cachedContexts[i] = new ArrayList<>();
        }
        ParseTreeWalker.DEFAULT.walk(new TransformerCollector(this), root);
    }

    /**
     * Injects at the end of the "external_declaration_list", before functions
     * @param code the to inject code
     */
    public void injectVariable(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::external_declaration);

        if (variable == null) {
            var storageQualifiers = getContextsForRule(GLSLParser.RULE_storage_qualifier);
            if (!storageQualifiers.isEmpty()) {
                var parent = storageQualifiers.get(0).getParent();
                while (!(parent instanceof GLSLParser.External_declarationContext)) {
                    if (parent.getParent() == null) {
                        break;
                    }
                    parent = parent.getParent();
                }
                if (parent instanceof GLSLParser.External_declarationContext list) {
                    variable = list;
                }
            }
        }

        if (variable == null){
            var functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
            if (functionDefinitions.get(0).getParent() instanceof GLSLParser.External_declarationContext list) {
                variable = list;
            }
        }

        if (variable != null) {
            var parent = variable.getParent();
            int i = parent.children.indexOf(variable);
            parent.children.add(i, insert);
            insert.setParent(parent);
            scanNode(insert);
            ParseTreeWalker.DEFAULT.walk(new InjectorPoint(this, false), insert);
        }
    }

    public void injectFunction(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::external_declaration);


        if (function == null) {
            var functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
            if (!functionDefinitions.isEmpty() && functionDefinitions.get(0).getParent() instanceof GLSLParser.External_declarationContext list) {
                function = list;
            }
        }

        ParserRuleContext parent;

        if (function != null) {
            parent = function.getParent();
        } else {
            parent = this.root;
        }

        int i = parent.children.indexOf(function);
        parent.children.add(i, insert);
        insert.setParent(parent);
        scanNode(insert);
        ParseTreeWalker.DEFAULT.walk(new InjectorPoint(this, true), insert);
    }

    public void injectAtEnd(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::external_declaration);

        root.children.add(insert);
        scanNode(insert);
    }

    public void rename(String oldName, String newName) {
        rename(Collections.singletonMap(oldName, newName));
    }

    public void rename(Map<String, String> names) {
        List<TerminalNode> nodes = new ArrayList<>();
        List<GLSLParser.Typeless_declarationContext> typelessDeclarations = getContextsForRule(GLSLParser.RULE_typeless_declaration);
        List<GLSLParser.Function_prototypeContext> functionPrototypes = getContextsForRule(GLSLParser.RULE_function_prototype);
        List<GLSLParser.Variable_identifierContext> variableIdentifiers = getContextsForRule(GLSLParser.RULE_variable_identifier);
        nodes.addAll(typelessDeclarations.stream().map(GLSLParser.Typeless_declarationContext::IDENTIFIER).collect(Collectors.toList()));
        nodes.addAll(variableIdentifiers.stream().map(GLSLParser.Variable_identifierContext::IDENTIFIER).collect(Collectors.toList()));
        nodes.addAll(functionPrototypes.stream().map(GLSLParser.Function_prototypeContext::IDENTIFIER).collect(Collectors.toList()));
        for (var node : nodes) {
            Token token = node.getSymbol();
            if(token instanceof CommonToken cToken) {
                String replacement = names.get(cToken.getText());
                if(replacement != null) {
                    cToken.setText(replacement);
                }
            }
        }
    }

    @Deprecated
    public void replaceExpression(String oldCode, String newCode) {
        replaceExpression(oldCode, newCode, GLSLParser::binary_expression);
        replaceExpression(oldCode, newCode, GLSLParser::postfix_expression);
    }

    private final ArrayDeque<ParseTree> nodeStack = new ArrayDeque<>();

    private boolean textEqual(RuleContext ctx, String text) {
        int currentIndex = 0;
        var stack = nodeStack;
        stack.add(ctx);
        boolean matches = true;
        // Traverse all leaf nodes,
        while (!stack.isEmpty()) {
            var node = stack.removeLast();
            if (node.getChildCount() == 0) {
                // Leaf, compare that part of text
                String nodeText = node.getText();
                if (!text.regionMatches(currentIndex, nodeText, 0, nodeText.length())) {
                    matches = false;
                    break;
                }
                currentIndex += nodeText.length();
            } else {
                for (int i = node.getChildCount() - 1; i >= 0; i--) {
                    stack.add(node.getChild(i));
                }
            }
        }
        stack.clear();
        return matches && currentIndex == text.length();
    }

    public <T extends ParserRuleContext> void replaceExpression(String oldCode, String newCode, Function<GLSLParser, T> expressionType) {
        var oldExpression = ShaderParser.parseSnippet(oldCode, expressionType);
        String oldText = oldExpression.getText();
        List<T> exprList = new ArrayList<>(getContextsForRule(oldExpression.getRuleIndex()));
        for (var ctx : exprList) {
            if (textEqual(ctx, oldText)) {
                replaceNode(ctx, ShaderParser.parseSnippet(newCode, expressionType));
            }
        }
    }

    private void replaceNode(ParserRuleContext oldNode, ParserRuleContext newNode) {
        scanNode(newNode);
        newNode.parent = oldNode.getParent();
        int i = oldNode.getParent().children.indexOf(oldNode);
        oldNode.getParent().children.set(i, newNode);
        removeNode(oldNode);
    }

    private void removeNode(ParserRuleContext node) {
        var remover = new TransformerRemover(this);
        ParseTreeWalker.DEFAULT.walk(remover, node);
        remover.performRemovals();
    }

    private void scanNode(ParserRuleContext node) {
        ParseTreeWalker.DEFAULT.walk(new TransformerCollector(this), node);
    }

    public void prependMain(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::statement);
        List<GLSLParser.Function_definitionContext> functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
        for (var ctx : functionDefinitions) {
            if (ctx.function_prototype().IDENTIFIER().getText().equals("main")) {
                ctx.compound_statement_no_new_scope().statement_list().children.add(0, insert);
                scanNode(insert);
            }
        }
    }

    public void removeVariable(String code) {
        ParserRuleContext typeless = null;
        List<GLSLParser.Typeless_declarationContext> typelessDeclaration = getContextsForRule(GLSLParser.RULE_typeless_declaration);
        for (var ctx : typelessDeclaration) {
            if (ctx.IDENTIFIER() != null) {
                Token token = ctx.IDENTIFIER().getSymbol();
                if(token instanceof CommonToken cToken) {
                    if(code.equals(cToken.getText())) {
                        if (ctx.getParent() instanceof GLSLParser.Single_declarationContext) {
                            if (ctx.getParent().getParent() instanceof GLSLParser.Init_declarator_listContext list) {
                                if (!list.typeless_declaration().isEmpty()) {
                                    var entry = list.typeless_declaration(0);
                                    cToken.setText(entry.getText());
                                    typeless = entry;
                                    break;
                                } else {
                                    typeless = ctx;
                                }
                            }
                        }
                        else if (ctx.getParent() instanceof GLSLParser.Init_declarator_listContext) {
                            typeless = ctx;
                            break;
                        }
                    }
                }
            }
        }

        if (typeless == null) {
            return;
        }

        if (typeless.getParent() instanceof GLSLParser.Init_declarator_listContext listContext) {
            int i = listContext.children.indexOf(typeless);
            if (listContext.children.remove(i-1) instanceof ParserRuleContext context) {
                removeNode(context);
            }
            if (listContext.children.remove(i-1) instanceof ParserRuleContext context) {
                removeNode(context);
            }
        } else if (typeless.parent instanceof GLSLParser.Single_declarationContext singleContext) {
            ParserRuleContext node = singleContext.getParent().getParent().getParent();
            node.getParent().children.remove(node);
            removeNode(node);
        }
    }

    public int findType(String code) {
        List<GLSLParser.Single_declarationContext> singleDeclarations = getContextsForRule(GLSLParser.RULE_single_declaration);
        for (var ctx : singleDeclarations) {
            if (ctx.typeless_declaration() == null) {
                continue;
            }
            if (ctx.typeless_declaration().IDENTIFIER().getSymbol() instanceof CommonToken cToken) {
                if (cToken.getText().equals(code)) {
                    if (ctx.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node) {
                        if (node.getSymbol() instanceof CommonToken t) {
                            return t.getType();
                        }
                    }
                }
            }
            if (ctx.getParent() instanceof GLSLParser.Init_declarator_listContext listContext) {
                for (var entry : listContext.typeless_declaration()) {
                    if (entry.IDENTIFIER().getSymbol() instanceof CommonToken cToken) {
                        if (cToken.getText().equals(code)) {
                            if (ctx.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node) {
                                if (node.getSymbol() instanceof CommonToken t) {
                                    return t.getType();
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public void appendMain(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::statement);
        List<GLSLParser.Function_definitionContext> functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
        for (var ctx : functionDefinitions) {
            if (ctx.function_prototype().IDENTIFIER().getText().equals("main")) {
                ctx.compound_statement_no_new_scope().statement_list().children.add(insert);
                scanNode(insert);
            }
        }
    }

    public boolean containsCall(String name) {
        List<GLSLParser.Variable_identifierContext> variableIdentifiers = getContextsForRule(GLSLParser.RULE_variable_identifier);
        for (var ctx : variableIdentifiers) {
            if (ctx.IDENTIFIER().getSymbol() instanceof CommonToken cToken) {
                if (cToken.getText().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasVariable(String name) {
        List<TerminalNode> nodes = new ArrayList<>();
        List<GLSLParser.Typeless_declarationContext> typelessDeclarations = getContextsForRule(GLSLParser.RULE_typeless_declaration);
        List<GLSLParser.Function_prototypeContext> functionPrototypes = getContextsForRule(GLSLParser.RULE_function_prototype);
        nodes.addAll(typelessDeclarations.stream().map(GLSLParser.Typeless_declarationContext::IDENTIFIER).collect(Collectors.toList()));
        nodes.addAll(functionPrototypes.stream().map(GLSLParser.Function_prototypeContext::IDENTIFIER).collect(Collectors.toList()));
        for (var node : nodes) {
            Token token = node.getSymbol();
            if(token instanceof CommonToken cToken) {
                if (name.equals(cToken.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void renameArray(String oldName, String newName, Set<Integer> found) {
        renameArray(Collections.singletonMap(oldName, newName), found);
    }

    public void renameArray(Map<String, String> replacements, Set<Integer> found) {
        List<GLSLParser.Postfix_expressionContext> postfixExpressions = getContextsForRule(GLSLParser.RULE_postfix_expression);
        for (var ctx : postfixExpressions) {
            if (ctx.postfix_expression() == null) {
                continue;
            }
            if (ctx.postfix_expression().primary_expression() == null) {
                continue;
            }
            if (ctx.postfix_expression().primary_expression().variable_identifier() == null) {
                continue;
            }
            if (ctx.postfix_expression().primary_expression().variable_identifier().IDENTIFIER().getSymbol() instanceof CommonToken cToken) {
                String replacement = replacements.get(cToken.getText());
                if(replacement != null && ctx.integer_expression() != null) {
                    String i = ctx.integer_expression().getText();
                    found.add(Integer.parseInt(i));
                    cToken.setText(replacement + i);
                    ctx.removeLastChild();
                    ctx.removeLastChild();
                    ctx.removeLastChild();
                }
            }
        }
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
        List<TerminalNode> nodes = new ArrayList<>();
        List<GLSLParser.Function_prototypeContext> functionPrototypes = getContextsForRule(GLSLParser.RULE_function_prototype);
        List<GLSLParser.Variable_identifierContext> variableIdentifiers = getContextsForRule(GLSLParser.RULE_variable_identifier);

        nodes.addAll(functionPrototypes.stream().map(GLSLParser.Function_prototypeContext::IDENTIFIER).collect(Collectors.toList()));
        nodes.addAll(variableIdentifiers.stream().map(GLSLParser.Variable_identifierContext::IDENTIFIER).collect(Collectors.toList()));
        // TODO necessary?
        /*
        nodes.addAll(textures.stream().map(c -> {
            if (c.TEXTURE2D() != null) {
                return c.TEXTURE2D();
            } else {
                return c.TEXTURE3D();
            }
        }).collect(Collectors.toList()));
         */
        for (var node : nodes) {
            Token token = node.getSymbol();
            if(token instanceof CommonToken cToken) {
                String replacement = names.get(cToken.getText());
                if(replacement != null) {
                    cToken.setText(replacement);
                }
            }
        }
    }

    public void renameAndWrapShadow(String oldName, String newName) {
        List<GLSLParser.Postfix_expressionContext> postfixExpressions = new ArrayList<>(getContextsForRule(GLSLParser.RULE_postfix_expression));
        for (var expr : postfixExpressions) {
            if (expr.function_call_parameters() == null) {
                continue;
            }
            String function = expr.getText();
            if (expr.function_call_parameters() != null && function.startsWith(oldName + "(")) {
                function = "vec4" + "(" + function + ")";
                var def = ShaderParser.parseSnippet(function, GLSLParser::postfix_expression);
                replaceNode(expr, def);
            }
        }
        renameFunctionCall(oldName, newName);
    }

    public void removeFunction(String name) {
        List<GLSLParser.Function_prototypeContext> functionPrototypes = getContextsForRule(GLSLParser.RULE_function_prototype);
        var functionPrototype = new ArrayList<>(functionPrototypes);
        for (var ctx : functionPrototype) {
            if (ctx.IDENTIFIER().getText().equals(name)) {
                if (ctx.getParent().getParent() instanceof GLSLParser.External_declarationContext declarationContext) {
                    declarationContext.getParent().children.remove(declarationContext);
                    removeNode(declarationContext);
                }
            }
        }
    }

    public void removeUnusedFunctions() {
        List<GLSLParser.Function_prototypeContext> functionPrototypes = getContextsForRule(GLSLParser.RULE_function_prototype);
        List<GLSLParser.Variable_identifierContext> variableIdentifiers = getContextsForRule(GLSLParser.RULE_variable_identifier);
        List<String> result = functionPrototypes.stream().map(c -> c.IDENTIFIER().getText()).collect(Collectors.toList());
        List<String> usedIdentifiers = variableIdentifiers.stream().map(c -> c.IDENTIFIER().getText()).collect(Collectors.toList());
        List<String> functionsToRemove = result.stream().filter(name -> !usedIdentifiers.contains(name) && !name.equals("main")).collect(Collectors.toList());
        // TODO - remove all the functions in one walk of the tree
        for (String name : functionsToRemove) {
            removeFunction(name);
        }
    }

    public Map<String, List<String>> findConstParameter() {
        Map<String, List<String>> functions = new HashMap<>();
        List<GLSLParser.Parameter_declarationContext> parameterDeclarations = getContextsForRule(GLSLParser.RULE_parameter_declaration);
        for (var ctx : parameterDeclarations) {
            GLSLParser.Type_qualifierContext typeQualifierContext = ctx.type_qualifier();
            if (typeQualifierContext == null) {
                continue;
            }
            GLSLParser.Single_type_qualifierContext singleTypeQualifierContext = typeQualifierContext.single_type_qualifier(0);
            if (singleTypeQualifierContext == null) {
                continue;
            }
            GLSLParser.Storage_qualifierContext storageQualifierContext = singleTypeQualifierContext.storage_qualifier();
            if (storageQualifierContext != null && storageQualifierContext.CONST() != null) {
                if (ctx.getParent().getParent() instanceof GLSLParser.Function_prototypeContext proto) {
                    if (functions.containsKey(proto.IDENTIFIER().getText())) {
                        functions.get(proto.IDENTIFIER().getText()).add(ctx.parameter_declarator().IDENTIFIER().getText());

                    } else {
                        List<String> params = new ArrayList<>();
                        params.add(ctx.parameter_declarator().IDENTIFIER().getText());
                        functions.put(proto.IDENTIFIER().getText(), params);
                    }
                }
            }
        }
        return functions;
    }

    public void removeConstAssignment(Map<String, List<String>> functions) {
        List<GLSLParser.Variable_identifierContext> variableIdentifiers = getContextsForRule(GLSLParser.RULE_variable_identifier);
        for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
            for (var ctx : variableIdentifiers) {
                if (entry.getValue().contains(ctx.IDENTIFIER().getText())) {
                    var parent = ctx.getParent();
                    while (!(parent instanceof GLSLParser.Function_definitionContext definitionContext)) {
                        parent = parent.getParent();
                        if (parent == null) {
                            return;
                        }
                    }

                    if (definitionContext.function_prototype().IDENTIFIER().getText().equals(entry.getKey())) {
                        parent = ctx.getParent();
                        while (!(parent instanceof GLSLParser.Single_declarationContext declarationContext)) {
                            parent = parent.getParent();
                            if (parent == null) {
                                return;
                            }
                        }

                        entry.getValue().add(declarationContext.typeless_declaration().IDENTIFIER().getText());

                        GLSLParser.Type_qualifierContext typeQualifierContext = declarationContext.fully_specified_type().type_qualifier();
                        if (typeQualifierContext == null || typeQualifierContext.single_type_qualifier(0) == null) {
                            return;
                        }
                        GLSLParser.Storage_qualifierContext storageQualifierContext = typeQualifierContext.single_type_qualifier(0).storage_qualifier();
                        if (storageQualifierContext != null && storageQualifierContext.CONST() != null) {
                            declarationContext.fully_specified_type().children.remove(typeQualifierContext);
                            removeNode(typeQualifierContext);
                        }
                    }
                }
            }
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

    public void makeOutDeclaration(GLSLParser.Single_declarationContext inDeclarationContext, String name) {
        String insert = Main.getFormattedShader(inDeclarationContext.fully_specified_type()) + name + ";"; //TODO, find a different way to make the out declaration
        insert = insert.replaceFirst("in", "out");
        injectVariable(insert);
    }

    public Map<String, GLSLParser.Single_declarationContext> findQualifiers(int type) {
        Map<String, GLSLParser.Single_declarationContext> nodes = new HashMap<>();
        List<GLSLParser.Storage_qualifierContext> storageQualifiers = getContextsForRule(GLSLParser.RULE_storage_qualifier);
        mainLoop:
        for (var ctx : storageQualifiers) {
            if (ctx.children.get(0) instanceof TerminalNode node &&
                    node.getSymbol() instanceof CommonToken token &&
                    token.getType() == type) {
                var parent = node.getParent();
                while (!(parent instanceof GLSLParser.Single_declarationContext declarationContext)) {
                    parent = parent.getParent();
                    if (parent == null) {
                        continue mainLoop;
                    }
                }

                nodes.put(declarationContext.typeless_declaration().IDENTIFIER().getText(), declarationContext);

                if (declarationContext.getParent() instanceof GLSLParser.Init_declarator_listContext listContext) {
                    if (listContext.typeless_declaration() != null) {
                        for (var entry : listContext.typeless_declaration()) {
                            nodes.put(entry.IDENTIFIER().getText(), declarationContext);
                        }
                    }
                }
            }
        }
        return nodes;
    }

    public boolean hasAssigment(String name) {
        List<GLSLParser.Assignment_expressionContext> assignmentExpressions = getContextsForRule(GLSLParser.RULE_assignment_expression);
        for (var ctx : assignmentExpressions) {
            if (ctx.unary_expression() != null && (ctx.unary_expression().getText().startsWith(name) || ctx.unary_expression().getText().startsWith(name + "."))) {
                return true;
            }
        }
        return false;
    }

    public void rewriteStructArrays() {
        List<GLSLParser.Struct_declarationContext> structDeclarations = getContextsForRule(GLSLParser.RULE_struct_declaration);
        for (var ctx : structDeclarations) {
            if (ctx.type_specifier().array_specifier() != null) {
                var array_specifier = ctx.type_specifier().array_specifier();
                if (array_specifier.dimension().get(0).constant_expression() != null) {
                    return;
                }
                ctx.type_specifier().children.remove(array_specifier);
                for (var entry : ctx.struct_declarator_list().struct_declarator()) {
                    array_specifier.setParent(entry);
                    entry.children.add(array_specifier);
                }
            }
        }
    }

    public List<TerminalNode> collectStorage() {
        List<GLSLParser.Storage_qualifierContext> storageQualifiers = getContextsForRule(GLSLParser.RULE_storage_qualifier);
        return storageQualifiers.stream().filter(ctx -> ctx.getChild(0) instanceof TerminalNode).map(ctx -> (TerminalNode) ctx.getChild(0)).collect(Collectors.toList());
    }
}
