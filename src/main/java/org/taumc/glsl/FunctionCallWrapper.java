package org.taumc.glsl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.Map;

public class FunctionCallWrapper extends GLSLParserBaseListener {

    private final String name;
    private final String code;

    public FunctionCallWrapper(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public void enterPostfix_expression(GLSLParser.Postfix_expressionContext ctx) {
        String function = ctx.getText();
        if (ctx.function_call_parameters() != null && function.startsWith(name + "(")) {
            function = code + "(" + function + ")";
            GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(function));
            GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
            var def = parser.postfix_expression();
            var parent = ctx.getParent();
            def.setParent(parent);
            parent.children.set(parent.children.indexOf(ctx), def);
        }
    }

    @Override
    public void enterFunction_call(GLSLParser.Function_callContext ctx) {
        super.enterFunction_call(ctx);
    }
}
