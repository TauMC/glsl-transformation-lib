package org.taumc.glsl;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class IdentifierCollector extends GLSLCancelableBaseListener {
    private final Predicate<String> identifierConsumer;

    /**
     * Construct a new identifier collector.
     * @param identifierConsumer Called with each identifier, return false to stop walking the tree
     */
    public IdentifierCollector(Predicate<String> identifierConsumer) {
        this.identifierConsumer = identifierConsumer;
    }

    private void handleIdentifier(TerminalNode node) {
        Token token = node.getSymbol();
        if(token instanceof CommonToken cToken) {
            if(!identifierConsumer.test(cToken.getText())) {
                keepWalking = false;
            }
        }
    }

    @Override
    public void enterVariable_identifier(GLSLParser.Variable_identifierContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            handleIdentifier(ctx.IDENTIFIER());
        }
    }
}
