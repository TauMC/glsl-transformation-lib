package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicBoolean;

public class AssigmentChecker extends GLSLCancelableBaseListener {

    private final String name;
    private final AtomicBoolean atomicBoolean;

    public AssigmentChecker(String name, AtomicBoolean atomicBoolean) {
        this.name = name;
        this.atomicBoolean = atomicBoolean;
    }

    @Override
    public void enterAssignment_expression(GLSLParser.Assignment_expressionContext ctx) {
        if (ctx.unary_expression() != null && (ctx.unary_expression().getText().startsWith(this.name) || ctx.unary_expression().getText().startsWith(this.name + "."))) {
            atomicBoolean.set(true);
            keepWalking = false;
        }
    }
}
