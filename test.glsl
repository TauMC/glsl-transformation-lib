// This is just a test input file for the shader parsing, the license is LGPL3 from Sodium/Embeddium.

uniform float framemod8;
// The position of the vertex around the model origin
vec3 _vert_position;

// The block texture coordinate of the vertex
vec2 _vert_tex_diffuse_coord, testing;

// The light texture coordinate of the vertex
ivec2 _vert_tex_light_coord;

// The color of the vertex
vec4 _vert_color;

// The index of the draw command which this vertex belongs to
uint _draw_id;

// The material bits for the primitive
uint _material_params;

#ifdef USE_VERTEX_COMPRESSION
in uvec4 a_PosId;
in vec4 a_Color;
in vec2 a_TexCoord;
in ivec2 a_LightCoord;

#if !defined(VERT_POS_SCALE)
#error "VERT_POS_SCALE not defined"
#elif !defined(VERT_POS_OFFSET)
#error "VERT_POS_OFFSET not defined"
#elif !defined(VERT_TEX_SCALE)
#error "VERT_TEX_SCALE not defined"
#endif

void _vert_init() {
    _vert_position = (vec3(a_PosId.xyz) * VERT_POS_SCALE + VERT_POS_OFFSET);
    _vert_tex_diffuse_coord = (a_TexCoord * VERT_TEX_SCALE);
    _vert_tex_light_coord = a_LightCoord;
    _vert_color = a_Color;

    _draw_id = (a_PosId.w >> 8u) & 0xFFu;
    _material_params = (a_PosId.w >> 0u) & 0xFFu;
}

#else

in vec3 a_PosId, a_PosId2;
in vec4 a_Color;
in vec2 a_TexCoord;
in uint a_LightCoord;

out VertexData {
    vec2 coord;
    vec4 color;
} fsh;

void _vert_init(const vec2 vector) {
    const vec2 storagetest = vector;
    const vec2 test2 = vector;
    _vert_position = a_PosId;
    _vert_tex_diffuse_coord = a_TexCoord;
    _vert_color = a_Color;

    uint packed_draw_params = (a_LightCoord & 0xFFFFu);
    // Vertex Material
    _material_params = (packed_draw_params) & 0xFFu;

    // Vertex Mesh ID
    _draw_id  = (packed_draw_params >> 8) & 0xFFu;
    test[5] = function(uvec2(ivec2(0),8), uvec2(0)).x;

    // Vertex Light
    _vert_tex_light_coord = ivec2((uvec2((a_LightCoord >> 16) & 0xFFFFu) >> uvec2(0, 8)) & uvec2(0xFFu));
}

vec4 texture2D_POMSwitch(sampler2D textures2D,
    vec2 lightmapCoord,
    vec4 dcdxdcdy,
    bool ifPOM
) {

}


void main() {
    if (vartest == test) {
        test = 4;
    }
    const float test = _vert_init(vec2(1,1));

    if (vartest > gl_TextureMatrix[0][3]) {
        test = 4;
    }

    if (vartest > test2[5]) {
        test = 4;
    }

    if (vartest == uint(5)) {
        test = 4;
    }

    if (test == vartest.xy) {

    }

    if (test == vec4(_vert_tex_diffuse_coord, 0.0, 1.0).xy) {

    }
    shadowCol = texture2D ( shadowcolor0 , shadowPos . st );
    _vert_init();
}

void removeMe() {

}

uniform sampler2D twodimsampler;

void myTestFunctionWithTex2D() {
    vec2 vec = vec2(0.05, 0.05);
    float myothertest = someRandomFunc(twodimsampler, vec).r;
    float mytest = texture2D(twodimsampler, vec).r;
    float value = pow2(texture2D(twodimsampler, vec).r * pow1_5(texture2D(twodimsampler, vec * 0.5).r));
}

#endif
