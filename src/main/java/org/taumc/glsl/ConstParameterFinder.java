package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConstParameterFinder extends GLSLParserBaseListener {

    private final Map<String, List<String>> functions;

    public ConstParameterFinder(Map<String,List<String>> functions) {
        this.functions = functions;
    }

    @Override
    public void enterParameter_declaration(GLSLParser.Parameter_declarationContext ctx) {
        GLSLParser.Type_qualifierContext typeQualifierContext = ctx.type_qualifier();
        if (typeQualifierContext == null) {
            return;
        }
        GLSLParser.Single_type_qualifierContext singleTypeQualifierContext = typeQualifierContext.single_type_qualifier(0);
        if (singleTypeQualifierContext == null) {
            return;
        }
        GLSLParser.Storage_qualifierContext storageQualifierContext = singleTypeQualifierContext.storage_qualifier();
        if (storageQualifierContext != null && storageQualifierContext.CONST() != null) {
            if (ctx.getParent().getParent() instanceof GLSLParser.Function_prototypeContext proto) {
                if (this.functions.containsKey(proto.IDENTIFIER().getText())) {
                    this.functions.get(proto.IDENTIFIER().getText()).add(ctx.parameter_declarator().IDENTIFIER().getText());

                } else {
                    List<String> params = new ArrayList<>();
                    params.add(ctx.parameter_declarator().IDENTIFIER().getText());
                    this.functions.put(proto.IDENTIFIER().getText(), params);
                }
            }
        }
    }
}
