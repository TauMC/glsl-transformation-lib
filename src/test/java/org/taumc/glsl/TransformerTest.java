package org.taumc.glsl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransformerTest {

    interface Transformation {
        void apply(Transformer t);
    }

    private static String transform(String shader, Transformation transformation) {
        ShaderParser.ParsedShader parsed = ShaderParser.parseShader(shader);
        transformation.apply(new Transformer(parsed.full()));
        return new ShaderPrinter(parsed.full()).getFormattedShader();
    }

    /**
     * Regression test: when a shader defines a helper function before any variable
     * declarations, injectVariable must still place injections before that function.
     */
    @Test
    void injectVariableBeforeFunctionWhenFunctionPrecedesVariables() {
        // Helper function appears before the in-variable — this is the bug trigger.
        String shader = "vec3 helper(vec3 v){return v;}in float fragCoord;void main(){}";

        String result = transform(shader, t -> {
            t.injectVariable("uniform float uFoo;");
            t.injectFunction("struct S{float x;};");
        });

        int structPos  = result.indexOf("struct S");
        int uniformPos = result.indexOf("uniform float uFoo");
        int helperPos  = result.indexOf("vec3 helper");

        assertTrue(structPos  >= 0, "struct S not found in output");
        assertTrue(uniformPos >= 0, "uniform uFoo not found in output");
        assertTrue(helperPos  >= 0, "helper function not found in output");

        assertTrue(structPos  < helperPos, "struct S should appear before helper()");
        assertTrue(uniformPos < helperPos, "uniform uFoo should appear before helper()");
    }

    /**
     * Sanity check: when variables already precede functions the existing behaviour
     * is preserved — injected variables still go before the first function.
     */
    @Test
    void injectVariableBeforeFunctionWhenVariablesPrecedeFunction() {
        String shader = "uniform float existing;void main(){}";

        String result = transform(shader, t -> t.injectVariable("uniform float injected;"));

        int injectedPos = result.indexOf("uniform float injected");
        int mainPos     = result.indexOf("void main");

        assertTrue(injectedPos >= 0, "injected uniform not found");
        assertTrue(mainPos     >= 0, "main not found");
        assertTrue(injectedPos < mainPos, "injected uniform should appear before main()");
    }

    /**
     * Multiple injectVariable calls with a function-first shader must all land
     * before the function.
     */
    @Test
    void multipleInjectVariablesAllPrecedeFunction() {
        String shader = "vec3 helper(vec3 v){return v;}in float fragCoord;void main(){}";

        String result = transform(shader, t -> {
            t.injectVariable("uniform float uA;");
            t.injectVariable("uniform float uB;");
            t.injectVariable("uniform float uC;");
        });

        int helperPos = result.indexOf("vec3 helper");
        assertTrue(result.indexOf("uniform float uA") < helperPos, "uA should be before helper()");
        assertTrue(result.indexOf("uniform float uB") < helperPos, "uB should be before helper()");
        assertTrue(result.indexOf("uniform float uC") < helperPos, "uC should be before helper()");
    }
}
