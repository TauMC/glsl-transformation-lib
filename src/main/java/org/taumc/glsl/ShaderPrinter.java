package org.taumc.glsl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.taumc.glsl.grammar.GLSLLexer;
import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseVisitor;
import org.taumc.glsl.grammar.GLSLPreParser;
import org.taumc.glsl.grammar.GLSLPreParserBaseVisitor;

/**
 * Formats a GLSL parse tree as a human-readable string with consistent indentation and spacing.
 */
public class ShaderPrinter {
    private final ParseTree shaderTree;

    public ShaderPrinter(ParseTree shaderTree) {
        this.shaderTree = shaderTree;
    }

    public String getFormattedShader() {
        if (shaderTree instanceof GLSLPreParser.Translation_unitContext) {
            var v = new PreVisitor();
            v.visit(shaderTree);
            return v.sb.toString();
        }
        var v = new Visitor();
        v.visit(shaderTree);
        return v.sb.toString();
    }

    public static String getFormattedShader(ParseTree tree) {
        return new ShaderPrinter(tree).getFormattedShader();
    }

    /**
     * ANTLR visitor for the GLSLPreParser tree (preprocessor directives).
     *
     * <p>Uses the same {@code nextSep}/{@link #emit}/{@link #newline}/{@link #nosep} pattern as
     * {@link Visitor}. Directives are never indented, so {@link #newline} simply sets
     * {@link #nextSep} to {@code "\n"} with no leading spaces.
     *
     * <p>{@link #visitCompiler_directive} appends a newline after each top-level directive.
     * Nested directives inside {@code #if}/{@code #ifdef}/{@code #ifndef} blocks fall through to
     * {@code visitChildren} and are emitted inline; complex conditional blocks are not
     * specially formatted beyond correct spacing.
     */
    private static class PreVisitor extends GLSLPreParserBaseVisitor<Void> {
        final StringBuilder sb = new StringBuilder();
        String nextSep = "";

        void emit(String s) {
            sb.append(nextSep);
            sb.append(s);
            nextSep = " ";
        }

        void newline() {
            nextSep = "\n";
        }

        void nosep() {
            nextSep = "";
        }

        @Override
        public Void visitCompiler_directive(GLSLPreParser.Compiler_directiveContext ctx) {
            nosep();
            visitChildren(ctx);
            sb.append('\n');
            nextSep = "";
            return null;
        }

        /**
         * Inserts a newline before the content of a {@code #if}/{@code #ifdef}/{@code #ifndef}
         * block body, separating the opening directive line from its contents.
         */
        @Override
        public Void visitGroup_of_lines(GLSLPreParser.Group_of_linesContext ctx) {
            sb.append('\n');
            nextSep = "";
            return visitChildren(ctx);
        }

        @Override
        public Void visitTerminal(TerminalNode node) {
            int type = node.getSymbol().getType();
            String text = node.getText();
            switch (type) {
                case GLSLLexer.EOF:
                    break;
                case GLSLLexer.NUMBER_SIGN:
                    nosep();
                    emit("#");
                    nosep();
                    break;
                case GLSLLexer.LEFT_PAREN:
                case GLSLLexer.LEFT_BRACKET:
                    nosep();
                    emit(text);
                    nosep();
                    break;
                case GLSLLexer.RIGHT_PAREN:
                case GLSLLexer.RIGHT_BRACKET:
                    nosep();
                    emit(text);
                    break;
                // These tokens include their own leading whitespace as lexed;
                // suppress the default separator to avoid doubling it.
                case GLSLLexer.MACRO_TEXT:
                case GLSLLexer.CONSTANT_EXPRESSION:
                case GLSLLexer.ERROR_MESSAGE:
                case GLSLLexer.LINE_EXPRESSION:
                case GLSLLexer.PROGRAM_TEXT:
                    nosep();
                    emit(text);
                    break;
                default:
                    emit(text);
                    break;
            }
            return null;
        }
    }

