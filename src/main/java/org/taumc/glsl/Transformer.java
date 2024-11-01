package org.taumc.glsl;

import com.ibm.icu.impl.CollectionSet;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
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
    private final List<GLSLParser.Function_definitionContext> functionDefinitions;
    private final List<GLSLParser.Postfix_expressionContext> postfixExpressions;
    private final List<GLSLParser.Assignment_expressionContext> assignmentExpressions;
    private final List<GLSLParser.Variable_identifierContext> variableIdentifiers;
    private final List<GLSLParser.Parameter_declarationContext> parameterDeclarations;
    private final List<GLSLParser.Typeless_declarationContext> typelessDeclarations;
    private final List<GLSLParser.Function_prototypeContext> functionPrototypes;
    private final List<GLSLParser.Binary_expressionContext> binaryExpressions;
    private final List<GLSLParser.Storage_qualifierContext> storageQualifiers;
    private final List<GLSLParser.Struct_declarationContext> structDeclarations;
    private final List<GLSLParser.Single_declarationContext> singleDeclarations;
    private final List<GLSLParser.Type_specifier_nonarrayContext> textures;
    public GLSLParser.External_declarationContext variable = null;
    public GLSLParser.External_declarationContext function = null;

    public Transformer(GLSLParser.Translation_unitContext root) {
        this.root = root;
        this.functionDefinitions = new ArrayList<>();
        this.postfixExpressions = new ArrayList<>();
        this.assignmentExpressions = new ArrayList<>();
        this.variableIdentifiers = new ArrayList<>();
        this.parameterDeclarations = new ArrayList<>();
        this.typelessDeclarations = new ArrayList<>();
        this.functionPrototypes = new ArrayList<>();
        this.binaryExpressions = new ArrayList<>();
        this.storageQualifiers = new ArrayList<>();
        this.structDeclarations = new ArrayList<>();
        this.singleDeclarations = new ArrayList<>();
        this.textures = new ArrayList<>();
        ParseTreeWalker.DEFAULT.walk(new TransformerCollector(this), root);
    }

    /**
     * Injects at the end of the "external_declaration_list", before functions
     * @param code the to inject code
     */
    public void injectVariable(String code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.external_declaration();

        if (variable == null) {
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
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.external_declaration();

        if (function == null) {
            if (functionDefinitions.get(0).getParent() instanceof GLSLParser.External_declarationContext list) {
                function = list;
            }
        }

        if (function != null) {
            var parent = function.getParent();
            int i = parent.children.indexOf(function);
            parent.children.add(i, insert);
            insert.setParent(parent);
            scanNode(insert);
            ParseTreeWalker.DEFAULT.walk(new InjectorPoint(this, true), insert);
        }
    }

    public void rename(String oldName, String newName) {
        rename(Collections.singletonMap(oldName, newName));
    }

    public void rename(Map<String, String> names) {
        List<TerminalNode> nodes = new ArrayList<>();
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

    public void replaceExpression(String oldCode, String newCode) {
        GLSLLexer oldLexer = new GLSLLexer(CharStreams.fromString(oldCode));
        GLSLParser oldParser = new GLSLParser(new CommonTokenStream(oldLexer));
        var oldExpression = oldParser.binary_expression();
        String oldText = oldExpression.getText();
        var binaryExpression = new ArrayList<>(binaryExpressions);
        for (var ctx : binaryExpression) {
            String ctxText = ctx.getText();
            if (ctxText.equals(oldText)) {
                GLSLLexer newLexer = new GLSLLexer(CharStreams.fromString(newCode));
                GLSLParser newParser = new GLSLParser(new CommonTokenStream(newLexer));
                replaceNode(ctx, newParser.binary_expression());
            } else if (ctxText.startsWith(oldText)) {
                if (ctx.unary_expression() != null) {
                    if (ctx.unary_expression().postfix_expression() != null) {
                        if (ctx.unary_expression().postfix_expression().postfix_expression() != null) {
                            var postfix = ctx.unary_expression().postfix_expression().postfix_expression();
                            while (postfix.getText().startsWith(oldText) && !postfix.getText().equals(oldText)) {
                                if (postfix.postfix_expression() != null) {
                                    postfix = postfix.postfix_expression();
                                } else {
                                    break;
                                }
                            }
                            if (postfix.getText().equals(oldText)) {
                                GLSLLexer newLexer = new GLSLLexer(CharStreams.fromString(newCode));
                                GLSLParser newParser = new GLSLParser(new CommonTokenStream(newLexer));
                                replaceNode(postfix, newParser.unary_expression());
                            }
                        }
                    }
                }
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
        ParseTreeWalker.DEFAULT.walk(new TransformerRemover(this), node);
    }

    private void scanNode(ParserRuleContext node) {
        ParseTreeWalker.DEFAULT.walk(new TransformerCollector(this), node);
    }

    public void prependMain(String code) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.statement();
        for (var ctx : functionDefinitions) {
            if (ctx.function_prototype().IDENTIFIER().getText().equals("main")) {
                ctx.compound_statement_no_new_scope().statement_list().children.add(0, insert);
                scanNode(insert);
            }
        }
    }

    public void removeVariable(String code) {
        ParserRuleContext typeless = null;
        var typelessDeclaration = new ArrayList<>(this.typelessDeclarations);
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
        for (var ctx : singleDeclarations) {
            if (ctx.typeless_declaration() == null) {
                return 0;
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
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        var insert = parser.statement();
        for (var ctx : functionDefinitions) {
            if (ctx.function_prototype().IDENTIFIER().getText().equals("main")) {
                ctx.compound_statement_no_new_scope().statement_list().children.add(insert);
                scanNode(insert);
            }
        }
    }

    public boolean containsCall(String name) {
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
        nodes.addAll(functionPrototypes.stream().map(GLSLParser.Function_prototypeContext::IDENTIFIER).collect(Collectors.toList()));
        nodes.addAll(variableIdentifiers.stream().map(GLSLParser.Variable_identifierContext::IDENTIFIER).collect(Collectors.toList()));
        nodes.addAll(textures.stream().map(c -> {
            if (c.TEXTURE2D() != null) {
                return c.TEXTURE2D();
            } else {
                return c.TEXTURE3D();
            }
        }).collect(Collectors.toList()));
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
        var postfixExpression = new ArrayList<>(postfixExpressions);
        for (var ctx : postfixExpression) {
            String function = ctx.getText();
            if (ctx.function_call_parameters() != null && function.startsWith(oldName + "(")) {
                function = "vec4" + "(" + function + ")";
                GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(function));
                GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
                var def = parser.postfix_expression();
                replaceNode(ctx, def);
            }
        }
        renameFunctionCall(oldName, newName);
    }

    public void removeFunction(String name) {
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
        for (var ctx : assignmentExpressions) {
            if (ctx.unary_expression() != null && (ctx.unary_expression().getText().startsWith(name) || ctx.unary_expression().getText().startsWith(name + "."))) {
                return true;
            }
        }
        return false;
    }

    public void rewriteStructArrays() {
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
        return storageQualifiers.stream().filter(ctx -> ctx.getChild(0) instanceof TerminalNode).map(ctx -> (TerminalNode) ctx.getChild(0)).collect(Collectors.toList());
    }

    //Add
    public void addFunctionDefinition(GLSLParser.Function_definitionContext ctx) {
        this.functionDefinitions.add(ctx);
    }

    public void addPostfix(GLSLParser.Postfix_expressionContext ctx) {
        this.postfixExpressions.add(ctx);
    }

    public void addAssignment(GLSLParser.Assignment_expressionContext ctx) {
        this.assignmentExpressions.add(ctx);
    }

    public void addVariable(GLSLParser.Variable_identifierContext ctx) {
        this.variableIdentifiers.add(ctx);
    }

    public void addParameter(GLSLParser.Parameter_declarationContext ctx) {
        this.parameterDeclarations.add(ctx);
    }

    public void addFunctionPrototype(GLSLParser.Function_prototypeContext ctx) {
        this.functionPrototypes.add(ctx);
    }

    public void addTypeless(GLSLParser.Typeless_declarationContext ctx) {
        this.typelessDeclarations.add(ctx);
    }

    public void addBinary(GLSLParser.Binary_expressionContext ctx) {
        this.binaryExpressions.add(ctx);
    }

    public void addStorage(GLSLParser.Storage_qualifierContext ctx) {
        this.storageQualifiers.add(ctx);
    }

    public void addStruct(GLSLParser.Struct_declarationContext ctx) {
        this.structDeclarations.add(ctx);
    }

    public void addSingle(GLSLParser.Single_declarationContext ctx) {
        this.singleDeclarations.add(ctx);
    }

    public void addTexture(GLSLParser.Type_specifier_nonarrayContext ctx) {
        this.textures.add(ctx);
    }

    //Remove
    public void removeFunctionDefinition(GLSLParser.Function_definitionContext ctx) {
        functionDefinitions.remove(ctx);
    }

    public void removeFunctionPrototype(GLSLParser.Function_prototypeContext ctx) {
        functionPrototypes.remove(ctx);
    }

    public void removeParameter(GLSLParser.Parameter_declarationContext ctx) {
        parameterDeclarations.remove(ctx);
    }

    public void removeTypeless(GLSLParser.Typeless_declarationContext ctx) {
        typelessDeclarations.remove(ctx);
    }

    public void removeTexture(GLSLParser.Type_specifier_nonarrayContext ctx) {
        textures.remove(ctx);
    }

    public void removePostfix(GLSLParser.Postfix_expressionContext ctx) {
        postfixExpressions.remove(ctx);
    }

    public void removeAssignment(GLSLParser.Assignment_expressionContext ctx) {
        assignmentExpressions.remove(ctx);
    }

    public void removeBinary(GLSLParser.Binary_expressionContext ctx) {
        binaryExpressions.remove(ctx);
    }

    public void removeStorage(GLSLParser.Storage_qualifierContext ctx) {
        storageQualifiers.remove(ctx);
    }

    public void removeStruct(GLSLParser.Struct_declarationContext ctx) {
        structDeclarations.remove(ctx);
    }

    public void removeSingle(GLSLParser.Single_declarationContext ctx) {
        singleDeclarations.remove(ctx);
    }

    public void removeVariables(GLSLParser.Variable_identifierContext ctx) {
        variableIdentifiers.remove(ctx);
    }
}
