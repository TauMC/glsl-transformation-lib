package org.taumc.glsl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ShaderPrinter {
    public static String tab = "";

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
