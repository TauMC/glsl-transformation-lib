package org.taumc.glsl;

import org.antlr.v4.runtime.ParserRuleContext;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransformerRemover extends GLSLParserBaseListener {

    private final Transformer transformer;
    private final Map<Integer, Set<ParserRuleContext>> batchedRemovals;

    public TransformerRemover(Transformer transformer) {
        this.transformer = transformer;
        this.batchedRemovals = new HashMap<>();
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
        int ruleIndex = ctx.getRuleIndex();
        if (ruleIndex >= 0 && ruleIndex < transformer.cachedContexts.length) {
            this.batchedRemovals.computeIfAbsent(ruleIndex, k -> Collections.newSetFromMap(new IdentityHashMap<>())).add(ctx);
        }
    }

    void performRemovals() {
        this.batchedRemovals.forEach((i, set) -> {
            transformer.cachedContexts[i].removeIf(set::contains);
        });
    }
}
