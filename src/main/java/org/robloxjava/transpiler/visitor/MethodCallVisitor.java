package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.CallExpression;
import org.robloxjava.transpiler.luau.ast.Identifier;

import java.util.List;

public final class MethodCallVisitor {
    public static LuauNode visit(MethodCallExpr callExpr, LuauGenerator luauGenerator, boolean isStatement) {
        final var scope = callExpr.getScope();
        final List<LuauNode> arguments = callExpr.getArguments()
                .stream().map(param -> StandaloneExprVisitor.visit(param, luauGenerator)).toList();

        final ResolvedMethodDeclaration resolved = callExpr.resolve();


        if (scope.isPresent()) {
            var scopeNode = scope.get();
            if (scopeNode instanceof NameExpr) {

                return new CallExpression(new Identifier(STR."\{scope.get()}\{resolved.isStatic() ? "." : ":"}\{callExpr.getNameAsString()}"), arguments,
                        isStatement);
            }
            return new CallExpression(new Identifier(STR."\{scope.get()}.\{callExpr.getNameAsString()}"), arguments,
                    isStatement);

        } else {
            // self or class name?

            return new CallExpression(new Identifier(STR."\{resolved.getClassName()}.\{callExpr.getNameAsString()}"), arguments,
                    isStatement);
        }
    }

    public static void visit(MethodCallExpr callExpr, LuauGenerator luauGenerator, LuauNode baseLuauNode, boolean isStatement) {
        baseLuauNode.addChildNoKey(visit(callExpr, luauGenerator, isStatement));
    }

}
