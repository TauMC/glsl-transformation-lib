//package org.taumc.glsl;
//
//import org.taumc.glsl.grammar.GLSLParser;
//
//public class Shader {
//
//    public static void commonnTransform(GLSLParser.Translation_unitContext root) {
//        Util.rename(root,"gl_FogFragCoord", "iris_FogFragCoord");
//        //if vertex
//        Util.injectVariable(root, "out float iris_FogFragCoord;");
//        Util.addMain(root, "iris_FogFragCoord = 0.0f;");
//        // if fragment
//        Util.injectVariable(root, "in float iris_FogFragCoord;");
//
//        // if vertex
//        Util.injectVariable(root, "vec4 iris_FrontColor;"); //TODO different inject?
//        Util.rename(root, "gl_FrontColor", "iris_FrontColor");
//
//        //if fragment
//        if (Util.has(root, "gl_FragColor")) {
//            Util.rename(root, "gl_FragColor", "gl_FragData[0]");
//        }
//
//        if (Util.has(root,"gl_TexCoord")) {
//            Util.rename(root, "gl_TexCoord", "irs_texCoords");
//            Util.injectVariable(root, "in vec4 irs_texCoords[3];");
//        }
//
//        if (Util.has(root, "gl_Color")) {
//            Util.rename(root, "gl_Color", "irs_Color");
//            Util.injectVariable(root, "in vec4 irs_Color;");
//        }
//
//        // change gl_FragData[i] to iris_FragDatai
//        Util.renameArray(root, "gl_FragData", "iris_FragData");
//
//        // addition: rename all uses of texture and gcolor to gtexture if it's *not*
//        // used as a function call.
//        // it only does this if they are declared as samplers and makes sure that there
//        // is only one sampler declaration.
//        //?
//
//        Util.rename(root, "gl_Fog", "iris_Fog");
//        Util.injectVariable(root, "uniform float iris_FogDensity;",
//                "uniform float iris_FogStart;",
//                "uniform float iris_FogEnd;",
//                "uniform vec4 iris_FogColor;",
//                "struct iris_FogParameters {" +
//                        "vec4 color;" +
//                        "float density;" +
//                        "float start;" +
//                        "float end;" +
//                        "float scale;" +
//                        "};",
//                "iris_FogParameters iris_Fog = iris_FogParameters(iris_FogColor, iris_FogDensity, iris_FogStart, iris_FogEnd, 1.0 / (iris_FogEnd - iris_FogStart));"
//        );
//
//        Util.renameFunctionCall(root, "texture2D", "texture");
//        Util.renameFunctionCall(root, "texture3D", "texture");
//        Util.renameFunctionCall(root, "texture2DLod", "textureLod");
//        Util.renameFunctionCall(root, "texture3DLod", "textureLod");
//        Util.renameFunctionCall(root, "texture2DGrad", "textureGrad");
//        Util.renameFunctionCall(root, "texture2DGradARB", "textureGrad");
//        Util.renameFunctionCall(root, "texture3DGrad", "textureGrad");
//        Util.renameFunctionCall(root, "texelFetch2D", "texelFetch");
//        Util.renameFunctionCall(root, "texelFetch3D", "texelFetch");
//        Util.renameFunctionCall(root, "textureSize2D", "textureSize");
//
//        Util.renameAndWrapShadow(root, "shadow2D", "texture");
//        Util.renameAndWrapShadow(root, "shadow2DLod", "textureLod");
//
//    }
//
//    public static void CoreTransform(GLSLParser.Translation_unitContext root) {
//
//    }
//}
