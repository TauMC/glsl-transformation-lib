package org.taumc.glsl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

public class ReplaceExpression extends GLSLParserBaseListener {

    private final GLSLParser.Binary_expressionContext oldExpression;
    private final String newExpression;

    public ReplaceExpression(String oldExpression, String newExpression) {
        this.newExpression = newExpression;
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(oldExpression));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        this.oldExpression = parser.binary_expression();

    }

    @Override
    public void enterBinary_expression(GLSLParser.Binary_expressionContext ctx) {
        if (ctx.getText().equals(oldExpression.getText())) {
            GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(newExpression));
            GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
            var expr = parser.binary_expression();
            expr.parent = ctx.getParent();
            int i = ctx.getParent().children.indexOf(ctx);
            ctx.getParent().children.set(i, expr);
        } else if (ctx.getText().startsWith(oldExpression.getText())) {
            if (ctx.unary_expression() != null) {
                if (ctx.unary_expression().postfix_expression() != null) {
                    if (ctx.unary_expression().postfix_expression().postfix_expression() != null) {
                        var postfix = ctx.unary_expression().postfix_expression().postfix_expression();
                        while (postfix.getText().startsWith(oldExpression.getText()) && !postfix.getText().equals(oldExpression.getText())) {
                            if (postfix.postfix_expression() != null) {
                                postfix = postfix.postfix_expression();
                            } else {
                                break;
                            }
                        }
                        if (postfix.getText().equals(oldExpression.getText())) {
                            GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(newExpression));
                            GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
                            var expr = parser.postfix_expression();
                            expr.parent = postfix.getParent();
                            int i = postfix.getParent().children.indexOf(postfix);
                            postfix.getParent().children.set(i, expr);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void enterVariable_identifier(GLSLParser.Variable_identifierContext ctx) {
        if (ctx.getText().equals(oldExpression.getText())) {
            GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(newExpression));
            GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
            var expr = parser.postfix_expression();
            var parent = ctx.getParent().getParent().getParent();
            int i = parent.children.indexOf(ctx.getParent().getParent());
            parent.children.set(i, expr);
        }
    }
}
