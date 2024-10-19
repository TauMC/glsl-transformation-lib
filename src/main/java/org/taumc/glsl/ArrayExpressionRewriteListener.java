package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayExpressionRewriteListener extends GLSLParserBaseListener {

    private final Map<String, String> replacements;
    private final Set<Integer> found;

    public ArrayExpressionRewriteListener(Map<String, String> replacements, Set<Integer> found) {
        this.replacements = replacements;
        this.found = found;
    }

    @Override
    public void enterPostfix_expression(GLSLParser.Postfix_expressionContext ctx) {
        if (ctx.postfix_expression() == null) {
            return;
        }
        if (ctx.postfix_expression().primary_expression() == null) {
            return;
        }
        if (ctx.postfix_expression().primary_expression().variable_identifier() == null) {
            return;
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
