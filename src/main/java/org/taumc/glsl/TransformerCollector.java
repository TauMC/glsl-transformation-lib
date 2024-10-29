package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

public class TransformerCollector extends GLSLParserBaseListener {

    private final Transformer transformer;

    public TransformerCollector(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public void enterFunction_definition(GLSLParser.Function_definitionContext ctx) {
        this.transformer.addFunctionDefinition(ctx);
    }

    @Override
    public void enterFunction_prototype(GLSLParser.Function_prototypeContext ctx) {
        this.transformer.addFunctionPrototype(ctx);
    }

    @Override
    public void enterParameter_declaration(GLSLParser.Parameter_declarationContext ctx) {
        this.transformer.addParameter(ctx);
    }

    @Override
    public void enterVariable_identifier(GLSLParser.Variable_identifierContext ctx) {
        this.transformer.addVariable(ctx);
    }

    @Override
    public void enterTypeless_declaration(GLSLParser.Typeless_declarationContext ctx) {
        this.transformer.addTypeless(ctx);
    }

    @Override
    public void enterType_specifier_nonarray(GLSLParser.Type_specifier_nonarrayContext ctx) {
        if (ctx.TEXTURE2D() != null) {
            this.transformer.addTexture(ctx);
        }
        if (ctx.TEXTURE3D() != null) {
            this.transformer.addTexture(ctx);
        }
    }

    @Override
    public void enterPostfix_expression(GLSLParser.Postfix_expressionContext ctx) {
        this.transformer.addPostfix(ctx);
    }

    @Override
    public void exitAssignment_expression(GLSLParser.Assignment_expressionContext ctx) {
        this.transformer.addAssignment(ctx);
    }

    @Override
    public void enterBinary_expression(GLSLParser.Binary_expressionContext ctx) {
        this.transformer.addBinary(ctx);
    }

    @Override
    public void enterStorage_qualifier(GLSLParser.Storage_qualifierContext ctx) {
        this.transformer.addStorage(ctx);
    }

    @Override
    public void enterStruct_declaration(GLSLParser.Struct_declarationContext ctx) {
        this.transformer.addStruct(ctx);
    }

    @Override
    public void enterSingle_declaration(GLSLParser.Single_declarationContext ctx) {
        this.transformer.addSingle(ctx);
    }
}
