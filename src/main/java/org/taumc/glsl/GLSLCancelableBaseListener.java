package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParserBaseListener;

public class GLSLCancelableBaseListener extends GLSLParserBaseListener {
    protected boolean keepWalking = true;

    public final boolean shouldKeepWalking() {
        return keepWalking;
    }
}
