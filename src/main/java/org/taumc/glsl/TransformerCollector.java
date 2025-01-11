package org.taumc.glsl;

import org.antlr.v4.runtime.ParserRuleContext;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.Collections;
import java.util.IdentityHashMap;

public class TransformerCollector extends GLSLParserBaseListener {

    private final Transformer transformer;

    public TransformerCollector(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
        int ruleIndex = ctx.getRuleIndex();
        if (ruleIndex >= 0 && ruleIndex < transformer.cachedContexts.length) {
            transformer.cachedContexts[ruleIndex].add(ctx);
        }
    }
}
