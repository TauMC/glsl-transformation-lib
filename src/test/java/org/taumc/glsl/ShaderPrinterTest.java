package org.taumc.glsl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShaderPrinterTest {

    private static String format(String input) {
        return new ShaderPrinter(ShaderParser.parseShader(input).full()).getFormattedShader();
    }

    private static String formatPre(String input) {
        return new ShaderPrinter(ShaderParser.parseShader(input).pre()).getFormattedShader();
    }

    @Test
    void formatsDeterministicallyAndReadably() {
        String input = "void main(){float x=1.0;for(int i=0;i<2;i++){x+=i;}if(x>0.0){x=x-1.0;}else{x=0.0;}}";
        String expected = ""
            + "void main() {\n"
            + "    float x = 1.0;\n"
            + "    for (int i = 0; i < 2; i++) {\n"
            + "        x += i;\n"
            + "    }\n"
            + "    if (x > 0.0) {\n"
            + "        x = x - 1.0;\n"
            + "    } else {\n"
            + "        x = 0.0;\n"
            + "    }\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void functionWithParametersAndReturn() {
        String input = "vec4 blend(vec4 a,vec4 b,float t){return mix(a,b,t);}";
        String expected = ""
            + "vec4 blend(vec4 a, vec4 b, float t) {\n"
            + "    return mix(a, b, t);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void globalDeclarationsBeforeFunction() {
        String input = "uniform sampler2D uTex;uniform vec2 uRes;void main(){gl_FragColor=texture2D(uTex,uRes);}";
        String expected = ""
            + "uniform sampler2D uTex;\n"
            + "uniform vec2 uRes;\n"
            + "void main() {\n"
            + "    gl_FragColor = texture2D(uTex, uRes);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void multipleFunctions() {
        String input = "float square(float x){return x*x;}void main(){float y=square(2.0);}";
        String expected = ""
            + "float square(float x) {\n"
            + "    return x * x;\n"
            + "}\n"
            + "void main() {\n"
            + "    float y = square(2.0);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void nestedFunctionCalls() {
        String input = "void main(){float r=sin(cos(x));}";
        String expected = ""
            + "void main() {\n"
            + "    float r = sin(cos(x));\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void constructorCall() {
        String input = "void main(){vec4 c=vec4(1.0,0.0,0.0,1.0);}";
        String expected = ""
            + "void main() {\n"
            + "    vec4 c = vec4(1.0, 0.0, 0.0, 1.0);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void fieldAccess() {
        String input = "void main(){vec4 c=col.rgba;float r=col.r;}";
        String expected = ""
            + "void main() {\n"
            + "    vec4 c = col.rgba;\n"
            + "    float r = col.r;\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void arrayIndexing() {
        String input = "void main(){vec4 c=colors[1];colors[0]=vec4(1.0);}";
        String expected = ""
            + "void main() {\n"
            + "    vec4 c = colors[1];\n"
            + "    colors[0] = vec4(1.0);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void whileLoop() {
        String input = "void main(){int n=0;while(n<10){n=n+1;}}";
        String expected = ""
            + "void main() {\n"
            + "    int n = 0;\n"
            + "    while (n < 10) {\n"
            + "        n = n + 1;\n"
            + "    }\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void doWhileLoop() {
        String input = "void main(){int i=0;do{i++;}while(i<5);}";
        String expected = ""
            + "void main() {\n"
            + "    int i = 0;\n"
            + "    do {\n"
            + "        i++;\n"
            + "    } while (i < 5);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void unaryOperators() {
        String input = "void main(){float a=-x;bool b=!flag;float c=~bits;++i;--j;}";
        String expected = ""
            + "void main() {\n"
            + "    float a = -x;\n"
            + "    bool b = !flag;\n"
            + "    float c = ~bits;\n"
            + "    ++i;\n"
            + "    --j;\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void switchStatement() {
        String input = "void main(){switch(x){case 1:y=1.0;break;case 2:y=2.0;break;default:y=0.0;break;}}";
        String expected = ""
            + "void main() {\n"
            + "    switch(x) {\n"
            + "        case 1:\n"
            + "            y = 1.0;\n"
            + "            break;\n"
            + "        case 2:\n"
            + "            y = 2.0;\n"
            + "            break;\n"
            + "        default:\n"
            + "            y = 0.0;\n"
            + "            break;\n"
            + "    }\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void methodCallOnArray() {
        // arr.length() parses as postfix_expression DOT field_selection -> function_call,
        // the only grammar path that exercises the function_call rule
        String input = "void main(){int n=arr.length();}";
        String expected = ""
            + "void main() {\n"
            + "    int n = arr.length();\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void ifUnbracedThenBracedElse() {
        // unbraced then-branch is indented onto the next line; else aligns with if
        String input = "void main(){if(x>0.0)x=1.0;else{x=0.0;}}";
        String expected = ""
            + "void main() {\n"
            + "    if (x > 0.0)\n"
            + "        x = 1.0;\n"
            + "    else {\n"
            + "        x = 0.0;\n"
            + "    }\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void ifWithoutElse() {
        String input = "void main(){float x=1.0;if(x>0.5){x=0.5;}}";
        String expected = ""
            + "void main() {\n"
            + "    float x = 1.0;\n"
            + "    if (x > 0.5) {\n"
            + "        x = 0.5;\n"
            + "    }\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void layoutQualifier() {
        String input = "layout(location=0) in vec4 aPos;void main(){}";
        String expected = ""
            + "layout(location = 0) in vec4 aPos;\n"
            + "void main() {\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void ternaryOperator() {
        String input = "void main(){float y=x>0.5?x:0.0;}";
        String expected = ""
            + "void main() {\n"
            + "    float y = x > 0.5 ? x : 0.0;\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void multipleDeclarators() {
        String input = "void main(){float x,y=0.0;}";
        String expected = ""
            + "void main() {\n"
            + "    float x, y = 0.0;\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void functionPrototype() {
        String input = "float square(float x);float square(float x){return x*x;}void main(){float y=square(2.0);}";
        String expected = ""
            + "float square(float x);\n"
            + "float square(float x) {\n"
            + "    return x * x;\n"
            + "}\n"
            + "void main() {\n"
            + "    float y = square(2.0);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void precisionDeclaration() {
        String input = "precision highp float;void main(){}";
        String expected = ""
            + "precision highp float;\n"
            + "void main() {\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void discardAndContinue() {
        String input = "void main(){for(int i=0;i<10;i++){if(i==5){continue;}}discard;}";
        String expected = ""
            + "void main() {\n"
            + "    for (int i = 0; i < 10; i++) {\n"
            + "        if (i == 5) {\n"
            + "            continue;\n"
            + "        }\n"
            + "    }\n"
            + "    discard;\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void structDeclaration() {
        String input = "struct Light{vec3 pos;float intensity;};void main(){}";
        String expected = ""
            + "struct Light {\n"
            + "    vec3 pos;\n"
            + "    float intensity;\n"
            + "};\n"
            + "void main() {\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void interfaceBlock() {
        String input = "uniform LightBlock{vec3 pos;float intensity;}light;void main(){}";
        String expected = ""
            + "uniform LightBlock {\n"
            + "    vec3 pos;\n"
            + "    float intensity;\n"
            + "} light;\n"
            + "void main() {\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void layoutInterfaceBlock() {
        String input = "layout(std140) uniform Block{vec4 color;float alpha;}uBlock;void main(){}";
        String expected = ""
            + "layout(std140) uniform Block {\n"
            + "    vec4 color;\n"
            + "    float alpha;\n"
            + "} uBlock;\n"
            + "void main() {\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void functionParameterQualifiers() {
        String input = "void f(in float x,out float y,inout vec3 z,const float w){y=x+w;z.x=y;}";
        String expected = ""
            + "void f(in float x, out float y, inout vec3 z, const float w) {\n"
            + "    y = x + w;\n"
            + "    z.x = y;\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void interpolationAndStorageQualifiers() {
        String input = "flat out vec4 color;const float PI=3.14159;void main(){}";
        String expected = ""
            + "flat out vec4 color;\n"
            + "const float PI = 3.14159;\n"
            + "void main() {\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void directiveVersion() {
        assertEquals("#version 330 core\n", formatPre("#version 330 core\nvoid main(){}"));
    }

    @Test
    void directiveExtension() {
        assertEquals("#extension GL_ARB_gpu_shader5 : require\n",
                formatPre("#extension GL_ARB_gpu_shader5 : require\nvoid main(){}"));
    }

    @Test
    void directiveDefine() {
        assertEquals("#define MY_CONST 1.0\n", formatPre("#define MY_CONST 1.0\nvoid main(){}"));
    }

    @Test
    void directiveMultiple() {
        String input = "#version 330 core\n#extension GL_ARB_gpu_shader5 : require\nvoid main(){}";
        String expected = "#version 330 core\n#extension GL_ARB_gpu_shader5 : require\n";
        assertEquals(expected, formatPre(input));
    }

    @Test
    void directivePragma() {
        assertEquals("#pragma optimize(on)\n", formatPre("#pragma optimize(on)\nvoid main(){}"));
    }

    @Test
    void directiveIfdef() {
        String input = "#ifdef MY_FLAG\n#define FOO 1\n#endif\nvoid main(){}";
        String expected = "#ifdef MY_FLAG\n#define FOO 1\n#endif\n";
        assertEquals(expected, formatPre(input));
    }

    @Test
    void elseIfChain() {
        String input = "void main(){if(x<0.0){x=0.0;}else if(x>1.0){x=1.0;}else{x=0.5;}}";
        String expected = ""
            + "void main() {\n"
            + "    if (x < 0.0) {\n"
            + "        x = 0.0;\n"
            + "    } else if (x > 1.0) {\n"
            + "        x = 1.0;\n"
            + "    } else {\n"
            + "        x = 0.5;\n"
            + "    }\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void parenthesisedSubExpression() {
        String input = "void main(){float r=1.0f/(a-b);}";
        String expected = ""
            + "void main() {\n"
            + "    float r = 1.0f / (a - b);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }

    @Test
    void returnWithParens() {
        String input = "float f(float x){return(x+1.0);}";
        String expected = ""
            + "float f(float x) {\n"
            + "    return (x + 1.0);\n"
            + "}\n";

        assertEquals(expected, format(input));
    }
}
