package org.taumc.glsl;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLPreParser;

public class ShaderParser {
    public record ParsedShader(GLSLPreParser.Translation_unitContext pre, GLSLParser.Translation_unitContext full) {}

    private static void configureNoError(Parser parser) {
        parser.setErrorHandler(new BailErrorStrategy());
        parser.removeErrorListeners();
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
    }

    private static void configureError(Parser parser) {
        parser.setErrorHandler(new DefaultErrorStrategy());
        parser.addErrorListener(ConsoleErrorListener.INSTANCE);
        parser.getInterpreter().setPredictionMode(PredictionMode.LL);
    }

    private static ParsedShader tryParse(GLSLLexer lexer, GLSLPreParser preParser, GLSLParser parser) {
        var pre = preParser.translation_unit();
        lexer.reset();
        parser.reset();
        var full = parser.translation_unit();
        return new ParsedShader(pre, full);
    }

    public static ParsedShader parseShader(String shader) {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromString(shader));
        GLSLPreParser preParser = new GLSLPreParser(new BufferedTokenStream(lexer));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        preParser.setBuildParseTree(true);
        parser.setBuildParseTree(true);
        configureNoError(parser);
        configureNoError(preParser);
        try {
            return tryParse(lexer, preParser, parser);
        } catch (Exception e) {
            lexer.reset();
            parser.reset();
            preParser.reset();
            configureError(parser);
            configureError(preParser);
            return tryParse(lexer, preParser, parser);
        }
    }
}