    /**
     * ANTLR visitor that walks the parse tree and writes formatted GLSL to a StringBuilder.
     *
     * <p>Spacing between tokens is controlled via the {@link #nextSep} field rather than
     * per-token conditionals. {@link #emit} prepends {@code nextSep} before each token and
     * then resets it to {@code " "}. Callers suppress or replace the separator by calling
     * {@link #nosep} or {@link #newline} before the next emit.
     *
     * <p>Most grammar rules fall through to the default {@code visitChildren}, which visits
     * each terminal via {@link #visitTerminal}. Rule-level overrides are only added when the
     * surrounding context is needed to determine correct spacing or indentation (e.g. prefix
     * vs. postfix {@code ++}, case label bodies, for-loop headers).
     */
    private static class Visitor extends GLSLParserBaseVisitor<Void> {
        final StringBuilder sb = new StringBuilder();
        /** Current indentation depth; each level adds four spaces. */
        int indent = 0;
        /**
         * Separator prepended by the next {@link #emit} call. Starts empty, becomes
         * {@code " "} after each emit, and is overridden to a newline+indent string by
         * {@link #newline} or cleared to {@code ""} by {@link #nosep}.
         */
        String nextSep = "";
        /** True while visiting the {@code for(;;)} header, so semicolons get a space instead of a newline. */
        boolean inForHeader = false;
        /** True while inside an indented case/default body; used by {@link #visitSwitch_statement} and {@link #visitCase_label}. */
        boolean inCaseBody = false;

        /** Appends {@link #nextSep} and {@code s} to the output, then sets {@link #nextSep} to {@code " "}. */
        void emit(String s) {
            sb.append(nextSep);
            sb.append(s);
            nextSep = " ";
        }

        /** Sets {@link #nextSep} to a newline followed by the current indentation. */
        void newline() {
            StringBuilder spaces = new StringBuilder("\n");
            for (int i = 0; i < indent; i++) spaces.append("    ");
            nextSep = spaces.toString();
        }

        /** Clears {@link #nextSep} so the next token is emitted with no preceding separator. */
        void nosep() {
            nextSep = "";
        }

        /**
         * Visits a function definition, appending a blank line after the closing brace.
         * The trailing newline is appended directly to avoid leaving {@link #nextSep} as
         * {@code " "}, which would bleed a leading space onto the next top-level declaration.
         */
        @Override
        public Void visitFunction_definition(GLSLParser.Function_definitionContext ctx) {
            visit(ctx.function_prototype());
            visit(ctx.compound_statement_no_new_scope());
            sb.append("\n");
            nosep();
            return null;
        }

        /**
         * Suppresses the space between a unary operator token ({@code -}, {@code !}, {@code ~})
         * and its operand.
         */
        @Override
        public Void visitUnary_operator(GLSLParser.Unary_operatorContext ctx) {
            visitChildren(ctx);
            nosep();
            return null;
        }

        /**
         * Handles prefix {@code ++} and {@code --}, where the operator must carry the pending
         * indentation separator (e.g. when it starts a statement) while still having no space
         * before the operand. Falls through to {@code visitChildren} for all other unary forms.
         *
         * <p>Postfix {@code ++}/{@code --} are handled by {@link #visitTerminal} via the
         * {@code INC_OP}/{@code DEC_OP} cases, which suppress the preceding space.
         */
        @Override
        public Void visitUnary_expression(GLSLParser.Unary_expressionContext ctx) {
            if (ctx.INC_OP() != null || ctx.DEC_OP() != null) {
                emit(ctx.getChild(0).getText());
                nosep();
                visit(ctx.unary_expression());
                return null;
            }
            return visitChildren(ctx);
        }

        /**
         * Formats a switch statement, managing the switch body's braces directly so that
         * {@link #visitCase_label} can open a case body indent and this method can close the
         * last one before the closing brace.
         */
        @Override
        public Void visitSwitch_statement(GLSLParser.Switch_statementContext ctx) {
            emit("switch");
            nosep();
            emit("(");
            nosep();
            visit(ctx.expression());
            nosep();
            emit(")");
            emit("{");
            indent++;
            newline();
            if (ctx.statement_list() != null) {
                visit(ctx.statement_list());
            }
            if (inCaseBody) {
                indent--;
                inCaseBody = false;
            }
            indent--;
            newline();
            emit("}");
            newline();
            return null;
        }

