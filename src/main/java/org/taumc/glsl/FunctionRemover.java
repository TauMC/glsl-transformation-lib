package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FunctionRemover extends GLSLCancelableBaseListener {

    private final List<String> names;
    private final List<GLSLParser.External_declarationContext> functions;

    public FunctionRemover(List<String> names, List<GLSLParser.External_declarationContext> function) {
        this.names = names;
        this.functions = function;
    }

    @Override
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        if (this.names.contains(ctx.IDENTIFIER().getText())) {
            if (ctx.getParent().getParent() instanceof GLSLParser.External_declarationContext declarationContext) {
                functions.add(declarationContext);
                if (functions.size() == names.size()) {
                    keepWalking = false;
                }
            }
        }
    }
}
