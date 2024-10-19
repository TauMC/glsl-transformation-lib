package org.taumc.glsl;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Extended version of ANTLR's {@link org.antlr.v4.runtime.tree.ParseTreeWalker} that can
 * exit the walk early.
 */
public class FastTreeWalker {
    public static void walk(GLSLCancelableBaseListener listener, ParseTree t) {
        if(!listener.shouldKeepWalking()) {
            return;
        }
        if (t instanceof ErrorNode error) {
            listener.visitErrorNode(error);
        } else if (t instanceof TerminalNode terminal) {
            listener.visitTerminal(terminal);
        } else {
            RuleNode r = (RuleNode)t;
            enterRule(listener, r);
            if(listener.shouldKeepWalking()) {
                int n = r.getChildCount();
                for (int i = 0; i<n; i++) {
                    walk(listener, r.getChild(i));
                    if(!listener.shouldKeepWalking()) {
                        break;
                    }
                }
            }
            exitRule(listener, r);
        }
    }

    private static void enterRule(ParseTreeListener listener, RuleNode r) {
        ParserRuleContext ctx = (ParserRuleContext)r.getRuleContext();
        listener.enterEveryRule(ctx);
        ctx.enterRule(listener);
    }

    private static void exitRule(ParseTreeListener listener, RuleNode r) {
        ParserRuleContext ctx = (ParserRuleContext)r.getRuleContext();
        ctx.exitRule(listener);
        listener.exitEveryRule(ctx);
    }
}
