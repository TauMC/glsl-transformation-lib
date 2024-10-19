package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicReference;

public class FunctionInjector extends GLSLParserBaseListener {
    private final AtomicReference<GLSLParser.External_declarationContext> atomic;

    public FunctionInjector(AtomicReference<GLSLParser.External_declarationContext> atomic) {
        this.atomic = atomic;
    }

    @Override
    public void enterFunction_definition(GLSLParser.Function_definitionContext ctx) {
        if (ctx.getParent() instanceof GLSLParser.External_declarationContext list) {
            if (atomic.get() == null) {
                atomic.set(list);
            }
        }
    }
}
