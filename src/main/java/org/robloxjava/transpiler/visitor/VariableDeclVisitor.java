package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.*;

import java.util.ArrayList;
import java.util.Optional;

public final class VariableDeclVisitor {
    public static void visit(VariableDeclarator variable, LuauGenerator luauGenerator, LuauNode baseLuauNode) {
        final String variableName = variable.getNameAsString();

        Optional<Expression> initializer = variable.getInitializer();

        if (initializer.isPresent()) {
            var realInitializer = initializer.get();

            if (realInitializer.isNameExpr()) {
                baseLuauNode.addChildNoKey(
                        new VariableDeclaration(variableName, new Identifier(realInitializer.asNameExpr().getNameAsString())));
                return;
            }

            if (realInitializer.isLiteralExpr()) {
                baseLuauNode.addChildNoKey(
                        new VariableDeclaration(variableName, new LiteralExpression(realInitializer.asLiteralExpr().toString())));
                return;
            }


        } else {
            baseLuauNode.addChildNoKey(new VariableDeclaration(variableName, new NilExpression()));
        }
    }
}
