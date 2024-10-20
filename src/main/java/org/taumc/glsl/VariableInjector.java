package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicReference;

public class VariableInjector extends GLSLCancelableBaseListener {
    private final AtomicReference<GLSLParser.External_declarationContext> atomic;
    private boolean function = false;

    public VariableInjector(AtomicReference<GLSLParser.External_declarationContext> atomic) {
        this.atomic = atomic;
    }

    @Override
    public void enterFunction_definition(GLSLParser.Function_definitionContext ctx) {
        if (ctx.getParent() instanceof GLSLParser.External_declarationContext list) {
            if (atomic.get() == null) {
                function = true;
                atomic.set(list);
            }
        }
    }

    @Override
    public void enterStorage_qualifier(GLSLParser.Storage_qualifierContext ctx) {
        var parent = ctx.getParent();
        while (!(parent instanceof GLSLParser.External_declarationContext list)) {
            if (parent.getParent() == null) {
                return;
            }
            parent = parent.getParent();
        }
        if (atomic.get() == null || function) {
            function = false;
            atomic.set(list);
            keepWalking = false;
        }
    }
}
