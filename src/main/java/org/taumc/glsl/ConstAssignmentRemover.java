package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

import java.util.List;

public class ConstAssignmentRemover extends GLSLParserBaseListener {

    private final String function;
    private final List<String> variables;

    public ConstAssignmentRemover(String function, List<String> variables) {
        this.function = function;
        this.variables = variables;
    }

    @Override
    public void enterVariable_identifier(GLSLParser.Variable_identifierContext ctx) {
        if (variables.contains(ctx.IDENTIFIER().getText())) {
            var parent = ctx.getParent();
            while (!(parent instanceof GLSLParser.Function_definitionContext definitionContext)) {
                parent = parent.getParent();
                if (parent == null) {
                    return;
                }
            }

            if (definitionContext.function_prototype().IDENTIFIER().getText().equals(this.function)) {
                parent = ctx.getParent();
                while (!(parent instanceof GLSLParser.Single_declarationContext declarationContext)) {
                    parent = parent.getParent();
                    if (parent == null) {
                        return;
                    }
                }

                variables.add(declarationContext.typeless_declaration().IDENTIFIER().getText());

                GLSLParser.Type_qualifierContext typeQualifierContext = declarationContext.fully_specified_type().type_qualifier();
                if (typeQualifierContext == null || typeQualifierContext.single_type_qualifier(0) == null) {
                    return;
                }
                GLSLParser.Storage_qualifierContext storageQualifierContext = typeQualifierContext.single_type_qualifier(0).storage_qualifier();
                if (storageQualifierContext != null && storageQualifierContext.CONST() != null) {
                    declarationContext.fully_specified_type().children.remove(typeQualifierContext);
                }
            }
        }
    }
}
