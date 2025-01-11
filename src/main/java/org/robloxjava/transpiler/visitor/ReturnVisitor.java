package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.ReturnStatement;

public final class ReturnVisitor {
    public static void visit(ReturnStmt returnStmt, LuauGenerator luauGenerator, LuauNode baseLuauNode) {
        ReturnStatement retNode = new ReturnStatement(
                StandaloneExprVisitor.visit(returnStmt.getExpression().orElse(new NullLiteralExpr()), luauGenerator));

        baseLuauNode.addChildNoKey(retNode);
    }
}
