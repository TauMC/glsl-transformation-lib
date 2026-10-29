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
    // Every rule context in the tree, by rule index, in the order they were added.
    private final Collection<ParserRuleContext>[] cachedContexts;
    // Every terminal in the tree, by its token's text, so that finding a name or an expression doesn't have to scan
    // the whole tree. Token text changed through this class keeps it up to date; see setTokenText.
    private final Map<String, Set<TerminalNode>> terminalsByText = new HashMap<>();
    // The key each terminal is filed under, which differs from its text if that was changed from outside.
    private final Map<TerminalNode, String> terminalKeys = new IdentityHashMap<>();
    // Set by mutateTree, whose changes the terminal index can't follow.
    private boolean terminalIndexStale = false;
    private final ArrayDeque<ParseTree> walkStack = new ArrayDeque<>();

    public GLSLParser.External_declarationContext variable = null;
    public GLSLParser.External_declarationContext function = null;


    @SuppressWarnings("unchecked")
    private <T extends ParserRuleContext> Collection<T> getContextsForRule(int ruleIndex) {
        return ruleIndex >= 0 && ruleIndex < this.cachedContexts.length ? (Collection<T>)this.cachedContexts[ruleIndex] : Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public Transformer(GLSLParser.Translation_unitContext root) {
        this.root = root;
        this.cachedContexts = new Collection[GLSLParser.ruleNames.length];
        for (int i = 0; i < this.cachedContexts.length; i++) {
            this.cachedContexts[i] = new LinkedHashSet<>();
        }
        scanNode(root);
    }

    /**
     * Adds a subtree to the indexes, visiting rule contexts in the same pre-order as ANTLR's tree walker.
     */
    private void scanNode(ParseTree node) {
        var stack = walkStack;
        stack.push(node);
        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (current instanceof ParserRuleContext ctx) {
                int ruleIndex = ctx.getRuleIndex();
                if (ruleIndex >= 0 && ruleIndex < cachedContexts.length) {
                    cachedContexts[ruleIndex].add(ctx);
                }
                var children = ctx.children;
                if (children != null) {
                    for (int i = children.size() - 1; i >= 0; i--) {
                        stack.push(children.get(i));
                    }
                }
            } else if (current instanceof TerminalNode terminal) {
                addTerminal(terminal);
            }
        }
    }

    /**
     * Removes a subtree from the indexes. The caller detaches it from the tree.
     */
    private void removeNode(ParseTree node) {
        var stack = walkStack;
        stack.push(node);
        while (!stack.isEmpty()) {
            var current = stack.pop();
            if (current instanceof ParserRuleContext ctx) {
                int ruleIndex = ctx.getRuleIndex();
                if (ruleIndex >= 0 && ruleIndex < cachedContexts.length) {
                    cachedContexts[ruleIndex].remove(ctx);
                }
                var children = ctx.children;
                if (children != null) {
                    for (int i = children.size() - 1; i >= 0; i--) {
                        stack.push(children.get(i));
                    }
                }
            } else if (current instanceof TerminalNode terminal) {
                removeTerminal(terminal);
            }
        }
    }

    private void addTerminal(TerminalNode terminal) {
        String key = terminal.getText();
        terminalsByText.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(terminal);
        terminalKeys.put(terminal, key);
    }

    private void removeTerminal(TerminalNode terminal) {
        String key = terminalKeys.remove(terminal);
        if (key != null) {
            terminalsByText.get(key).remove(terminal);
        }
    }

    private void setTokenText(TerminalNode terminal, CommonToken token, String text) {
        removeTerminal(terminal);
        token.setText(text);
        addTerminal(terminal);
    }

    /**
     * Returns the terminals whose text currently is {@code text}, as a list that stays valid while the tree changes.
     */
    private List<TerminalNode> terminalsWithText(String text) {
        if (terminalIndexStale) {
            terminalsByText.clear();
            terminalKeys.clear();
            var stack = walkStack;
            stack.push(root);
            while (!stack.isEmpty()) {
                var current = stack.pop();
                if (current instanceof ParserRuleContext ctx) {
                    if (ctx.children != null) {
                        for (int i = ctx.children.size() - 1; i >= 0; i--) {
                            stack.push(ctx.children.get(i));
                        }
                    }
                } else if (current instanceof TerminalNode terminal) {
                    addTerminal(terminal);
                }
            }
            terminalIndexStale = false;
        }
        var terminals = terminalsByText.get(text);
        if (terminals == null || terminals.isEmpty()) {
            return Collections.emptyList();
        }
        List<TerminalNode> result = new ArrayList<>(terminals.size());
        for (var terminal : terminals) {
            // Skip tokens whose text was changed from outside since they were indexed.
            if (text.equals(terminal.getText())) {
                result.add(terminal);
            }
        }
        return result;
    }

    /**
     * Whether the terminal is the identifier of a context for one of the given rules, like a variable_identifier's.
     */
    private static boolean isIdentifierOf(TerminalNode terminal, int ruleA, int ruleB, int ruleC) {
        if (terminal.getSymbol().getType() != GLSLParser.IDENTIFIER || !(terminal.getParent() instanceof ParserRuleContext parent)) {
            return false;
        }
        int ruleIndex = parent.getRuleIndex();
        return ruleIndex == ruleA || ruleIndex == ruleB || ruleIndex == ruleC;
    }

    private static ParseTree firstLeaf(ParseTree node) {
        while (node.getChildCount() > 0) {
            node = node.getChild(0);
        }
        return node;
    }

    /**
     * Calls the action for each context that the terminal is the first leaf of, innermost first.
     */
    private static void forEachContextStartingWith(TerminalNode terminal, Consumer<ParserRuleContext> action) {
        ParseTree child = terminal;
        var parent = terminal.getParent();
        while (parent instanceof ParserRuleContext ctx && ctx.getChildCount() > 0 && ctx.getChild(0) == child) {
            action.accept(ctx);
            child = ctx;
            parent = ctx.getParent();
        }
    }

    /**
     * Injects at the end of the "external_declaration_list", before functions
     * @param code the to inject code
     */
    public void injectVariable(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::external_declaration);

        if (variable == null) {
            // Locate the external_declaration wrapping the first function definition.
            GLSLParser.External_declarationContext firstFuncExtDecl = null;
            var functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
            if (!functionDefinitions.isEmpty()) {
                var fd = functionDefinitions.iterator().next();
                if (fd.getParent() instanceof GLSLParser.External_declarationContext list) {
                    firstFuncExtDecl = list;
                }
            }

            // Use the first storage qualifier only if its external_declaration ancestor
            // appears before the first function definition.  When the shader declares a
            // function before its variable declarations the first storage qualifier found
            // would be after that function, which would cause injected variables to land
            // in the wrong position.
            var storageQualifiers = getContextsForRule(GLSLParser.RULE_storage_qualifier);
            if (!storageQualifiers.isEmpty()) {
                var parent = storageQualifiers.iterator().next().getParent();
                while (!(parent instanceof GLSLParser.External_declarationContext)) {
                    if (parent.getParent() == null) {
                        parent = null;
                        break;
                    }
                    parent = parent.getParent();
                }
                if (parent instanceof GLSLParser.External_declarationContext list) {
                    boolean beforeFirstFunction = true;
                    if (firstFuncExtDecl != null) {
                        var grandparent = list.getParent();
                        int listIdx = grandparent.children.indexOf(list);
                        int funcIdx = grandparent.children.indexOf(firstFuncExtDecl);
                        beforeFirstFunction = listIdx < funcIdx;
                    }
                    if (beforeFirstFunction) {
                        variable = list;
                    }
                }
            }

            // Fallback: anchor on the first function definition.
            if (variable == null && firstFuncExtDecl != null) {
                variable = firstFuncExtDecl;
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
            if (!functionDefinitions.isEmpty() && functionDefinitions.iterator().next().getParent() instanceof GLSLParser.External_declarationContext list) {
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
        // Collected before renaming anything, so that a name renamed to another key isn't renamed twice.
        List<TerminalNode> nodes = new ArrayList<>();
        List<String> replacements = new ArrayList<>();
        for (var entry : names.entrySet()) {
            for (var node : terminalsWithText(entry.getKey())) {
                if (isIdentifierOf(node, GLSLParser.RULE_typeless_declaration, GLSLParser.RULE_variable_identifier, GLSLParser.RULE_function_prototype)) {
                    nodes.add(node);
                    replacements.add(entry.getValue());
                }
            }
        }
        for (int i = 0; i < nodes.size(); i++) {
            var node = nodes.get(i);
            if (node.getSymbol() instanceof CommonToken cToken) {
                setTokenText(node, cToken, replacements.get(i));
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

    @SuppressWarnings("unchecked")
    public <T extends ParserRuleContext> void replaceExpression(String oldCode, String newCode, Function<GLSLParser, T> expressionType) {
        var oldExpression = ShaderParser.parseSnippet(oldCode, expressionType);
        String oldText = oldExpression.getText();
        int ruleIndex = oldExpression.getRuleIndex();
        List<T> exprList = new ArrayList<>();
        if (firstLeaf(oldExpression) instanceof TerminalNode first) {
            // A match's first leaf has the same text as the expression's, so only contexts above those can match.
            for (var terminal : terminalsWithText(first.getText())) {
                forEachContextStartingWith(terminal, ctx -> {
                    if (ctx.getRuleIndex() == ruleIndex && textEqual(ctx, oldText)) {
                        exprList.add((T) ctx);
                    }
                });
            }
        } else {
            for (var ctx : getContextsForRule(ruleIndex)) {
                if (textEqual(ctx, oldText)) {
                    exprList.add((T) ctx);
                }
            }
        }
        for (var ctx : exprList) {
            replaceNode(ctx, ShaderParser.parseSnippet(newCode, expressionType));
        }
    }

    private void replaceNode(ParserRuleContext oldNode, ParserRuleContext newNode) {
        scanNode(newNode);
        newNode.parent = oldNode.getParent();
        int i = oldNode.getParent().children.indexOf(oldNode);
        oldNode.getParent().children.set(i, newNode);
        removeNode(oldNode);
    }

    public void prependMain(String code) {
        var insert = ShaderParser.parseSnippet(code, GLSLParser::statement);
        Collection<GLSLParser.Function_definitionContext> functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
        for (var ctx : functionDefinitions) {
            if (ctx.function_prototype().IDENTIFIER().getText().equals("main")) {
                scanNode(Util.injectStatement(ctx.compound_statement_no_new_scope(), insert, true));
            }
        }
    }

    public void removeVariable(String code) {
        ParserRuleContext typeless = null;
        Collection<GLSLParser.Typeless_declarationContext> typelessDeclaration = getContextsForRule(GLSLParser.RULE_typeless_declaration);
        for (var ctx : typelessDeclaration) {
            if (ctx.IDENTIFIER() != null) {
                Token token = ctx.IDENTIFIER().getSymbol();
                if(token instanceof CommonToken cToken) {
                    if(code.equals(cToken.getText())) {
                        if (ctx.getParent() instanceof GLSLParser.Single_declarationContext) {
                            if (ctx.getParent().getParent() instanceof GLSLParser.Init_declarator_listContext list) {
                                if (!list.typeless_declaration().isEmpty()) {
                                    var entry = list.typeless_declaration(0);
                                    setTokenText(ctx.IDENTIFIER(), cToken, entry.getText());
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
            removeNode(listContext.children.remove(i-1));
            removeNode(listContext.children.remove(i-1));
        } else if (typeless.parent instanceof GLSLParser.Single_declarationContext singleContext) {
            ParserRuleContext node = singleContext.getParent().getParent().getParent();
            node.getParent().children.remove(node);
            removeNode(node);
        }
    }

    public int findType(String code) {
        Collection<GLSLParser.Single_declarationContext> singleDeclarations = getContextsForRule(GLSLParser.RULE_single_declaration);
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
        Collection<GLSLParser.Function_definitionContext> functionDefinitions = getContextsForRule(GLSLParser.RULE_function_definition);
        for (var ctx : functionDefinitions) {
            if (ctx.function_prototype().IDENTIFIER().getText().equals("main")) {
                scanNode(Util.injectStatement(ctx.compound_statement_no_new_scope(), insert, false));
            }
        }
    }

    public boolean containsCall(String name) {
        for (var node : terminalsWithText(name)) {
            if (isIdentifierOf(node, GLSLParser.RULE_variable_identifier, -1, -1)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVariable(String name) {
        for (var node : terminalsWithText(name)) {
            if (isIdentifierOf(node, GLSLParser.RULE_typeless_declaration, GLSLParser.RULE_function_prototype, -1)) {
                return true;
            }
        }
        return false;
    }

    public void renameArray(String oldName, String newName, Set<Integer> found) {
        renameArray(Collections.singletonMap(oldName, newName), found);
    }

    public void renameArray(Map<String, String> replacements, Set<Integer> found) {
        // Finds name[i] from each use of the name: the variable_identifier, inside a primary_expression, inside the
        // postfix_expression that is the first postfix_expression of the indexing one.
        List<GLSLParser.Postfix_expressionContext> matches = new ArrayList<>();
        List<String> matchReplacements = new ArrayList<>();
        for (var entry : replacements.entrySet()) {
            for (var node : terminalsWithText(entry.getKey())) {
                if (node.getParent() instanceof GLSLParser.Variable_identifierContext identifier
                        && identifier.getParent() instanceof GLSLParser.Primary_expressionContext primary
                        && primary.variable_identifier() == identifier
                        && primary.getParent() instanceof GLSLParser.Postfix_expressionContext inner
                        && inner.primary_expression() == primary
                        && inner.getParent() instanceof GLSLParser.Postfix_expressionContext ctx
                        && ctx.postfix_expression() == inner
                        && ctx.integer_expression() != null) {
                    matches.add(ctx);
                    matchReplacements.add(entry.getValue());
                }
            }
        }
        for (int m = 0; m < matches.size(); m++) {
            var ctx = matches.get(m);
            var identifier = ctx.postfix_expression().primary_expression().variable_identifier().IDENTIFIER();
            if (identifier.getSymbol() instanceof CommonToken cToken) {
                String i = ctx.integer_expression().getText();
                found.add(Integer.parseInt(i));
                setTokenText(identifier, cToken, matchReplacements.get(m) + i);
                for (int r = 0; r < 3; r++) {
                    removeNode(ctx.children.get(ctx.children.size() - 1));
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
        terminalIndexStale = true;
    }

    public void renameFunctionCall(String oldName, String newName) {
        renameFunctionCall(Collections.singletonMap(oldName, newName));
    }

    public void renameFunctionCall(Map<String, String> names) {
        // Collected before renaming anything, so that a name renamed to another key isn't renamed twice.
        List<TerminalNode> nodes = new ArrayList<>();
        List<String> replacements = new ArrayList<>();
        for (var entry : names.entrySet()) {
            for (var node : terminalsWithText(entry.getKey())) {
                int type = node.getSymbol().getType();
                boolean matches = isIdentifierOf(node, GLSLParser.RULE_function_prototype, GLSLParser.RULE_variable_identifier, -1)
                        || ((type == GLSLParser.TEXTURE2D || type == GLSLParser.TEXTURE3D)
                            && node.getParent() instanceof GLSLParser.Type_specifier_nonarrayContext);
                if (matches) {
                    nodes.add(node);
                    replacements.add(entry.getValue());
                }
            }
        }
        for (int i = 0; i < nodes.size(); i++) {
            var node = nodes.get(i);
            if (node.getSymbol() instanceof CommonToken cToken) {
                setTokenText(node, cToken, replacements.get(i));
            }
        }
    }

    public void renameAndWrapShadow(String oldName, String newName) {
        String callStart = oldName + "(";
        List<GLSLParser.Postfix_expressionContext> calls = new ArrayList<>();
        for (var terminal : terminalsWithText(oldName)) {
            forEachContextStartingWith(terminal, ctx -> {
                if (ctx instanceof GLSLParser.Postfix_expressionContext expr && expr.function_call_parameters() != null
                        && expr.getText().startsWith(callStart)) {
                    calls.add(expr);
                }
            });
        }
        for (var call : calls) {
            wrapInVec4(call);
        }
        renameFunctionCall(oldName, newName);
    }

    private static final String WRAP_PLACEHOLDER = "iris_wrapPlaceholder";

    /**
     * Replaces the expression with vec4(expression), moving the existing expression into the new call rather than
     * printing and parsing it again.
     */
    private void wrapInVec4(GLSLParser.Postfix_expressionContext expr) {
        var wrapper = ShaderParser.parseSnippet("vec4(" + WRAP_PLACEHOLDER + ")", GLSLParser::postfix_expression);

        // The placeholder is parsed as postfix_expression > primary_expression > variable_identifier, which is where
        // a postfix_expression argument goes.
        ParseTree placeholder = firstLeaf(wrapper.function_call_parameters());
        while (!(placeholder instanceof GLSLParser.Postfix_expressionContext)) {
            placeholder = placeholder.getParent();
        }

        // Re-added below together with the wrapper, which leaves the indexes in the same order as replacing the
        // expression with a freshly parsed copy would.
        removeNode(expr);

        var parent = expr.getParent();
        parent.children.set(parent.children.indexOf(expr), wrapper);
        wrapper.parent = parent;

        var placeholderParent = (ParserRuleContext) placeholder.getParent();
        placeholderParent.children.set(placeholderParent.children.indexOf(placeholder), expr);
        expr.parent = placeholderParent;

        scanNode(wrapper);
    }

    public void removeFunction(String name) {
        for (var node : terminalsWithText(name)) {
            if (node.getParent() instanceof GLSLParser.Function_prototypeContext ctx && ctx.IDENTIFIER() == node) {
                if (ctx.getParent().getParent() instanceof GLSLParser.External_declarationContext declarationContext) {
                    declarationContext.getParent().children.remove(declarationContext);
                    removeNode(declarationContext);
                }
            }
        }
    }

    public void removeUnusedFunctions() {
        boolean changed = true;
        while (changed) {
            changed = false;
            Collection<GLSLParser.Function_prototypeContext> functionPrototypes = getContextsForRule(GLSLParser.RULE_function_prototype);
            List<String> functionsToRemove = new ArrayList<>();
            for (var prototype : functionPrototypes) {
                String name = prototype.IDENTIFIER().getText();
                if (!name.equals("main") && !containsCall(name)) {
                    functionsToRemove.add(name);
                }
            }
            for (String name : functionsToRemove) {
                removeFunction(name);
                changed = true;
            }
        }
    }

    public Map<String, List<String>> findConstParameter() {
        Map<String, List<String>> functions = new HashMap<>();
        Collection<GLSLParser.Parameter_declarationContext> parameterDeclarations = getContextsForRule(GLSLParser.RULE_parameter_declaration);
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
        Collection<GLSLParser.Variable_identifierContext> variableIdentifiers = getContextsForRule(GLSLParser.RULE_variable_identifier);
        for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
            nextIdentifier:
            for (var ctx : variableIdentifiers) {
                if (entry.getValue().contains(ctx.IDENTIFIER().getText())) {
                    var parent = ctx.getParent();
                    while (!(parent instanceof GLSLParser.Function_definitionContext definitionContext)) {
                        parent = parent.getParent();
                        if (parent == null) {
                            continue nextIdentifier;
                        }
                    }

                    if (definitionContext.function_prototype().IDENTIFIER().getText().equals(entry.getKey())) {
                        parent = ctx.getParent();
                        while (!(parent instanceof GLSLParser.Single_declarationContext declarationContext)) {
                            parent = parent.getParent();
                            if (parent == null) {
                                continue nextIdentifier;
                            }
                        }

                        entry.getValue().add(declarationContext.typeless_declaration().IDENTIFIER().getText());

                        GLSLParser.Type_qualifierContext typeQualifierContext = declarationContext.fully_specified_type().type_qualifier();
                        if (typeQualifierContext == null || typeQualifierContext.single_type_qualifier(0) == null) {
                            continue nextIdentifier;
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
            String insert = name + " = " + BuiltinFunction.getByType(token.getType()).getInitializer() + ";" ;
            prependMain(insert);
        }
    }

    public void makeOutDeclaration(GLSLParser.Single_declarationContext inDeclarationContext, String name) {
        String insert = ShaderPrinter.getFormattedShader(inDeclarationContext.fully_specified_type()) + name + ";"; //TODO, find a different way to make the out declaration
        insert = insert.replaceFirst("in", "out");
        injectVariable(insert);
    }

    public Map<String, GLSLParser.Single_declarationContext> findQualifiers(int type) {
        Map<String, GLSLParser.Single_declarationContext> nodes = new HashMap<>();
        Collection<GLSLParser.Storage_qualifierContext> storageQualifiers = getContextsForRule(GLSLParser.RULE_storage_qualifier);
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
        Collection<GLSLParser.Assignment_expressionContext> assignmentExpressions = getContextsForRule(GLSLParser.RULE_assignment_expression);
        for (var ctx : assignmentExpressions) {
            if (ctx.unary_expression() != null && (ctx.unary_expression().getText().startsWith(name) || ctx.unary_expression().getText().startsWith(name + "."))) {
                return true;
            }
        }
        return false;
    }

    public void rewriteStructArrays() {
        Collection<GLSLParser.Struct_declarationContext> structDeclarations = getContextsForRule(GLSLParser.RULE_struct_declaration);
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
        Collection<GLSLParser.Storage_qualifierContext> storageQualifiers = getContextsForRule(GLSLParser.RULE_storage_qualifier);
        return storageQualifiers.stream().filter(ctx -> ctx.getChild(0) instanceof TerminalNode).map(ctx -> (TerminalNode) ctx.getChild(0)).collect(Collectors.toList());
    }
}
