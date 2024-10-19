package org.taumc.glsl;

import org.taumc.glsl.grammar.GLSLParser;
import org.taumc.glsl.grammar.GLSLParserBaseListener;

public class StructArrayRewriter extends GLSLParserBaseListener {

    public StructArrayRewriter() {}

    @Override
    public void enterStruct_declaration(GLSLParser.Struct_declarationContext ctx) {
        if (ctx.type_specifier().array_specifier() != null) {
            var array_specifier = ctx.type_specifier().array_specifier();
            if (array_specifier.dimension().get(0).constant_expression() != null) {
                return;
            }
            ctx.type_specifier().children.remove(array_specifier);
            for (var entry : ctx.struct_declarator_list().struct_declarator()) {
                array_specifier.setParent(entry);
                entry.children.add(array_specifier);
            }
        }
    }
}
