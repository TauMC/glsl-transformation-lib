package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParserBaseListener;
import org.taumc.glsl.grammar.GLSLParser;

import java.util.Map;

public class Renamer extends GLSLParserBaseListener {
    private final Map<String, String> replacements;

    public Renamer(Map<String, String> replacements) {
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
    public void enterTypeless_declaration(GLSLParser.Typeless_declarationContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            handleIdentifier(ctx.IDENTIFIER());
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
        if(ctx.IDENTIFIER() != null) {
            handleIdentifier(ctx.IDENTIFIER());
        }
    }
}
