package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

public class TransformerRemover extends GLSLParserBaseListener {

    private final Transformer transformer;

    public TransformerRemover(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public void enterFunction_definition(GLSLParser.Function_definitionContext ctx) {
        this.transformer.removeFunctionDefinition(ctx);
    }

    @Override
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        this.transformer.removeFunctionPrototype(ctx);
    }

    @Override
    public void enterParameter_declaration(GLSLParser.Parameter_declarationContext ctx) {
        this.transformer.removeParameter(ctx);
    }

    @Override
    public void enterVariable_identifier(GLSLParser.Variable_identifierContext ctx) {
        this.transformer.removeVariables(ctx);
    }

    @Override
    public void enterTypeless_declaration(GLSLParser.Typeless_declarationContext ctx) {
        this.transformer.removeTypeless(ctx);
    }

    @Override
    public void enterType_specifier_nonarray(GLSLParser.Type_specifier_nonarrayContext ctx) {
        if (ctx.TEXTURE2D() != null) {
            this.transformer.removeTexture(ctx);
        }
        if (ctx.TEXTURE3D() != null) {
            this.transformer.removeTexture(ctx);
        }
    }

    @Override
    public void enterPostfix_expression(GLSLParser.Postfix_expressionContext ctx) {
        this.transformer.removePostfix(ctx);
    }

    @Override
    public void exitAssignment_expression(GLSLParser.Assignment_expressionContext ctx) {
        this.transformer.removeAssignment(ctx);
    }

    @Override
    public void enterBinary_expression(GLSLParser.Binary_expressionContext ctx) {
        this.transformer.removeBinary(ctx);
    }

    @Override
    public void enterStorage_qualifier(GLSLParser.Storage_qualifierContext ctx) {
        this.transformer.removeStorage(ctx);
    }

    @Override
    public void enterStruct_declaration(GLSLParser.Struct_declarationContext ctx) {
        this.transformer.removeStruct(ctx);
    }

    @Override
    public void enterSingle_declaration(GLSLParser.Single_declarationContext ctx) {
        this.transformer.removeSingle(ctx);
    }
}
