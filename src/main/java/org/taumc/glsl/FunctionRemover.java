package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FunctionRemover extends GLSLCancelableBaseListener {

    private final String name;
    private final AtomicReference<GLSLParser.External_declarationContext> function;

    public FunctionRemover(String name, AtomicReference<GLSLParser.External_declarationContext> function) {
        this.name = name;
        this.function = function;
    }

    @Override
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        if (ctx.IDENTIFIER().getText().equals(this.name)) {
            if (ctx.getParent().getParent() instanceof GLSLParser.External_declarationContext declarationContext) {
                function.set(declarationContext);
                keepWalking = false;
            }
        }
    }
}
