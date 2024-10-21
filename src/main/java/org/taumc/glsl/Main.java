package org.taumc.glsl;

import org.anarres.cpp.CppReader;
import org.anarres.cpp.Preprocessor;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLPreParser;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static String tab = "";

    public static void main(String[] args) throws Exception {
        GLSLLexer lexer = new GLSLLexer(CharStreams.fromReader(new CppReader(new Preprocessor(new File("test.glsl")))));
        GLSLParser parser = new GLSLParser(new CommonTokenStream(lexer));
        GLSLPreParser preParser = new GLSLPreParser(new BufferedTokenStream(lexer));
        System.out.println(getFormattedShader(preParser.translation_unit()));

        parser.setBuildParseTree(true);
        var translationUnit = parser.translation_unit();

        Util.injectVariable(translationUnit, "in ivec2 a_LightCoord2;");
        Util.rename(translationUnit, "a_Color", "rewritten_Color");
        Set<Integer> found = new HashSet<>();
        Util.renameArray(translationUnit, "test", "replaced", found);
        //Util.removeVariable(translationUnit, "_vert_tex_diffuse_coord");
        Util.removeVariable(translationUnit, "testing");
        Util.removeConstAssignment(translationUnit);
        Util.renameFunctionCall(translationUnit, "_vert_init", "newCall");
        Util.renameAndWrapShadow(translationUnit, "function", "wrapped");
        Util.prependMain(translationUnit, "injected = 5;");
        Util.replaceExpression(translationUnit, "vartest", "unint(5)");
        Util.removeUnusedFunctions(translationUnit);
        Util.rewriteStructArrays(translationUnit);
        Util.renameFunctionCall(translationUnit, "texture2D", "texture");

        System.out.println(getFormattedShader(translationUnit));

        ShaderViewerGUI.display(parser, translationUnit);
    }

    public static String getFormattedShader(ParseTree tree) {
        StringBuilder sb = new StringBuilder();
        getFormattedShader(tree, sb);
        return sb.toString();
    }

    private static void getFormattedShader(ParseTree tree, StringBuilder stringBuilder) {
        if (tree instanceof TerminalNode) {
            String text = tree.getText();
            stringBuilder.append(text);
            if (text.equals("{")) {
                stringBuilder.append(" \n\t"); //TODO fix indent
                tab = "\t";
            }
            if (text.equals("}")) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);
                tab = "";
            }
            stringBuilder.append(text.equals(";") ? " \n" + tab : " ");
        } else {
            for(int i = 0; i < tree.getChildCount(); i++) {
                getFormattedShader(tree.getChild(i), stringBuilder);
            }
        }
    }
}
