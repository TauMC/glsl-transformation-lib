package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;

public class VariableCollector extends GLSLParserBaseListener {

    private List<String> variables;

    public VariableCollector(List<String> variables) {
        this.variables = variables;
    }

    private void handleIdentifier(TerminalNode node) {
        Token token = node.getSymbol();
        if(token instanceof CommonToken cToken) {
            variables.add(cToken.getText());
        }
    }

    @Override
    public void enterTypeless_declaration(GLSLParser.Typeless_declarationContext ctx) {
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
}
