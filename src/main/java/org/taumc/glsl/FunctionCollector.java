package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;

public class FunctionCollector extends GLSLParserBaseListener {

    private final List<String> functions;

    FunctionCollector(List<String> functions) {
        this.functions = functions;
    }

    @Override
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        this.functions.add(ctx.IDENTIFIER().getText());
    }
}
