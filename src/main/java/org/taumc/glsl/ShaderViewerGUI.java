package org.taumc.glsl;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.taumc.glsl.grammar.GLSLParser;

import javax.swing.*;
import java.util.Arrays;

public class ShaderViewerGUI {
    public static void display(GLSLParser parser, ParseTree tree) {
        JFrame frame = new JFrame("Shader");
        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewer.open();
    }
}
