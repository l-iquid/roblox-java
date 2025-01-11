package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.CallExpression;
import org.robloxjava.transpiler.luau.ast.Identifier;
import org.robloxjava.transpiler.luau.ast.VariableDeclaration;

import java.util.List;
import java.util.Optional;

public final class VariableDeclVisitor {
    public static void visit(VariableDeclarator variable, LuauGenerator luauGenerator, LuauNode baseLuauNode) {
        final String variableName = variable.getNameAsString();

        Optional<Expression> initializer = variable.getInitializer();

        if (initializer.isPresent()) {
            var realInitializer = initializer.get();
            baseLuauNode.addChildNoKey(
                    new VariableDeclaration(variableName, StandaloneExprVisitor.visit(realInitializer, luauGenerator)));
        } else {
            baseLuauNode.addChildNoKey(new VariableDeclaration(variableName, null));
        }


    }
}
