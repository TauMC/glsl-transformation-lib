package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class HasVariable extends GLSLParserBaseListener {

    private final String name;
    private AtomicBoolean atomicBoolean;

    public HasVariable(String name, AtomicBoolean atomicBoolean) {
        this.name = name;
        this.atomicBoolean = atomicBoolean;
    }

    private void handleIdentifier(TerminalNode node) {
        Token token = node.getSymbol();
        if(token instanceof CommonToken cToken) {
            if (name.equals(cToken.getText())) {
                atomicBoolean.set(true);
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
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            handleIdentifier(ctx.IDENTIFIER());
        }
    }
}
