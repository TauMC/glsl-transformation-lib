package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;
import java.util.Map;

public class QualifierFinder extends GLSLParserBaseListener {

    private final int type;
    private final Map<String, GLSLParser.Single_declarationContext> nodes;

    public QualifierFinder(int type, Map<String, GLSLParser.Single_declarationContext> nodes) {
        this.type = type;
        this.nodes = nodes;
    }

    @Override
    public void enterStorage_qualifier(GLSLParser.Storage_qualifierContext ctx) {
        if (ctx.children.get(0) instanceof TerminalNode node &&
                node.getSymbol() instanceof CommonToken token &&
                token.getType() == type) {
            var parent = node.getParent();
            while (!(parent instanceof GLSLParser.Single_declarationContext declarationContext)) {
                parent = parent.getParent();
                if (parent == null) {
                    return;
                }
            }

            nodes.put(declarationContext.typeless_declaration().IDENTIFIER().getText(), declarationContext);

            if (declarationContext.getParent() instanceof GLSLParser.Init_declarator_listContext listContext) {
                if (listContext.typeless_declaration() != null) {
                    for (var entry : listContext.typeless_declaration()) {
                        nodes.put(entry.IDENTIFIER().getText(), declarationContext);
                    }
                }
            }
        }
    }
}
