package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicInteger;

public class TypeFinder extends GLSLCancelableBaseListener {

    private final String name;
    private final AtomicInteger type;

    public TypeFinder(String name, AtomicInteger type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void enterSingle_declaration(GLSLParser.Single_declarationContext ctx) {
        if (ctx.typeless_declaration() == null) {
            return;
        }
        if (ctx.typeless_declaration().IDENTIFIER().getSymbol() instanceof CommonToken cToken) {
            if (cToken.getText().equals(name)) {
                if (ctx.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node) {
                    if (node.getSymbol() instanceof CommonToken t) {
                        type.set(t.getType());
                        keepWalking = false;
                    }
                }
            }
        }
        if (ctx.getParent() instanceof GLSLParser.Init_declarator_listContext listContext) {
            for (var entry : listContext.typeless_declaration()) {
                if (entry.IDENTIFIER().getSymbol() instanceof CommonToken cToken) {
                    if (cToken.getText().equals(name)) {
                        if (ctx.fully_specified_type().type_specifier().type_specifier_nonarray().getChild(0) instanceof TerminalNode node) {
                            if (node.getSymbol() instanceof CommonToken t) {
                                type.set(t.getType());
                                keepWalking = false;
                            }
                        }
                    }
                }
            }
        }
    }
}
