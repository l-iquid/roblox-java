package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.stmt.AssertStmt;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.BinaryExpression;
import org.robloxjava.transpiler.luau.ast.CallExpression;
import org.robloxjava.transpiler.luau.ast.Identifier;

import java.util.List;

public final class AssertVisitor {
    public static void visit(AssertStmt assertStmt, LuauGenerator luauGenerator, LuauNode baseLuauNode) {
        final var checkExpression = assertStmt.getCheck().asBinaryExpr();
        final var messageExpression = assertStmt.getMessage();

        baseLuauNode.addChildNoKey(new CallExpression(new Identifier("assert"), messageExpression.isPresent() ? List.of(
                StandaloneExprVisitor.visit(checkExpression, luauGenerator),
                StandaloneExprVisitor.visit(messageExpression.get(), luauGenerator)
        ) : List.of(StandaloneExprVisitor.visit(checkExpression, luauGenerator)), true));
    }
}