        /**
         * Emits a {@code case} or {@code default} label, closing the previous case body's
         * indent (if any) and opening a new one. Case body indentation is closed either by
         * the next case label or by {@link #visitSwitch_statement} at the closing brace.
         */
        @Override
        public Void visitCase_label(GLSLParser.Case_labelContext ctx) {
            if (inCaseBody) {
                indent--;
                newline();
            }
            if (ctx.DEFAULT() != null) {
                emit("default");
            } else {
                emit("case");
                visit(ctx.expression());
            }
            nosep();
            emit(":");
            indent++;
            newline();
            inCaseBody = true;
            return null;
        }

        /**
         * Visits an if/else branch body. Braced bodies are visited directly; unbraced
         * single-statement bodies are indented one extra level on their own line.
         */
        private void visitBranchStatement(GLSLParser.StatementContext stmt) {
            if (stmt.compound_statement() != null) {
                visit(stmt);
            } else {
                indent++;
                newline();
                visit(stmt);
                indent--;
                newline();
            }
        }

        /** Formats an {@code if}/{@code else} statement, handling both braced and unbraced branches. */
        @Override
        public Void visitSelection_statement(GLSLParser.Selection_statementContext ctx) {
            var rest = ctx.selection_rest_statement();
            emit("if");
            emit("(");
            nosep();
            visit(ctx.expression());
            nosep();
            emit(")");
            visitBranchStatement(rest.statement(0));
            if (rest.ELSE() != null) {
                emit("else");
                visitBranchStatement(rest.statement(1));
            }
            newline();
            return null;
        }

        /**
         * Formats {@code for}, {@code while}, and {@code do-while} loops.
         * Sets {@link #inForHeader} while visiting the for-loop init and rest clauses so
         * that semicolons within the header produce spaces rather than newlines.
         */
        @Override
        public Void visitIteration_statement(GLSLParser.Iteration_statementContext ctx) {
            if (ctx.FOR() != null) {
                emit("for");
                emit("(");
                nosep();
                inForHeader = true;
                visit(ctx.for_init_statement());
                visit(ctx.for_rest_statement());
                inForHeader = false;
                nosep();
                emit(")");
                visit(ctx.statement_no_new_scope());
            } else if (ctx.DO() != null) {
                emit("do");
                visit(ctx.statement());
                nextSep = " "; // statement may be unbraced, so its semicolon could have set a newline
                emit("while");
                emit("(");
                nosep();
                visit(ctx.expression());
                nosep();
                emit(")");
                nosep();
                emit(";");
            } else {
                emit("while");
                emit("(");
                nosep();
                visit(ctx.condition());
                nosep();
                emit(")");
                visit(ctx.statement_no_new_scope());
            }
            newline();
            return null;
        }

        /**
         * Handles all leaf tokens. Most tokens use the default space separator via
         * {@link #emit}; punctuation tokens override {@link #nextSep} via {@link #nosep}
         * to suppress spaces where grammar rules cannot.
         */
        @Override
        public Void visitTerminal(TerminalNode node) {
            int type = node.getSymbol().getType();
            String text = node.getText();
            switch (type) {
                case GLSLParser.EOF:
                    break;
                case GLSLParser.LEFT_BRACE:
                    emit("{");
                    indent++;
                    newline();
                    break;
                case GLSLParser.RIGHT_BRACE:
                    indent--;
                    newline();
                    emit("}");
                    break;
                case GLSLParser.LEFT_PAREN:
                case GLSLParser.LEFT_BRACKET:
                    nosep();
                    emit(text);
                    nosep();
                    break;
                case GLSLParser.RIGHT_PAREN:
                case GLSLParser.RIGHT_BRACKET:
                    nosep();
                    emit(text);
                    break;
                case GLSLParser.DOT:
                    nosep();
                    emit(".");
                    nosep();
                    break;
                case GLSLParser.COMMA:
                    nosep();
                    emit(",");
                    break;
                case GLSLParser.SEMICOLON:
                    nosep();
                    emit(";");
                    if (!inForHeader) {
                        newline();
                    }
                    break;
                case GLSLParser.INC_OP:
                case GLSLParser.DEC_OP:
                    nosep();
                    emit(text);
                    break;
                default:
                    emit(text);
                    break;
            }
            return null;
        }
    }
}
