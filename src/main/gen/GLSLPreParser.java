// Generated from D:/git/glsl-transformation-lib/src/main/antlr/GLSLPreParser.g4 by ANTLR 4.13.1

    package org.taumc.glsl.grammar;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class GLSLPreParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ATOMIC_UINT=1, ATTRIBUTE=2, BOOL=3, BREAK=4, BUFFER=5, BVEC2=6, BVEC3=7, 
		BVEC4=8, CASE=9, CENTROID=10, COHERENT=11, CONST=12, CONTINUE=13, DEFAULT=14, 
		DISCARD=15, DMAT2=16, DMAT2X2=17, DMAT2X3=18, DMAT2X4=19, DMAT3=20, DMAT3X2=21, 
		DMAT3X3=22, DMAT3X4=23, DMAT4=24, DMAT4X2=25, DMAT4X3=26, DMAT4X4=27, 
		DO=28, DOUBLE=29, DVEC2=30, DVEC3=31, DVEC4=32, ELSE=33, FALSE=34, FLAT=35, 
		FLOAT=36, FOR=37, HIGHP=38, IF=39, IIMAGE1D=40, IIMAGE1DARRAY=41, IIMAGE2D=42, 
		IIMAGE2DARRAY=43, IIMAGE2DMS=44, IIMAGE2DMSARRAY=45, IIMAGE2DRECT=46, 
		IIMAGE3D=47, IIMAGEBUFFER=48, IIMAGECUBE=49, IIMAGECUBEARRAY=50, IMAGE1D=51, 
		IMAGE1DARRAY=52, IMAGE2D=53, IMAGE2DARRAY=54, IMAGE2DMS=55, IMAGE2DMSARRAY=56, 
		IMAGE2DRECT=57, IMAGE3D=58, IMAGEBUFFER=59, IMAGECUBE=60, IMAGECUBEARRAY=61, 
		IN=62, INOUT=63, INT=64, INVARIANT=65, ISAMPLER1D=66, ISAMPLER1DARRAY=67, 
		ISAMPLER2D=68, ISAMPLER2DARRAY=69, ISAMPLER2DMS=70, ISAMPLER2DMSARRAY=71, 
		ISAMPLER2DRECT=72, ISAMPLER3D=73, ISAMPLERBUFFER=74, ISAMPLERCUBE=75, 
		ISAMPLERCUBEARRAY=76, ISUBPASSINPUT=77, ISUBPASSINPUTMS=78, ITEXTURE1D=79, 
		ITEXTURE1DARRAY=80, ITEXTURE2D=81, ITEXTURE2DARRAY=82, ITEXTURE2DMS=83, 
		ITEXTURE2DMSARRAY=84, ITEXTURE2DRECT=85, ITEXTURE3D=86, ITEXTUREBUFFER=87, 
		ITEXTURECUBE=88, ITEXTURECUBEARRAY=89, IVEC2=90, IVEC3=91, IVEC4=92, LAYOUT=93, 
		LOWP=94, MAT2=95, MAT2X2=96, MAT2X3=97, MAT2X4=98, MAT3=99, MAT3X2=100, 
		MAT3X3=101, MAT3X4=102, MAT4=103, MAT4X2=104, MAT4X3=105, MAT4X4=106, 
		MEDIUMP=107, NOPERSPECTIVE=108, OUT=109, PATCH=110, PRECISE=111, PRECISION=112, 
		READONLY=113, RESTRICT=114, RETURN=115, SAMPLE=116, SAMPLER=117, SAMPLER1D=118, 
		SAMPLER1DARRAY=119, SAMPLER1DARRAYSHADOW=120, SAMPLER1DSHADOW=121, SAMPLER2D=122, 
		SAMPLER2DARRAY=123, SAMPLER2DARRAYSHADOW=124, SAMPLER2DMS=125, SAMPLER2DMSARRAY=126, 
		SAMPLER2DRECT=127, SAMPLER2DRECTSHADOW=128, SAMPLER2DSHADOW=129, SAMPLER3D=130, 
		SAMPLERBUFFER=131, SAMPLERCUBE=132, SAMPLERCUBEARRAY=133, SAMPLERCUBEARRAYSHADOW=134, 
		SAMPLERCUBESHADOW=135, SAMPLERSHADOW=136, SHARED=137, SMOOTH=138, STRUCT=139, 
		SUBPASSINPUT=140, SUBPASSINPUTMS=141, SUBROUTINE=142, SWITCH=143, TEXTURE1D=144, 
		TEXTURE1DARRAY=145, TEXTURE2D=146, TEXTURE2DARRAY=147, TEXTURE2DMS=148, 
		TEXTURE2DMSARRAY=149, TEXTURE2DRECT=150, TEXTURE3D=151, TEXTUREBUFFER=152, 
		TEXTURECUBE=153, TEXTURECUBEARRAY=154, TRUE=155, UIMAGE1D=156, UIMAGE1DARRAY=157, 
		UIMAGE2D=158, UIMAGE2DARRAY=159, UIMAGE2DMS=160, UIMAGE2DMSARRAY=161, 
		UIMAGE2DRECT=162, UIMAGE3D=163, UIMAGEBUFFER=164, UIMAGECUBE=165, UIMAGECUBEARRAY=166, 
		UINT=167, UNIFORM=168, USAMPLER1D=169, USAMPLER1DARRAY=170, USAMPLER2D=171, 
		USAMPLER2DARRAY=172, USAMPLER2DMS=173, USAMPLER2DMSARRAY=174, USAMPLER2DRECT=175, 
		USAMPLER3D=176, USAMPLERBUFFER=177, USAMPLERCUBE=178, USAMPLERCUBEARRAY=179, 
		USUBPASSINPUT=180, USUBPASSINPUTMS=181, UTEXTURE1D=182, UTEXTURE1DARRAY=183, 
		UTEXTURE2D=184, UTEXTURE2DARRAY=185, UTEXTURE2DMS=186, UTEXTURE2DMSARRAY=187, 
		UTEXTURE2DRECT=188, UTEXTURE3D=189, UTEXTUREBUFFER=190, UTEXTURECUBE=191, 
		UTEXTURECUBEARRAY=192, UVEC2=193, UVEC3=194, UVEC4=195, VARYING=196, VEC2=197, 
		VEC3=198, VEC4=199, VOID=200, VOLATILE=201, WHILE=202, WRITEONLY=203, 
		ADD_ASSIGN=204, AMPERSAND=205, AND_ASSIGN=206, AND_OP=207, BANG=208, CARET=209, 
		COLON=210, COMMA=211, DASH=212, DEC_OP=213, DIV_ASSIGN=214, DOT=215, EQ_OP=216, 
		EQUAL=217, GE_OP=218, INC_OP=219, LE_OP=220, LEFT_ANGLE=221, LEFT_ASSIGN=222, 
		LEFT_BRACE=223, LEFT_BRACKET=224, LEFT_OP=225, LEFT_PAREN=226, MOD_ASSIGN=227, 
		MUL_ASSIGN=228, NE_OP=229, NUMBER_SIGN=230, OR_ASSIGN=231, OR_OP=232, 
		PERCENT=233, PLUS=234, QUESTION=235, RIGHT_ANGLE=236, RIGHT_ASSIGN=237, 
		RIGHT_BRACE=238, RIGHT_BRACKET=239, RIGHT_OP=240, RIGHT_PAREN=241, SEMICOLON=242, 
		SLASH=243, STAR=244, SUB_ASSIGN=245, TILDE=246, VERTICAL_BAR=247, XOR_ASSIGN=248, 
		XOR_OP=249, DOUBLECONSTANT=250, FLOATCONSTANT=251, INTCONSTANT=252, UINTCONSTANT=253, 
		BLOCK_COMMENT=254, LINE_COMMENT=255, LINE_CONTINUATION=256, IDENTIFIER=257, 
		WHITE_SPACE=258, DEFINE_DIRECTIVE=259, ELIF_DIRECTIVE=260, ELSE_DIRECTIVE=261, 
		ENDIF_DIRECTIVE=262, ERROR_DIRECTIVE=263, EXTENSION_DIRECTIVE=264, IF_DIRECTIVE=265, 
		IFDEF_DIRECTIVE=266, IFNDEF_DIRECTIVE=267, LINE_DIRECTIVE=268, PRAGMA_DIRECTIVE=269, 
		UNDEF_DIRECTIVE=270, VERSION_DIRECTIVE=271, SPACE_TAB_0=272, NEWLINE_0=273, 
		MACRO_NAME=274, NEWLINE_1=275, SPACE_TAB_1=276, CONSTANT_EXPRESSION=277, 
		NEWLINE_2=278, ERROR_MESSAGE=279, NEWLINE_3=280, BEHAVIOR=281, EXTENSION_NAME=282, 
		NEWLINE_4=283, SPACE_TAB_2=284, NEWLINE_5=285, MACRO_IDENTIFIER=286, NEWLINE_6=287, 
		SPACE_TAB_3=288, LINE_EXPRESSION=289, NEWLINE_7=290, MACRO_ESC_NEWLINE=291, 
		MACRO_TEXT=292, NEWLINE_8=293, DEBUG=294, NEWLINE_9=295, OFF=296, ON=297, 
		OPTIMIZE=298, SPACE_TAB_5=299, STDGL=300, PROGRAM_TEXT=301, NEWLINE_10=302, 
		SPACE_TAB_6=303, NEWLINE_11=304, NUMBER=305, PROFILE=306, SPACE_TAB_7=307;
	public static final int
		RULE_translation_unit = 0, RULE_compiler_directive = 1, RULE_behavior = 2, 
		RULE_constant_expression = 3, RULE_define_directive = 4, RULE_elif_directive = 5, 
		RULE_else_directive = 6, RULE_endif_directive = 7, RULE_error_directive = 8, 
		RULE_error_message = 9, RULE_extension_directive = 10, RULE_extension_name = 11, 
		RULE_group_of_lines = 12, RULE_if_directive = 13, RULE_ifdef_directive = 14, 
		RULE_ifndef_directive = 15, RULE_line_directive = 16, RULE_line_expression = 17, 
		RULE_macro_esc_newline = 18, RULE_macro_identifier = 19, RULE_macro_name = 20, 
		RULE_macro_text = 21, RULE_macro_text_ = 22, RULE_number = 23, RULE_off = 24, 
		RULE_on = 25, RULE_pragma_debug = 26, RULE_pragma_directive = 27, RULE_pragma_optimize = 28, 
		RULE_profile = 29, RULE_program_text = 30, RULE_stdgl = 31, RULE_undef_directive = 32, 
		RULE_version_directive = 33;
	private static String[] makeRuleNames() {
		return new String[] {
			"translation_unit", "compiler_directive", "behavior", "constant_expression", 
			"define_directive", "elif_directive", "else_directive", "endif_directive", 
			"error_directive", "error_message", "extension_directive", "extension_name", 
			"group_of_lines", "if_directive", "ifdef_directive", "ifndef_directive", 
			"line_directive", "line_expression", "macro_esc_newline", "macro_identifier", 
			"macro_name", "macro_text", "macro_text_", "number", "off", "on", "pragma_debug", 
			"pragma_directive", "pragma_optimize", "profile", "program_text", "stdgl", 
			"undef_directive", "version_directive"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'atomic_uint'", "'attribute'", "'bool'", "'break'", "'buffer'", 
			"'bvec2'", "'bvec3'", "'bvec4'", "'case'", "'centroid'", "'coherent'", 
			"'const'", "'continue'", "'default'", "'discard'", "'dmat2'", "'dmat2x2'", 
			"'dmat2x3'", "'dmat2x4'", "'dmat3'", "'dmat3x2'", "'dmat3x3'", "'dmat3x4'", 
			"'dmat4'", "'dmat4x2'", "'dmat4x3'", "'dmat4x4'", "'do'", "'double'", 
			"'dvec2'", "'dvec3'", "'dvec4'", "'else'", "'false'", "'flat'", "'float'", 
			"'for'", "'highp'", "'if'", "'iimage1D'", "'iimage1DArray'", "'iimage2D'", 
			"'iimage2DArray'", "'iimage2DMS'", "'iimage2DMSArray'", "'iimage2DRect'", 
			"'iimage3D'", "'iimageBuffer'", "'iimageCube'", "'iimageCubeArray'", 
			"'image1D'", "'image1DArray'", "'image2D'", "'image2DArray'", "'image2DMS'", 
			"'image2DMSArray'", "'image2DRect'", "'image3D'", "'imageBuffer'", "'imageCube'", 
			"'imageCubeArray'", "'in'", "'inout'", "'int'", "'invariant'", "'isampler1D'", 
			"'isampler1DArray'", "'isampler2D'", "'isampler2DArray'", "'isampler2DMS'", 
			"'isampler2DMSArray'", "'isampler2DRect'", "'isampler3D'", "'isamplerBuffer'", 
			"'isamplerCube'", "'isamplerCubeArray'", "'isubpassInput'", "'isubpassInputMS'", 
			"'itexture1D'", "'itexture1DArray'", "'itexture2D'", "'itexture2DArray'", 
			"'itexture2DMS'", "'itexture2DMSArray'", "'itexture2DRect'", "'itexture3D'", 
			"'itextureBuffer'", "'itextureCube'", "'itextureCubeArray'", "'ivec2'", 
			"'ivec3'", "'ivec4'", "'layout'", "'lowp'", "'mat2'", "'mat2x2'", "'mat2x3'", 
			"'mat2x4'", "'mat3'", "'mat3x2'", "'mat3x3'", "'mat3x4'", "'mat4'", "'mat4x2'", 
			"'mat4x3'", "'mat4x4'", "'mediump'", "'noperspective'", "'out'", "'patch'", 
			"'precise'", "'precision'", "'readonly'", "'restrict'", "'return'", "'sample'", 
			"'sampler'", "'sampler1D'", "'sampler1DArray'", "'sampler1DArrayShadow'", 
			"'sampler1DShadow'", "'sampler2D'", "'sampler2DArray'", "'sampler2DArrayShadow'", 
			"'sampler2DMS'", "'sampler2DMSArray'", "'sampler2DRect'", "'sampler2DRectShadow'", 
			"'sampler2DShadow'", "'sampler3D'", "'samplerBuffer'", "'samplerCube'", 
			"'samplerCubeArray'", "'samplerCubeArrayShadow'", "'samplerCubeShadow'", 
			"'samplerShadow'", "'shared'", "'smooth'", "'struct'", "'subpassInput'", 
			"'subpassInputMS'", "'subroutine'", "'switch'", "'texture1D'", "'texture1DArray'", 
			"'texture2D'", "'texture2DArray'", "'texture2DMS'", "'texture2DMSArray'", 
			"'texture2DRect'", "'texture3D'", "'textureBuffer'", "'textureCube'", 
			"'textureCubeArray'", "'true'", "'uimage1D'", "'uimage1DArray'", "'uimage2D'", 
			"'uimage2DArray'", "'uimage2DMS'", "'uimage2DMSArray'", "'uimage2DRect'", 
			"'uimage3D'", "'uimageBuffer'", "'uimageCube'", "'uimageCubeArray'", 
			"'uint'", "'uniform'", "'usampler1D'", "'usampler1DArray'", "'usampler2D'", 
			"'usampler2DArray'", "'usampler2DMS'", "'usampler2DMSArray'", "'usampler2DRect'", 
			"'usampler3D'", "'usamplerBuffer'", "'usamplerCube'", "'usamplerCubeArray'", 
			"'usubpassInput'", "'usubpassInputMS'", "'utexture1D'", "'utexture1DArray'", 
			"'utexture2D'", "'utexture2DArray'", "'utexture2DMS'", "'utexture2DMSArray'", 
			"'utexture2DRect'", "'utexture3D'", "'utextureBuffer'", "'utextureCube'", 
			"'utextureCubeArray'", "'uvec2'", "'uvec3'", "'uvec4'", "'varying'", 
			"'vec2'", "'vec3'", "'vec4'", "'void'", "'volatile'", "'while'", "'writeonly'", 
			"'+='", "'&'", "'&='", "'&&'", "'!'", "'^'", "':'", "','", "'-'", "'--'", 
			"'/='", "'.'", "'=='", "'='", "'>='", "'++'", "'<='", "'<'", "'<<='", 
			"'{'", "'['", "'<<'", "'('", "'%='", "'*='", "'!='", null, "'|='", "'||'", 
			"'%'", "'+'", "'?'", "'>'", "'>>='", "'}'", "']'", "'>>'", "')'", "';'", 
			"'/'", "'*'", "'-='", "'~'", "'|'", "'^='", "'^^'", null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "'debug'", null, "'off'", "'on'", "'optimize'", 
			null, "'STDGL'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ATOMIC_UINT", "ATTRIBUTE", "BOOL", "BREAK", "BUFFER", "BVEC2", 
			"BVEC3", "BVEC4", "CASE", "CENTROID", "COHERENT", "CONST", "CONTINUE", 
			"DEFAULT", "DISCARD", "DMAT2", "DMAT2X2", "DMAT2X3", "DMAT2X4", "DMAT3", 
			"DMAT3X2", "DMAT3X3", "DMAT3X4", "DMAT4", "DMAT4X2", "DMAT4X3", "DMAT4X4", 
			"DO", "DOUBLE", "DVEC2", "DVEC3", "DVEC4", "ELSE", "FALSE", "FLAT", "FLOAT", 
			"FOR", "HIGHP", "IF", "IIMAGE1D", "IIMAGE1DARRAY", "IIMAGE2D", "IIMAGE2DARRAY", 
			"IIMAGE2DMS", "IIMAGE2DMSARRAY", "IIMAGE2DRECT", "IIMAGE3D", "IIMAGEBUFFER", 
			"IIMAGECUBE", "IIMAGECUBEARRAY", "IMAGE1D", "IMAGE1DARRAY", "IMAGE2D", 
			"IMAGE2DARRAY", "IMAGE2DMS", "IMAGE2DMSARRAY", "IMAGE2DRECT", "IMAGE3D", 
			"IMAGEBUFFER", "IMAGECUBE", "IMAGECUBEARRAY", "IN", "INOUT", "INT", "INVARIANT", 
			"ISAMPLER1D", "ISAMPLER1DARRAY", "ISAMPLER2D", "ISAMPLER2DARRAY", "ISAMPLER2DMS", 
			"ISAMPLER2DMSARRAY", "ISAMPLER2DRECT", "ISAMPLER3D", "ISAMPLERBUFFER", 
			"ISAMPLERCUBE", "ISAMPLERCUBEARRAY", "ISUBPASSINPUT", "ISUBPASSINPUTMS", 
			"ITEXTURE1D", "ITEXTURE1DARRAY", "ITEXTURE2D", "ITEXTURE2DARRAY", "ITEXTURE2DMS", 
			"ITEXTURE2DMSARRAY", "ITEXTURE2DRECT", "ITEXTURE3D", "ITEXTUREBUFFER", 
			"ITEXTURECUBE", "ITEXTURECUBEARRAY", "IVEC2", "IVEC3", "IVEC4", "LAYOUT", 
			"LOWP", "MAT2", "MAT2X2", "MAT2X3", "MAT2X4", "MAT3", "MAT3X2", "MAT3X3", 
			"MAT3X4", "MAT4", "MAT4X2", "MAT4X3", "MAT4X4", "MEDIUMP", "NOPERSPECTIVE", 
			"OUT", "PATCH", "PRECISE", "PRECISION", "READONLY", "RESTRICT", "RETURN", 
			"SAMPLE", "SAMPLER", "SAMPLER1D", "SAMPLER1DARRAY", "SAMPLER1DARRAYSHADOW", 
			"SAMPLER1DSHADOW", "SAMPLER2D", "SAMPLER2DARRAY", "SAMPLER2DARRAYSHADOW", 
			"SAMPLER2DMS", "SAMPLER2DMSARRAY", "SAMPLER2DRECT", "SAMPLER2DRECTSHADOW", 
			"SAMPLER2DSHADOW", "SAMPLER3D", "SAMPLERBUFFER", "SAMPLERCUBE", "SAMPLERCUBEARRAY", 
			"SAMPLERCUBEARRAYSHADOW", "SAMPLERCUBESHADOW", "SAMPLERSHADOW", "SHARED", 
			"SMOOTH", "STRUCT", "SUBPASSINPUT", "SUBPASSINPUTMS", "SUBROUTINE", "SWITCH", 
			"TEXTURE1D", "TEXTURE1DARRAY", "TEXTURE2D", "TEXTURE2DARRAY", "TEXTURE2DMS", 
			"TEXTURE2DMSARRAY", "TEXTURE2DRECT", "TEXTURE3D", "TEXTUREBUFFER", "TEXTURECUBE", 
			"TEXTURECUBEARRAY", "TRUE", "UIMAGE1D", "UIMAGE1DARRAY", "UIMAGE2D", 
			"UIMAGE2DARRAY", "UIMAGE2DMS", "UIMAGE2DMSARRAY", "UIMAGE2DRECT", "UIMAGE3D", 
			"UIMAGEBUFFER", "UIMAGECUBE", "UIMAGECUBEARRAY", "UINT", "UNIFORM", "USAMPLER1D", 
			"USAMPLER1DARRAY", "USAMPLER2D", "USAMPLER2DARRAY", "USAMPLER2DMS", "USAMPLER2DMSARRAY", 
			"USAMPLER2DRECT", "USAMPLER3D", "USAMPLERBUFFER", "USAMPLERCUBE", "USAMPLERCUBEARRAY", 
			"USUBPASSINPUT", "USUBPASSINPUTMS", "UTEXTURE1D", "UTEXTURE1DARRAY", 
			"UTEXTURE2D", "UTEXTURE2DARRAY", "UTEXTURE2DMS", "UTEXTURE2DMSARRAY", 
			"UTEXTURE2DRECT", "UTEXTURE3D", "UTEXTUREBUFFER", "UTEXTURECUBE", "UTEXTURECUBEARRAY", 
			"UVEC2", "UVEC3", "UVEC4", "VARYING", "VEC2", "VEC3", "VEC4", "VOID", 
			"VOLATILE", "WHILE", "WRITEONLY", "ADD_ASSIGN", "AMPERSAND", "AND_ASSIGN", 
			"AND_OP", "BANG", "CARET", "COLON", "COMMA", "DASH", "DEC_OP", "DIV_ASSIGN", 
			"DOT", "EQ_OP", "EQUAL", "GE_OP", "INC_OP", "LE_OP", "LEFT_ANGLE", "LEFT_ASSIGN", 
			"LEFT_BRACE", "LEFT_BRACKET", "LEFT_OP", "LEFT_PAREN", "MOD_ASSIGN", 
			"MUL_ASSIGN", "NE_OP", "NUMBER_SIGN", "OR_ASSIGN", "OR_OP", "PERCENT", 
			"PLUS", "QUESTION", "RIGHT_ANGLE", "RIGHT_ASSIGN", "RIGHT_BRACE", "RIGHT_BRACKET", 
			"RIGHT_OP", "RIGHT_PAREN", "SEMICOLON", "SLASH", "STAR", "SUB_ASSIGN", 
			"TILDE", "VERTICAL_BAR", "XOR_ASSIGN", "XOR_OP", "DOUBLECONSTANT", "FLOATCONSTANT", 
			"INTCONSTANT", "UINTCONSTANT", "BLOCK_COMMENT", "LINE_COMMENT", "LINE_CONTINUATION", 
			"IDENTIFIER", "WHITE_SPACE", "DEFINE_DIRECTIVE", "ELIF_DIRECTIVE", "ELSE_DIRECTIVE", 
			"ENDIF_DIRECTIVE", "ERROR_DIRECTIVE", "EXTENSION_DIRECTIVE", "IF_DIRECTIVE", 
			"IFDEF_DIRECTIVE", "IFNDEF_DIRECTIVE", "LINE_DIRECTIVE", "PRAGMA_DIRECTIVE", 
			"UNDEF_DIRECTIVE", "VERSION_DIRECTIVE", "SPACE_TAB_0", "NEWLINE_0", "MACRO_NAME", 
			"NEWLINE_1", "SPACE_TAB_1", "CONSTANT_EXPRESSION", "NEWLINE_2", "ERROR_MESSAGE", 
			"NEWLINE_3", "BEHAVIOR", "EXTENSION_NAME", "NEWLINE_4", "SPACE_TAB_2", 
			"NEWLINE_5", "MACRO_IDENTIFIER", "NEWLINE_6", "SPACE_TAB_3", "LINE_EXPRESSION", 
			"NEWLINE_7", "MACRO_ESC_NEWLINE", "MACRO_TEXT", "NEWLINE_8", "DEBUG", 
			"NEWLINE_9", "OFF", "ON", "OPTIMIZE", "SPACE_TAB_5", "STDGL", "PROGRAM_TEXT", 
			"NEWLINE_10", "SPACE_TAB_6", "NEWLINE_11", "NUMBER", "PROFILE", "SPACE_TAB_7"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GLSLPreParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GLSLPreParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Translation_unitContext extends ParserRuleContext {
		public List<Compiler_directiveContext> compiler_directive() {
			return getRuleContexts(Compiler_directiveContext.class);
		}
		public Compiler_directiveContext compiler_directive(int i) {
			return getRuleContext(Compiler_directiveContext.class,i);
		}
		public Translation_unitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translation_unit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterTranslation_unit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitTranslation_unit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitTranslation_unit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Translation_unitContext translation_unit() throws RecognitionException {
		Translation_unitContext _localctx = new Translation_unitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_translation_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NUMBER_SIGN) {
				{
				{
				setState(68);
				compiler_directive();
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compiler_directiveContext extends ParserRuleContext {
		public Define_directiveContext define_directive() {
			return getRuleContext(Define_directiveContext.class,0);
		}
		public Elif_directiveContext elif_directive() {
			return getRuleContext(Elif_directiveContext.class,0);
		}
		public Else_directiveContext else_directive() {
			return getRuleContext(Else_directiveContext.class,0);
		}
		public Endif_directiveContext endif_directive() {
			return getRuleContext(Endif_directiveContext.class,0);
		}
		public Error_directiveContext error_directive() {
			return getRuleContext(Error_directiveContext.class,0);
		}
		public Extension_directiveContext extension_directive() {
			return getRuleContext(Extension_directiveContext.class,0);
		}
		public If_directiveContext if_directive() {
			return getRuleContext(If_directiveContext.class,0);
		}
		public Ifdef_directiveContext ifdef_directive() {
			return getRuleContext(Ifdef_directiveContext.class,0);
		}
		public Ifndef_directiveContext ifndef_directive() {
			return getRuleContext(Ifndef_directiveContext.class,0);
		}
		public Line_directiveContext line_directive() {
			return getRuleContext(Line_directiveContext.class,0);
		}
		public Pragma_directiveContext pragma_directive() {
			return getRuleContext(Pragma_directiveContext.class,0);
		}
		public Undef_directiveContext undef_directive() {
			return getRuleContext(Undef_directiveContext.class,0);
		}
		public Version_directiveContext version_directive() {
			return getRuleContext(Version_directiveContext.class,0);
		}
		public Compiler_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compiler_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterCompiler_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitCompiler_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitCompiler_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compiler_directiveContext compiler_directive() throws RecognitionException {
		Compiler_directiveContext _localctx = new Compiler_directiveContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_compiler_directive);
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				define_directive();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				elif_directive();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(76);
				else_directive();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(77);
				endif_directive();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(78);
				error_directive();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(79);
				extension_directive();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(80);
				if_directive();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(81);
				ifdef_directive();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(82);
				ifndef_directive();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(83);
				line_directive();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(84);
				pragma_directive();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(85);
				undef_directive();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(86);
				version_directive();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BehaviorContext extends ParserRuleContext {
		public TerminalNode BEHAVIOR() { return getToken(GLSLPreParser.BEHAVIOR, 0); }
		public BehaviorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_behavior; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterBehavior(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitBehavior(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitBehavior(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BehaviorContext behavior() throws RecognitionException {
		BehaviorContext _localctx = new BehaviorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_behavior);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(BEHAVIOR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Constant_expressionContext extends ParserRuleContext {
		public TerminalNode CONSTANT_EXPRESSION() { return getToken(GLSLPreParser.CONSTANT_EXPRESSION, 0); }
		public Constant_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterConstant_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitConstant_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitConstant_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Constant_expressionContext constant_expression() throws RecognitionException {
		Constant_expressionContext _localctx = new Constant_expressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_constant_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(CONSTANT_EXPRESSION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Define_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode DEFINE_DIRECTIVE() { return getToken(GLSLPreParser.DEFINE_DIRECTIVE, 0); }
		public Macro_nameContext macro_name() {
			return getRuleContext(Macro_nameContext.class,0);
		}
		public Macro_textContext macro_text() {
			return getRuleContext(Macro_textContext.class,0);
		}
		public Define_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_define_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterDefine_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitDefine_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitDefine_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Define_directiveContext define_directive() throws RecognitionException {
		Define_directiveContext _localctx = new Define_directiveContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_define_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(NUMBER_SIGN);
			setState(94);
			match(DEFINE_DIRECTIVE);
			setState(95);
			macro_name();
			setState(96);
			macro_text();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Elif_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode ELIF_DIRECTIVE() { return getToken(GLSLPreParser.ELIF_DIRECTIVE, 0); }
		public Constant_expressionContext constant_expression() {
			return getRuleContext(Constant_expressionContext.class,0);
		}
		public Group_of_linesContext group_of_lines() {
			return getRuleContext(Group_of_linesContext.class,0);
		}
		public Elif_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elif_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterElif_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitElif_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitElif_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Elif_directiveContext elif_directive() throws RecognitionException {
		Elif_directiveContext _localctx = new Elif_directiveContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elif_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(NUMBER_SIGN);
			setState(99);
			match(ELIF_DIRECTIVE);
			setState(100);
			constant_expression();
			setState(101);
			group_of_lines();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Else_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode ELSE_DIRECTIVE() { return getToken(GLSLPreParser.ELSE_DIRECTIVE, 0); }
		public Group_of_linesContext group_of_lines() {
			return getRuleContext(Group_of_linesContext.class,0);
		}
		public Else_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterElse_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitElse_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitElse_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_directiveContext else_directive() throws RecognitionException {
		Else_directiveContext _localctx = new Else_directiveContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_else_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(NUMBER_SIGN);
			setState(104);
			match(ELSE_DIRECTIVE);
			setState(105);
			group_of_lines();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Endif_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode ENDIF_DIRECTIVE() { return getToken(GLSLPreParser.ENDIF_DIRECTIVE, 0); }
		public Endif_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endif_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterEndif_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitEndif_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitEndif_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Endif_directiveContext endif_directive() throws RecognitionException {
		Endif_directiveContext _localctx = new Endif_directiveContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_endif_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(NUMBER_SIGN);
			setState(108);
			match(ENDIF_DIRECTIVE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Error_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode ERROR_DIRECTIVE() { return getToken(GLSLPreParser.ERROR_DIRECTIVE, 0); }
		public Error_messageContext error_message() {
			return getRuleContext(Error_messageContext.class,0);
		}
		public Error_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterError_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitError_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitError_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Error_directiveContext error_directive() throws RecognitionException {
		Error_directiveContext _localctx = new Error_directiveContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_error_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(NUMBER_SIGN);
			setState(111);
			match(ERROR_DIRECTIVE);
			setState(112);
			error_message();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Error_messageContext extends ParserRuleContext {
		public TerminalNode ERROR_MESSAGE() { return getToken(GLSLPreParser.ERROR_MESSAGE, 0); }
		public Error_messageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error_message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterError_message(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitError_message(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitError_message(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Error_messageContext error_message() throws RecognitionException {
		Error_messageContext _localctx = new Error_messageContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_error_message);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(ERROR_MESSAGE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Extension_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode EXTENSION_DIRECTIVE() { return getToken(GLSLPreParser.EXTENSION_DIRECTIVE, 0); }
		public Extension_nameContext extension_name() {
			return getRuleContext(Extension_nameContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GLSLPreParser.COLON, 0); }
		public BehaviorContext behavior() {
			return getRuleContext(BehaviorContext.class,0);
		}
		public Extension_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterExtension_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitExtension_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitExtension_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Extension_directiveContext extension_directive() throws RecognitionException {
		Extension_directiveContext _localctx = new Extension_directiveContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_extension_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(NUMBER_SIGN);
			setState(117);
			match(EXTENSION_DIRECTIVE);
			setState(118);
			extension_name();
			setState(119);
			match(COLON);
			setState(120);
			behavior();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Extension_nameContext extends ParserRuleContext {
		public TerminalNode EXTENSION_NAME() { return getToken(GLSLPreParser.EXTENSION_NAME, 0); }
		public Extension_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extension_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterExtension_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitExtension_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitExtension_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Extension_nameContext extension_name() throws RecognitionException {
		Extension_nameContext _localctx = new Extension_nameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_extension_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(EXTENSION_NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Group_of_linesContext extends ParserRuleContext {
		public List<Program_textContext> program_text() {
			return getRuleContexts(Program_textContext.class);
		}
		public Program_textContext program_text(int i) {
			return getRuleContext(Program_textContext.class,i);
		}
		public List<Compiler_directiveContext> compiler_directive() {
			return getRuleContexts(Compiler_directiveContext.class);
		}
		public Compiler_directiveContext compiler_directive(int i) {
			return getRuleContext(Compiler_directiveContext.class,i);
		}
		public Group_of_linesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group_of_lines; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterGroup_of_lines(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitGroup_of_lines(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitGroup_of_lines(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Group_of_linesContext group_of_lines() throws RecognitionException {
		Group_of_linesContext _localctx = new Group_of_linesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_group_of_lines);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(126);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case PROGRAM_TEXT:
						{
						setState(124);
						program_text();
						}
						break;
					case NUMBER_SIGN:
						{
						setState(125);
						compiler_directive();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(130);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode IF_DIRECTIVE() { return getToken(GLSLPreParser.IF_DIRECTIVE, 0); }
		public Constant_expressionContext constant_expression() {
			return getRuleContext(Constant_expressionContext.class,0);
		}
		public Group_of_linesContext group_of_lines() {
			return getRuleContext(Group_of_linesContext.class,0);
		}
		public Endif_directiveContext endif_directive() {
			return getRuleContext(Endif_directiveContext.class,0);
		}
		public List<Elif_directiveContext> elif_directive() {
			return getRuleContexts(Elif_directiveContext.class);
		}
		public Elif_directiveContext elif_directive(int i) {
			return getRuleContext(Elif_directiveContext.class,i);
		}
		public Else_directiveContext else_directive() {
			return getRuleContext(Else_directiveContext.class,0);
		}
		public If_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterIf_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitIf_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitIf_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_directiveContext if_directive() throws RecognitionException {
		If_directiveContext _localctx = new If_directiveContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_if_directive);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(NUMBER_SIGN);
			setState(132);
			match(IF_DIRECTIVE);
			setState(133);
			constant_expression();
			setState(134);
			group_of_lines();
			setState(138);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(135);
					elif_directive();
					}
					} 
				}
				setState(140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(142);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(141);
				else_directive();
				}
				break;
			}
			setState(144);
			endif_directive();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Ifdef_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode IFDEF_DIRECTIVE() { return getToken(GLSLPreParser.IFDEF_DIRECTIVE, 0); }
		public Macro_identifierContext macro_identifier() {
			return getRuleContext(Macro_identifierContext.class,0);
		}
		public Group_of_linesContext group_of_lines() {
			return getRuleContext(Group_of_linesContext.class,0);
		}
		public Endif_directiveContext endif_directive() {
			return getRuleContext(Endif_directiveContext.class,0);
		}
		public List<Elif_directiveContext> elif_directive() {
			return getRuleContexts(Elif_directiveContext.class);
		}
		public Elif_directiveContext elif_directive(int i) {
			return getRuleContext(Elif_directiveContext.class,i);
		}
		public Else_directiveContext else_directive() {
			return getRuleContext(Else_directiveContext.class,0);
		}
		public Ifdef_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifdef_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterIfdef_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitIfdef_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitIfdef_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ifdef_directiveContext ifdef_directive() throws RecognitionException {
		Ifdef_directiveContext _localctx = new Ifdef_directiveContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ifdef_directive);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(NUMBER_SIGN);
			setState(147);
			match(IFDEF_DIRECTIVE);
			setState(148);
			macro_identifier();
			setState(149);
			group_of_lines();
			setState(153);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(150);
					elif_directive();
					}
					} 
				}
				setState(155);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			setState(157);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(156);
				else_directive();
				}
				break;
			}
			setState(159);
			endif_directive();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Ifndef_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode IFNDEF_DIRECTIVE() { return getToken(GLSLPreParser.IFNDEF_DIRECTIVE, 0); }
		public Macro_identifierContext macro_identifier() {
			return getRuleContext(Macro_identifierContext.class,0);
		}
		public Group_of_linesContext group_of_lines() {
			return getRuleContext(Group_of_linesContext.class,0);
		}
		public Endif_directiveContext endif_directive() {
			return getRuleContext(Endif_directiveContext.class,0);
		}
		public List<Elif_directiveContext> elif_directive() {
			return getRuleContexts(Elif_directiveContext.class);
		}
		public Elif_directiveContext elif_directive(int i) {
			return getRuleContext(Elif_directiveContext.class,i);
		}
		public Else_directiveContext else_directive() {
			return getRuleContext(Else_directiveContext.class,0);
		}
		public Ifndef_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifndef_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterIfndef_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitIfndef_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitIfndef_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ifndef_directiveContext ifndef_directive() throws RecognitionException {
		Ifndef_directiveContext _localctx = new Ifndef_directiveContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifndef_directive);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(NUMBER_SIGN);
			setState(162);
			match(IFNDEF_DIRECTIVE);
			setState(163);
			macro_identifier();
			setState(164);
			group_of_lines();
			setState(168);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(165);
					elif_directive();
					}
					} 
				}
				setState(170);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(172);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(171);
				else_directive();
				}
				break;
			}
			setState(174);
			endif_directive();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Line_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode LINE_DIRECTIVE() { return getToken(GLSLPreParser.LINE_DIRECTIVE, 0); }
		public Line_expressionContext line_expression() {
			return getRuleContext(Line_expressionContext.class,0);
		}
		public Line_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterLine_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitLine_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitLine_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Line_directiveContext line_directive() throws RecognitionException {
		Line_directiveContext _localctx = new Line_directiveContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_line_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			match(NUMBER_SIGN);
			setState(177);
			match(LINE_DIRECTIVE);
			setState(178);
			line_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Line_expressionContext extends ParserRuleContext {
		public TerminalNode LINE_EXPRESSION() { return getToken(GLSLPreParser.LINE_EXPRESSION, 0); }
		public Line_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterLine_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitLine_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitLine_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Line_expressionContext line_expression() throws RecognitionException {
		Line_expressionContext _localctx = new Line_expressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_line_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			match(LINE_EXPRESSION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Macro_esc_newlineContext extends ParserRuleContext {
		public TerminalNode MACRO_ESC_NEWLINE() { return getToken(GLSLPreParser.MACRO_ESC_NEWLINE, 0); }
		public Macro_esc_newlineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro_esc_newline; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterMacro_esc_newline(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitMacro_esc_newline(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitMacro_esc_newline(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Macro_esc_newlineContext macro_esc_newline() throws RecognitionException {
		Macro_esc_newlineContext _localctx = new Macro_esc_newlineContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_macro_esc_newline);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(MACRO_ESC_NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Macro_identifierContext extends ParserRuleContext {
		public TerminalNode MACRO_IDENTIFIER() { return getToken(GLSLPreParser.MACRO_IDENTIFIER, 0); }
		public Macro_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterMacro_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitMacro_identifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitMacro_identifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Macro_identifierContext macro_identifier() throws RecognitionException {
		Macro_identifierContext _localctx = new Macro_identifierContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_macro_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			match(MACRO_IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Macro_nameContext extends ParserRuleContext {
		public TerminalNode MACRO_NAME() { return getToken(GLSLPreParser.MACRO_NAME, 0); }
		public Macro_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterMacro_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitMacro_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitMacro_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Macro_nameContext macro_name() throws RecognitionException {
		Macro_nameContext _localctx = new Macro_nameContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_macro_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(MACRO_NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Macro_textContext extends ParserRuleContext {
		public List<Macro_text_Context> macro_text_() {
			return getRuleContexts(Macro_text_Context.class);
		}
		public Macro_text_Context macro_text_(int i) {
			return getRuleContext(Macro_text_Context.class,i);
		}
		public List<Macro_esc_newlineContext> macro_esc_newline() {
			return getRuleContexts(Macro_esc_newlineContext.class);
		}
		public Macro_esc_newlineContext macro_esc_newline(int i) {
			return getRuleContext(Macro_esc_newlineContext.class,i);
		}
		public Macro_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterMacro_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitMacro_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitMacro_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Macro_textContext macro_text() throws RecognitionException {
		Macro_textContext _localctx = new Macro_textContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_macro_text);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MACRO_ESC_NEWLINE || _la==MACRO_TEXT) {
				{
				setState(190);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MACRO_TEXT:
					{
					setState(188);
					macro_text_();
					}
					break;
				case MACRO_ESC_NEWLINE:
					{
					setState(189);
					macro_esc_newline();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Macro_text_Context extends ParserRuleContext {
		public TerminalNode MACRO_TEXT() { return getToken(GLSLPreParser.MACRO_TEXT, 0); }
		public Macro_text_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro_text_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterMacro_text_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitMacro_text_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitMacro_text_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Macro_text_Context macro_text_() throws RecognitionException {
		Macro_text_Context _localctx = new Macro_text_Context(_ctx, getState());
		enterRule(_localctx, 44, RULE_macro_text_);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(MACRO_TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(GLSLPreParser.NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OffContext extends ParserRuleContext {
		public TerminalNode OFF() { return getToken(GLSLPreParser.OFF, 0); }
		public OffContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_off; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterOff(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitOff(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitOff(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OffContext off() throws RecognitionException {
		OffContext _localctx = new OffContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_off);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(OFF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OnContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(GLSLPreParser.ON, 0); }
		public OnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_on; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterOn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitOn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitOn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OnContext on() throws RecognitionException {
		OnContext _localctx = new OnContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_on);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(ON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Pragma_debugContext extends ParserRuleContext {
		public TerminalNode DEBUG() { return getToken(GLSLPreParser.DEBUG, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(GLSLPreParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(GLSLPreParser.RIGHT_PAREN, 0); }
		public OnContext on() {
			return getRuleContext(OnContext.class,0);
		}
		public OffContext off() {
			return getRuleContext(OffContext.class,0);
		}
		public Pragma_debugContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragma_debug; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterPragma_debug(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitPragma_debug(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitPragma_debug(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pragma_debugContext pragma_debug() throws RecognitionException {
		Pragma_debugContext _localctx = new Pragma_debugContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_pragma_debug);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(DEBUG);
			setState(204);
			match(LEFT_PAREN);
			setState(207);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				{
				setState(205);
				on();
				}
				break;
			case OFF:
				{
				setState(206);
				off();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(209);
			match(RIGHT_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Pragma_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode PRAGMA_DIRECTIVE() { return getToken(GLSLPreParser.PRAGMA_DIRECTIVE, 0); }
		public StdglContext stdgl() {
			return getRuleContext(StdglContext.class,0);
		}
		public Pragma_debugContext pragma_debug() {
			return getRuleContext(Pragma_debugContext.class,0);
		}
		public Pragma_optimizeContext pragma_optimize() {
			return getRuleContext(Pragma_optimizeContext.class,0);
		}
		public Pragma_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragma_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterPragma_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitPragma_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitPragma_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pragma_directiveContext pragma_directive() throws RecognitionException {
		Pragma_directiveContext _localctx = new Pragma_directiveContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_pragma_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(NUMBER_SIGN);
			setState(212);
			match(PRAGMA_DIRECTIVE);
			setState(216);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STDGL:
				{
				setState(213);
				stdgl();
				}
				break;
			case DEBUG:
				{
				setState(214);
				pragma_debug();
				}
				break;
			case OPTIMIZE:
				{
				setState(215);
				pragma_optimize();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Pragma_optimizeContext extends ParserRuleContext {
		public TerminalNode OPTIMIZE() { return getToken(GLSLPreParser.OPTIMIZE, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(GLSLPreParser.LEFT_PAREN, 0); }
		public TerminalNode RIGHT_PAREN() { return getToken(GLSLPreParser.RIGHT_PAREN, 0); }
		public OnContext on() {
			return getRuleContext(OnContext.class,0);
		}
		public OffContext off() {
			return getRuleContext(OffContext.class,0);
		}
		public Pragma_optimizeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pragma_optimize; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterPragma_optimize(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitPragma_optimize(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitPragma_optimize(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pragma_optimizeContext pragma_optimize() throws RecognitionException {
		Pragma_optimizeContext _localctx = new Pragma_optimizeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_pragma_optimize);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(OPTIMIZE);
			setState(219);
			match(LEFT_PAREN);
			setState(222);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				{
				setState(220);
				on();
				}
				break;
			case OFF:
				{
				setState(221);
				off();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(224);
			match(RIGHT_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProfileContext extends ParserRuleContext {
		public TerminalNode PROFILE() { return getToken(GLSLPreParser.PROFILE, 0); }
		public ProfileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_profile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterProfile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitProfile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitProfile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProfileContext profile() throws RecognitionException {
		ProfileContext _localctx = new ProfileContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_profile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(PROFILE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Program_textContext extends ParserRuleContext {
		public TerminalNode PROGRAM_TEXT() { return getToken(GLSLPreParser.PROGRAM_TEXT, 0); }
		public Program_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterProgram_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitProgram_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitProgram_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Program_textContext program_text() throws RecognitionException {
		Program_textContext _localctx = new Program_textContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_program_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(PROGRAM_TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StdglContext extends ParserRuleContext {
		public TerminalNode STDGL() { return getToken(GLSLPreParser.STDGL, 0); }
		public StdglContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stdgl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterStdgl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitStdgl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitStdgl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StdglContext stdgl() throws RecognitionException {
		StdglContext _localctx = new StdglContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_stdgl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(STDGL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Undef_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode UNDEF_DIRECTIVE() { return getToken(GLSLPreParser.UNDEF_DIRECTIVE, 0); }
		public Macro_identifierContext macro_identifier() {
			return getRuleContext(Macro_identifierContext.class,0);
		}
		public Undef_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_undef_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterUndef_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitUndef_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitUndef_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Undef_directiveContext undef_directive() throws RecognitionException {
		Undef_directiveContext _localctx = new Undef_directiveContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_undef_directive);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(NUMBER_SIGN);
			setState(233);
			match(UNDEF_DIRECTIVE);
			setState(234);
			macro_identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Version_directiveContext extends ParserRuleContext {
		public TerminalNode NUMBER_SIGN() { return getToken(GLSLPreParser.NUMBER_SIGN, 0); }
		public TerminalNode VERSION_DIRECTIVE() { return getToken(GLSLPreParser.VERSION_DIRECTIVE, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ProfileContext profile() {
			return getRuleContext(ProfileContext.class,0);
		}
		public Version_directiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_version_directive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).enterVersion_directive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GLSLPreParserListener ) ((GLSLPreParserListener)listener).exitVersion_directive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GLSLPreParserVisitor ) return ((GLSLPreParserVisitor<? extends T>)visitor).visitVersion_directive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Version_directiveContext version_directive() throws RecognitionException {
		Version_directiveContext _localctx = new Version_directiveContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_version_directive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			match(NUMBER_SIGN);
			setState(237);
			match(VERSION_DIRECTIVE);
			setState(238);
			number();
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PROFILE) {
				{
				setState(239);
				profile();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0133\u00f3\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007"+
		"\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007"+
		"\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007"+
		"\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007"+
		"\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0001\u0000"+
		"\u0005\u0000F\b\u0000\n\u0000\f\u0000I\t\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001"+
		"X\b\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0005\f\u007f\b\f\n\f\f\f\u0082"+
		"\t\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u0089\b\r\n\r\f\r"+
		"\u008c\t\r\u0001\r\u0003\r\u008f\b\r\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0098\b\u000e\n"+
		"\u000e\f\u000e\u009b\t\u000e\u0001\u000e\u0003\u000e\u009e\b\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0005\u000f\u00a7\b\u000f\n\u000f\f\u000f\u00aa\t\u000f\u0001\u000f"+
		"\u0003\u000f\u00ad\b\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0005\u0015\u00bf\b\u0015\n\u0015\f\u0015\u00c2\t\u0015\u0001\u0016\u0001"+
		"\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0001"+
		"\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u00d0"+
		"\b\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001"+
		"\u001b\u0001\u001b\u0003\u001b\u00d9\b\u001b\u0001\u001c\u0001\u001c\u0001"+
		"\u001c\u0001\u001c\u0003\u001c\u00df\b\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001"+
		" \u0001 \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0003!\u00f1\b!\u0001"+
		"!\u0000\u0000\"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@B\u0000\u0000\u00ec\u0000"+
		"G\u0001\u0000\u0000\u0000\u0002W\u0001\u0000\u0000\u0000\u0004Y\u0001"+
		"\u0000\u0000\u0000\u0006[\u0001\u0000\u0000\u0000\b]\u0001\u0000\u0000"+
		"\u0000\nb\u0001\u0000\u0000\u0000\fg\u0001\u0000\u0000\u0000\u000ek\u0001"+
		"\u0000\u0000\u0000\u0010n\u0001\u0000\u0000\u0000\u0012r\u0001\u0000\u0000"+
		"\u0000\u0014t\u0001\u0000\u0000\u0000\u0016z\u0001\u0000\u0000\u0000\u0018"+
		"\u0080\u0001\u0000\u0000\u0000\u001a\u0083\u0001\u0000\u0000\u0000\u001c"+
		"\u0092\u0001\u0000\u0000\u0000\u001e\u00a1\u0001\u0000\u0000\u0000 \u00b0"+
		"\u0001\u0000\u0000\u0000\"\u00b4\u0001\u0000\u0000\u0000$\u00b6\u0001"+
		"\u0000\u0000\u0000&\u00b8\u0001\u0000\u0000\u0000(\u00ba\u0001\u0000\u0000"+
		"\u0000*\u00c0\u0001\u0000\u0000\u0000,\u00c3\u0001\u0000\u0000\u0000."+
		"\u00c5\u0001\u0000\u0000\u00000\u00c7\u0001\u0000\u0000\u00002\u00c9\u0001"+
		"\u0000\u0000\u00004\u00cb\u0001\u0000\u0000\u00006\u00d3\u0001\u0000\u0000"+
		"\u00008\u00da\u0001\u0000\u0000\u0000:\u00e2\u0001\u0000\u0000\u0000<"+
		"\u00e4\u0001\u0000\u0000\u0000>\u00e6\u0001\u0000\u0000\u0000@\u00e8\u0001"+
		"\u0000\u0000\u0000B\u00ec\u0001\u0000\u0000\u0000DF\u0003\u0002\u0001"+
		"\u0000ED\u0001\u0000\u0000\u0000FI\u0001\u0000\u0000\u0000GE\u0001\u0000"+
		"\u0000\u0000GH\u0001\u0000\u0000\u0000H\u0001\u0001\u0000\u0000\u0000"+
		"IG\u0001\u0000\u0000\u0000JX\u0003\b\u0004\u0000KX\u0003\n\u0005\u0000"+
		"LX\u0003\f\u0006\u0000MX\u0003\u000e\u0007\u0000NX\u0003\u0010\b\u0000"+
		"OX\u0003\u0014\n\u0000PX\u0003\u001a\r\u0000QX\u0003\u001c\u000e\u0000"+
		"RX\u0003\u001e\u000f\u0000SX\u0003 \u0010\u0000TX\u00036\u001b\u0000U"+
		"X\u0003@ \u0000VX\u0003B!\u0000WJ\u0001\u0000\u0000\u0000WK\u0001\u0000"+
		"\u0000\u0000WL\u0001\u0000\u0000\u0000WM\u0001\u0000\u0000\u0000WN\u0001"+
		"\u0000\u0000\u0000WO\u0001\u0000\u0000\u0000WP\u0001\u0000\u0000\u0000"+
		"WQ\u0001\u0000\u0000\u0000WR\u0001\u0000\u0000\u0000WS\u0001\u0000\u0000"+
		"\u0000WT\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000WV\u0001\u0000"+
		"\u0000\u0000X\u0003\u0001\u0000\u0000\u0000YZ\u0005\u0119\u0000\u0000"+
		"Z\u0005\u0001\u0000\u0000\u0000[\\\u0005\u0115\u0000\u0000\\\u0007\u0001"+
		"\u0000\u0000\u0000]^\u0005\u00e6\u0000\u0000^_\u0005\u0103\u0000\u0000"+
		"_`\u0003(\u0014\u0000`a\u0003*\u0015\u0000a\t\u0001\u0000\u0000\u0000"+
		"bc\u0005\u00e6\u0000\u0000cd\u0005\u0104\u0000\u0000de\u0003\u0006\u0003"+
		"\u0000ef\u0003\u0018\f\u0000f\u000b\u0001\u0000\u0000\u0000gh\u0005\u00e6"+
		"\u0000\u0000hi\u0005\u0105\u0000\u0000ij\u0003\u0018\f\u0000j\r\u0001"+
		"\u0000\u0000\u0000kl\u0005\u00e6\u0000\u0000lm\u0005\u0106\u0000\u0000"+
		"m\u000f\u0001\u0000\u0000\u0000no\u0005\u00e6\u0000\u0000op\u0005\u0107"+
		"\u0000\u0000pq\u0003\u0012\t\u0000q\u0011\u0001\u0000\u0000\u0000rs\u0005"+
		"\u0117\u0000\u0000s\u0013\u0001\u0000\u0000\u0000tu\u0005\u00e6\u0000"+
		"\u0000uv\u0005\u0108\u0000\u0000vw\u0003\u0016\u000b\u0000wx\u0005\u00d2"+
		"\u0000\u0000xy\u0003\u0004\u0002\u0000y\u0015\u0001\u0000\u0000\u0000"+
		"z{\u0005\u011a\u0000\u0000{\u0017\u0001\u0000\u0000\u0000|\u007f\u0003"+
		"<\u001e\u0000}\u007f\u0003\u0002\u0001\u0000~|\u0001\u0000\u0000\u0000"+
		"~}\u0001\u0000\u0000\u0000\u007f\u0082\u0001\u0000\u0000\u0000\u0080~"+
		"\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u0019"+
		"\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0083\u0084"+
		"\u0005\u00e6\u0000\u0000\u0084\u0085\u0005\u0109\u0000\u0000\u0085\u0086"+
		"\u0003\u0006\u0003\u0000\u0086\u008a\u0003\u0018\f\u0000\u0087\u0089\u0003"+
		"\n\u0005\u0000\u0088\u0087\u0001\u0000\u0000\u0000\u0089\u008c\u0001\u0000"+
		"\u0000\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008a\u008b\u0001\u0000"+
		"\u0000\u0000\u008b\u008e\u0001\u0000\u0000\u0000\u008c\u008a\u0001\u0000"+
		"\u0000\u0000\u008d\u008f\u0003\f\u0006\u0000\u008e\u008d\u0001\u0000\u0000"+
		"\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000"+
		"\u0000\u0090\u0091\u0003\u000e\u0007\u0000\u0091\u001b\u0001\u0000\u0000"+
		"\u0000\u0092\u0093\u0005\u00e6\u0000\u0000\u0093\u0094\u0005\u010a\u0000"+
		"\u0000\u0094\u0095\u0003&\u0013\u0000\u0095\u0099\u0003\u0018\f\u0000"+
		"\u0096\u0098\u0003\n\u0005\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0098"+
		"\u009b\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099"+
		"\u009a\u0001\u0000\u0000\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b"+
		"\u0099\u0001\u0000\u0000\u0000\u009c\u009e\u0003\f\u0006\u0000\u009d\u009c"+
		"\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u0001\u0000\u0000\u0000\u009f\u00a0\u0003\u000e\u0007\u0000\u00a0\u001d"+
		"\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\u00e6\u0000\u0000\u00a2\u00a3"+
		"\u0005\u010b\u0000\u0000\u00a3\u00a4\u0003&\u0013\u0000\u00a4\u00a8\u0003"+
		"\u0018\f\u0000\u00a5\u00a7\u0003\n\u0005\u0000\u00a6\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00ac\u0001\u0000"+
		"\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab\u00ad\u0003\f\u0006"+
		"\u0000\u00ac\u00ab\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00af\u0003\u000e\u0007"+
		"\u0000\u00af\u001f\u0001\u0000\u0000\u0000\u00b0\u00b1\u0005\u00e6\u0000"+
		"\u0000\u00b1\u00b2\u0005\u010c\u0000\u0000\u00b2\u00b3\u0003\"\u0011\u0000"+
		"\u00b3!\u0001\u0000\u0000\u0000\u00b4\u00b5\u0005\u0121\u0000\u0000\u00b5"+
		"#\u0001\u0000\u0000\u0000\u00b6\u00b7\u0005\u0123\u0000\u0000\u00b7%\u0001"+
		"\u0000\u0000\u0000\u00b8\u00b9\u0005\u011e\u0000\u0000\u00b9\'\u0001\u0000"+
		"\u0000\u0000\u00ba\u00bb\u0005\u0112\u0000\u0000\u00bb)\u0001\u0000\u0000"+
		"\u0000\u00bc\u00bf\u0003,\u0016\u0000\u00bd\u00bf\u0003$\u0012\u0000\u00be"+
		"\u00bc\u0001\u0000\u0000\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c2\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0"+
		"\u00c1\u0001\u0000\u0000\u0000\u00c1+\u0001\u0000\u0000\u0000\u00c2\u00c0"+
		"\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005\u0124\u0000\u0000\u00c4-\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c6\u0005\u0131\u0000\u0000\u00c6/\u0001\u0000"+
		"\u0000\u0000\u00c7\u00c8\u0005\u0128\u0000\u0000\u00c81\u0001\u0000\u0000"+
		"\u0000\u00c9\u00ca\u0005\u0129\u0000\u0000\u00ca3\u0001\u0000\u0000\u0000"+
		"\u00cb\u00cc\u0005\u0126\u0000\u0000\u00cc\u00cf\u0005\u00e2\u0000\u0000"+
		"\u00cd\u00d0\u00032\u0019\u0000\u00ce\u00d0\u00030\u0018\u0000\u00cf\u00cd"+
		"\u0001\u0000\u0000\u0000\u00cf\u00ce\u0001\u0000\u0000\u0000\u00d0\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\u00f1\u0000\u0000\u00d25\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0005\u00e6\u0000\u0000\u00d4\u00d8\u0005"+
		"\u010d\u0000\u0000\u00d5\u00d9\u0003>\u001f\u0000\u00d6\u00d9\u00034\u001a"+
		"\u0000\u00d7\u00d9\u00038\u001c\u0000\u00d8\u00d5\u0001\u0000\u0000\u0000"+
		"\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d8\u00d7\u0001\u0000\u0000\u0000"+
		"\u00d97\u0001\u0000\u0000\u0000\u00da\u00db\u0005\u012a\u0000\u0000\u00db"+
		"\u00de\u0005\u00e2\u0000\u0000\u00dc\u00df\u00032\u0019\u0000\u00dd\u00df"+
		"\u00030\u0018\u0000\u00de\u00dc\u0001\u0000\u0000\u0000\u00de\u00dd\u0001"+
		"\u0000\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\u00e1\u0005"+
		"\u00f1\u0000\u0000\u00e19\u0001\u0000\u0000\u0000\u00e2\u00e3\u0005\u0132"+
		"\u0000\u0000\u00e3;\u0001\u0000\u0000\u0000\u00e4\u00e5\u0005\u012d\u0000"+
		"\u0000\u00e5=\u0001\u0000\u0000\u0000\u00e6\u00e7\u0005\u012c\u0000\u0000"+
		"\u00e7?\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005\u00e6\u0000\u0000\u00e9"+
		"\u00ea\u0005\u010e\u0000\u0000\u00ea\u00eb\u0003&\u0013\u0000\u00ebA\u0001"+
		"\u0000\u0000\u0000\u00ec\u00ed\u0005\u00e6\u0000\u0000\u00ed\u00ee\u0005"+
		"\u010f\u0000\u0000\u00ee\u00f0\u0003.\u0017\u0000\u00ef\u00f1\u0003:\u001d"+
		"\u0000\u00f0\u00ef\u0001\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000"+
		"\u0000\u00f1C\u0001\u0000\u0000\u0000\u0010GW~\u0080\u008a\u008e\u0099"+
		"\u009d\u00a8\u00ac\u00be\u00c0\u00cf\u00d8\u00de\u00f0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}