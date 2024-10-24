package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.Map;

public class ExpressionRenamer extends GLSLParserBaseListener {
    private final Map<String, String> replacements;

    public ExpressionRenamer(Map<String, String> replacements) {
        this.replacements = replacements;
    }

    private void handleIdentifier(TerminalNode node) {
        Token token = node.getSymbol();
        if(token instanceof CommonToken cToken) {
            String replacement = replacements.get(cToken.getText());
            if(replacement != null) {
                cToken.setText(replacement);
            }
        }
    }

    @Override
    public void enterVariable_identifier(GLSLParser.Variable_identifierContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            handleIdentifier(ctx.IDENTIFIER());
        }
    }

    @Override
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            handleIdentifier(ctx.IDENTIFIER());
        }
    }


    @Override
    public void enterType_specifier_nonarray(GLSLParser.Type_specifier_nonarrayContext ctx) {
        if (ctx.TEXTURE2D() != null) {
            handleIdentifier(ctx.TEXTURE2D());
        } else if (ctx.TEXTURE3D() != null) {
            handleIdentifier(ctx.TEXTURE3D());
        }
    }
}
