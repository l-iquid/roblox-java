package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.VariableReassign;

public final class FieldVisitor {
    public static void visit(FieldDeclaration field, LuauGenerator luauGenerator, LuauNode baseLuauNode, String className) {
        final var value = field.getVariables(); // retrieve list, all variables have same value

        for (VariableDeclarator variable : value) {
            if (field.isStatic()) {
                baseLuauNode.addChildNoKey(
                    new VariableReassign(
                            STR."\{className}.\{variable.getNameAsString()}",
                            StandaloneExprVisitor.visit(variable.getInitializer().orElse(new NullLiteralExpr()), luauGenerator)
                    )
                );
            } else {
                baseLuauNode.children.get("__constructor").addChildNoKey(
                        new VariableReassign(
                                STR."self.\{variable.getNameAsString()}",
                                StandaloneExprVisitor.visit(variable.getInitializer().orElse(new NullLiteralExpr()), luauGenerator)
                        )
                );
            }
        }
    }
}
