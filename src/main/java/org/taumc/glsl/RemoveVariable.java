package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RemoveVariable extends GLSLCancelableBaseListener {
    private final String code;
    private final AtomicReference<ParserRuleContext> top;

    public RemoveVariable(String code, AtomicReference<ParserRuleContext> top) {
        this.code = code;
        this.top = top;
    }

    @Override
    public void exitTypeless_declaration(GLSLParser.Typeless_declarationContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            Token token = ctx.IDENTIFIER().getSymbol();
            if(token instanceof CommonToken cToken) {
                if(code.equals(cToken.getText())) {
                    if (ctx.getParent() instanceof GLSLParser.Single_declarationContext) {
                        if (ctx.getParent().getParent() instanceof GLSLParser.Init_declarator_listContext list) {
                            if (!list.typeless_declaration().isEmpty()) {
                                var entry = list.typeless_declaration(0);
                                cToken.setText(entry.getText());
                                top.set(entry);
                                keepWalking = false;
                            }
                        } else {
                            top.set(ctx.getParent());
                        }
                    }
                    else if (ctx.getParent() instanceof GLSLParser.Init_declarator_listContext list) {
                        top.set(ctx);
                        keepWalking = false;
                    }
                }
            }
        }
    }
}
