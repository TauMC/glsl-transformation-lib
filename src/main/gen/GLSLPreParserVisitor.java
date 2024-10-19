// Generated from D:/git/glsl-transformation-lib/src/main/antlr/GLSLPreParser.g4 by ANTLR 4.13.1

    package org.taumc.glsl.grammar;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GLSLPreParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GLSLPreParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#translation_unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslation_unit(GLSLPreParser.Translation_unitContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#compiler_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompiler_directive(GLSLPreParser.Compiler_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#behavior}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBehavior(GLSLPreParser.BehaviorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#constant_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant_expression(GLSLPreParser.Constant_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#define_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefine_directive(GLSLPreParser.Define_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#elif_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElif_directive(GLSLPreParser.Elif_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#else_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_directive(GLSLPreParser.Else_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#endif_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndif_directive(GLSLPreParser.Endif_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#error_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitError_directive(GLSLPreParser.Error_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#error_message}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitError_message(GLSLPreParser.Error_messageContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#extension_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtension_directive(GLSLPreParser.Extension_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#extension_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtension_name(GLSLPreParser.Extension_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#group_of_lines}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup_of_lines(GLSLPreParser.Group_of_linesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#if_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_directive(GLSLPreParser.If_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#ifdef_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfdef_directive(GLSLPreParser.Ifdef_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#ifndef_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfndef_directive(GLSLPreParser.Ifndef_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#line_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine_directive(GLSLPreParser.Line_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#line_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine_expression(GLSLPreParser.Line_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#macro_esc_newline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_esc_newline(GLSLPreParser.Macro_esc_newlineContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#macro_identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_identifier(GLSLPreParser.Macro_identifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#macro_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_name(GLSLPreParser.Macro_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#macro_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_text(GLSLPreParser.Macro_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#macro_text_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_text_(GLSLPreParser.Macro_text_Context ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(GLSLPreParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#off}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOff(GLSLPreParser.OffContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#on}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOn(GLSLPreParser.OnContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#pragma_debug}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPragma_debug(GLSLPreParser.Pragma_debugContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#pragma_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPragma_directive(GLSLPreParser.Pragma_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#pragma_optimize}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPragma_optimize(GLSLPreParser.Pragma_optimizeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#profile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProfile(GLSLPreParser.ProfileContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#program_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram_text(GLSLPreParser.Program_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#stdgl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStdgl(GLSLPreParser.StdglContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#undef_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUndef_directive(GLSLPreParser.Undef_directiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link GLSLPreParser#version_directive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVersion_directive(GLSLPreParser.Version_directiveContext ctx);
}