package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicReference;

public class InjectorPoint extends GLSLParserBaseListener {

    private final Transformer transformer;
    private final boolean function;

    public InjectorPoint(Transformer transformer, boolean function) {
        this.transformer = transformer;
        this.function = function;
    }

    @Override
    public void enterFunction_definition(GLSLParser.Function_definitionContext ctx) {
        if (ctx.getParent() instanceof GLSLParser.External_declarationContext list) {
            transformer.function = list;
            transformer.variable = list;
        }
    }

    @Override
    public void enterStorage_qualifier(GLSLParser.Storage_qualifierContext ctx) {
        if (function) {
            return;
        }
        var parent = ctx.getParent();
        while (!(parent instanceof GLSLParser.External_declarationContext list)) {
            if (parent.getParent() == null) {
                return;
            }
            parent = parent.getParent();
        }
        transformer.variable = list;
    }
}
