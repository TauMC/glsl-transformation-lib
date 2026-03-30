package org.taumc.glsl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    /**
     * removeConstAssignment must strip const from locals initialized with const
     * parameters. GLSL 330 core requires const initializers to be compile-time
     * constant expressions; function parameters do not qualify.
     */
    @Test
    void removeConstAssignmentStripsConstFromLocalInitializedWithConstParam() {
        String shader = "vec3 fn(vec3 d, const float p) { const float K = 0.5 * p; return d * K; }"
            + "void main() { fn(vec3(1.0), 1.0); }";

        String result = transform(shader, Transformer::removeConstAssignment);

        assertTrue(result.contains("float K"), "K declaration should exist");
        assertFalse(result.contains("const float K"), "const should be stripped from K");
    }

    /**
     * After stripping const from a variable, add it to the tracked list. Later iterations may
     * find it used in an expression (not a declaration). The method must not return, it must
     * continue and also strip const from Y.
     */
    @Test
    void removeConstAssignmentDoesNotAbortOnLaterUsage() {
        String shader = "vec3 fn(const float a, const float b) {"
            + "const float X = 0.5 * a; const float Y = 0.5 * b; return vec3(X + Y); }"
            + "void main() { fn(1.0, 2.0); }";

        String result = transform(shader, Transformer::removeConstAssignment);

        assertFalse(result.contains("const float X"), "const should be stripped from X");
        assertFalse(result.contains("const float Y"), "const should be stripped from Y");
    }

    /**
     * removeUnusedFunctions must transitively remove dead functions. If top() calls
     * mid() calls leaf(), and none are reachable from main(), all three must be
     * removed -- not just top().
     */
    @Test
    void removeUnusedFunctionsTransitiveRemoval() {
        String shader = "float leaf() { return 1.0; }"
            + "float mid(float x) { return leaf() + x; }"
            + "float top() { return mid(2.0); }"
            + "float used() { return 3.0; }"
            + "void main() { used(); }";

        String result = transform(shader, Transformer::removeUnusedFunctions);

        assertFalse(result.contains("leaf"), "leaf should be removed");
        assertFalse(result.contains("mid"), "mid should be removed");
        assertFalse(result.contains("top"), "top should be removed");
        assertTrue(result.contains("used"), "used should survive");
        assertTrue(result.contains("main"), "main should survive");
    }

    /**
     * Combined test matching SEUS Renewed scenario: dead caller keeps callee alive
     * in single-pass removal, then removeConstAssignment must still strip const from
     * the surviving callee's locals. Tests both fixes working together.
     */
    @Test
    void removeUnusedThenConstAssignmentSEUSPattern() {
        // caller() references callee() but is itself unused from main.
        // Single-pass removeUnusedFunctions removes caller but not callee.
        // callee has const params and const locals initialized from them.
        String shader =
            "vec3 callee(vec3 d, const float mie, const float ray) {"
            + "  const float K_R = 0.186 * ray;"
            + "  const float K_M = 0.035 * mie;"
            + "  return d * K_R + d * K_M;"
            + "}"
            + "vec3 caller() { return callee(vec3(1.0), 1.0, 1.0); }"
            + "float used() { return 3.0; }"
            + "void main() { used(); }";

        String result = transform(shader, t -> {
            t.removeUnusedFunctions();
            t.removeConstAssignment();
        });

        // With transitive removal: callee, caller both removed. Test passes.
        // Without transitive removal: callee survives, but const must be stripped.
        // Either outcome is acceptable -- the shader must compile on GLSL 330 core.
        if (result.contains("callee")) {
            // callee survived (single-pass). const must be stripped.
            assertFalse(result.contains("const float K_R"), "const should be stripped from K_R");
            assertFalse(result.contains("const float K_M"), "const should be stripped from K_M");
        }
        // caller must always be removed (unreachable from main directly)
        assertFalse(result.contains("caller"), "caller should be removed");
    }
}
