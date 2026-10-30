package org.taumc.glsl;

import org.junit.jupiter.api.Test;
import org.taumc.glsl.grammar.GLSLParser;

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

    @Test
    void injectIntoEmptyMainBody() {
        String result = transform("void main(){}", t -> {
            t.prependMain("first = 1;");
            t.appendMain("last = 2;");
        });
        int first = result.indexOf("first = 1;");
        int last = result.indexOf("last = 2;");
        assertTrue(first > result.indexOf("{") && first < result.indexOf("}"), "prepended stmt should be inside main");
        assertTrue(last > first && last < result.indexOf("}"), "appended stmt should follow, inside main");
    }

    @Test
    void utilInjectIntoEmptyMainBody() {
        GLSLParser.Translation_unitContext root = ShaderParser.parseShader("void main(){}").full();
        Util.prependMain(root, "first = 1;");
        Util.appendMain(root, "last = 2;");
        String result = new ShaderPrinter(root).getFormattedShader();
        assertTrue(result.indexOf("first = 1;") >= 0, "prepended statement missing");
        assertTrue(result.indexOf("last = 2;") > result.indexOf("first = 1;"), "appended stmt should follow prepended");
    }

    /**
     * Regression test: a name renamed after an earlier replacement must still be found by later replacements and
     * queries, and the old name must no longer be.
     */
    @Test
    void replaceExpressionFindsNamesRenamedAfterEarlierReplacements() {
        String shader = "uniform vec4 a;uniform vec4 b;void main(){vec4 x=a+b;vec4 y=b.xy.xyxy;}";

        String result = transform(shader, t -> {
            t.replaceExpression("a", "vec4(0.0)");
            t.rename("b", "c");
            assertTrue(t.containsCall("c"), "renamed identifier not found");
            assertFalse(t.containsCall("b"), "old name still found after rename");
            t.replaceExpression("c", "vec4(1.0)");
        });

        assertTrue(result.contains("vec4(0.0) + vec4(1.0)"), "renamed identifier was not replaced: " + result);
        assertTrue(result.contains("vec4(1.0).xy.xyxy"), "member access on the replacement is missing: " + result);
    }

    /**
     * Wrapping must keep the call's arguments and leave other calls alone.
     */
    @Test
    void renameAndWrapShadowWrapsOnlyMatchingCalls() {
        String shader = "uniform sampler2DShadow s;void main(){float v=shadow2D(s,vec3(0.0)).x+shadow2DLod(s,vec3(0.0),0.0).x;}";

        String result = transform(shader, t -> t.renameAndWrapShadow("shadow2D", "texture"));

        assertTrue(result.contains("vec4(texture(s, vec3(0.0))).x") || result.contains("vec4(texture(s,vec3(0.0))).x"), "call was not wrapped: " + result);
        assertTrue(result.contains("shadow2DLod(s"), "other call was changed: " + result);
    }

    /**
     * Removing the declaration injected variables are placed before must not break later injections.
     */
    @Test
    void injectVariableAfterRemovingInjectionPoint() {
        String shader = "uniform float b;void main(){}";

        String result = transform(shader, t -> {
            t.injectVariable("uniform float x;");
            t.removeVariable("x");
            t.injectVariable("uniform float y;");
        });

        int yPos = result.indexOf("uniform float y");
        int mainPos = result.indexOf("void main");

        assertTrue(yPos >= 0, "uniform y not found: " + result);
        assertTrue(yPos < mainPos, "uniform y should appear before main: " + result);
        assertFalse(result.contains("float x"), "removed variable is still declared: " + result);
    }

    @Test
    void injectFunctionWithoutFunctions() {
        String result = transform("uniform float b;void f(){}", t -> {
            t.removeFunction("f");
            t.injectFunction("void g(){}");
        });

        assertTrue(result.contains("void g()"), "function was not injected: " + result);
        assertTrue(result.indexOf("uniform float b") < result.indexOf("void g()"), "function should follow the declarations: " + result);
    }

    @Test
    void injectVariableAfterMutateTreeRemovesInjectionPoint() {
        String result = transform("uniform float b;uniform float c;void main(){}", t -> {
            t.injectVariable("uniform float x;");
            t.mutateTree(root -> root.children.remove(0));
            t.injectVariable("uniform float y;");
            assertTrue(t.hasVariable("y"), "injected variable is missing from the index");
            assertFalse(t.hasVariable("x"), "variable removed by mutateTree is still indexed");
        });

        assertTrue(result.contains("uniform float y"), "uniform y not found: " + result);
        assertFalse(result.contains("float x"), "removed variable is still declared: " + result);
    }

    @Test
    void removeFirstVariableOfDeclarationList() {
        String result = transform("float a = 1.0, b = 2.0, c;uniform float d, e[2];void main(){}", t -> {
            t.removeVariable("a");
            t.removeVariable("d");
            assertTrue(t.hasVariable("b"), "remaining variable b is missing from the index");
            assertTrue(t.hasVariable("e"), "remaining variable e is missing from the index");
            assertTrue(t.findType("c") != 0, "type of c was lost");
        });

        String compact = result.replace(" ", "");
        assertTrue(compact.contains("floatb=2.0,c;"), "declaration list was not kept intact: " + result);
        assertTrue(compact.contains("uniformfloate[2];"), "array declarator was not kept intact: " + result);
        assertFalse(compact.contains("1.0"), "removed initializer is still present: " + result);
    }

    @Test
    void renameArraySkipsNonConstantIndices() {
        java.util.Set<Integer> found = new java.util.HashSet<>();
        String result = transform("void main(){gl_FragData[0]=vec4(1.0);for(int i=0;i<2;i++)gl_FragData[i]=vec4(0.0);}",
                t -> t.renameArray("gl_FragData", "iris_FragData", found));

        assertTrue(found.equals(java.util.Collections.singleton(0)), "unexpected indices: " + found);
        assertTrue(result.contains("iris_FragData0"), "constant index was not renamed: " + result);
        assertTrue(result.replace(" ", "").contains("gl_FragData[i]"), "dynamic index was changed: " + result);
    }

    @Test
    void rewriteStructArraysContinuesPastSizedArrays() {
        String result = transform("struct T{float[2] y;};struct U{float[] a, b;};void main(){}", Transformer::rewriteStructArrays);

        String compact = result.replace(" ", "");
        assertTrue(compact.contains("float[2]y;"), "sized array was changed: " + result);
        assertTrue(compact.contains("floata[],b[];"), "unsized array after a sized one was not rewritten: " + result);
    }
}
