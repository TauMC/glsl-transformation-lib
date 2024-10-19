package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLLexer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public enum BuiltinFunction {

    BOOL(GLSLLexer.BOOL, "false"),
    BVEC2(GLSLLexer.BVEC2, "bvec2(false)"),
    BVEC3(GLSLLexer.BVEC3, "bvec3(false)"),
    BVEC4(GLSLLexer.BVEC4, "bvec4(false)"),

    DOUBLE(GLSLLexer.DOUBLE, "0.0d"),
    DVEC2(GLSLLexer.DVEC2, "dvec2(0.0d)"),
    DVEC3(GLSLLexer.DVEC3, "dvec3(0.0d)"),
    DVEC4(GLSLLexer.DVEC4, "dvec4(0.0d)"),
    DMAT2(GLSLLexer.DMAT2, "dmat2(0.0d)"),
    DMAT2X2(GLSLLexer.DMAT2X2, "dmat2x2(0.0d)"),
    DMAT2X3(GLSLLexer.DMAT2X3, "dmat2x3(0.0d)"),
    DMAT2X4(GLSLLexer.DMAT2X4, "dmat2x4(0.0d)"),
    DMAT3(GLSLLexer.DMAT3, "dmat3(0.0d)"),
    DMAT3X2(GLSLLexer.DMAT3X2, "dmat3x2(0.0d)"),
    DMAT3X3(GLSLLexer.DMAT3X3, "dmat3x3(0.0d)"),
    DMAT3X4(GLSLLexer.DMAT3X4, "dmat3x4(0.0d)"),
    DMAT4(GLSLLexer.DMAT4, "dmat4(0.0d)"),
    DMAT4X2(GLSLLexer.DMAT4X2, "dmat4x2(0.0d)"),
    DMAT4X3(GLSLLexer.DMAT4X3, "dmat4x3(0.0d)"),
    DMAT4X4(GLSLLexer.DMAT4X4, "dmat4x4(0.0d)"),

    FLOAT(GLSLLexer.FLOAT, "0.0f"),
    VEC2(GLSLLexer.VEC2, "vec2(0.0f)"),
    VEC3(GLSLLexer.VEC3, "vec3(0.0f)"),
    VEC4(GLSLLexer.VEC4, "vec4(0.0f)"),
    MAT2(GLSLLexer.MAT2, "mat2(0.0f)"),
    MAT2X2(GLSLLexer.MAT2X2, "mat2x2(0.0f)"),
    MAT2X3(GLSLLexer.MAT2X3, "mat2x3(0.0f)"),
    MAT2X4(GLSLLexer.MAT2X4, "mat2x4(0.0f)"),
    MAT3(GLSLLexer.MAT3, "mat3(0.0f)"),
    MAT3X2(GLSLLexer.MAT3X2, "mat3x2(0.0f)"),
    MAT3X3(GLSLLexer.MAT3X3, "mat3x3(0.0f)"),
    MAT3X4(GLSLLexer.MAT3X4, "mat3x4(0.0f)"),
    MAT4(GLSLLexer.MAT4, "mat4(0.0f)"),
    MAT4X2(GLSLLexer.MAT4X2, "mat4x2(0.0f)"),
    MAT4X3(GLSLLexer.MAT4X3, "mat4x3(0.0f)"),
    MAT4X4(GLSLLexer.MAT4X4, "mat4x4(0.0f)"),

    INT(GLSLLexer.INT, "0"),
    IVEC2(GLSLLexer.IVEC2, "ivec2(0)"),
    IVEC3(GLSLLexer.IVEC3, "ivec3(0)"),
    IVEC4(GLSLLexer.IVEC4, "ivec4(0)"),

    UINT(GLSLLexer.UINT, "0u"),
    UVEC2(GLSLLexer.UVEC2, "uvec2(0u)"),
    UVEC3(GLSLLexer.UVEC3, "uvec3(0u)"),
    UVEC4(GLSLLexer.UVEC4, "uvec4(0u)");

    private final String initializer;
    private final int type;
    private static final Map<Integer, BuiltinFunction> BY_TYPE = Arrays.stream(values()).collect(Collectors.toMap(BuiltinFunction::getType, b-> b));

    BuiltinFunction(int type, String initializer) {
        this.type = type;
        this.initializer = initializer;
    }

    public int getType() {
        return type;
    }

    public String getInitializer() {
        return initializer;
    }

    public static BuiltinFunction getByType(int type) {
        return BY_TYPE.get(type);
    }
}
