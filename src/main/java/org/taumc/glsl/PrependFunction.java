package org.taumc.glsl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

public class PrependFunction extends GLSLParserBaseListener {

    private final String name;
    private final GLSLParser.StatementContext insert;

    public PrependFunction(String name, String code) {
        this.name = name;
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(code));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        this.insert = parser.statement();
    }

    @Override
    public void enterFunction_definition(GLSLParser.Function_definitionContext ctx) {
        if (ctx.function_prototype().IDENTIFIER().getText().equals(name)) {
            ctx.compound_statement_no_new_scope().statement_list().children.add(0, insert);
        }
    }
}
