package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This is for expressions like 1 + 1, a + (b - a) * t, etc.
 * Also solves java things, like
 */
public final class StandaloneExprVisitor {
    private static LuauNode controlLoop(Node expr, LuauGenerator luauGenerator) {
        if (expr instanceof BinaryExpr) {
            var realExpr = ((BinaryExpr) expr).asBinaryExpr();
            return new BinaryExpression(
                    controlLoop(realExpr.getLeft(), luauGenerator),
                    controlLoop(realExpr.getRight(), luauGenerator),
                    realExpr.getOperator().asString()
            );

        }
        if (expr instanceof LiteralExpr) {
            var realExpr = ((LiteralExpr) expr).asLiteralExpr();
            return new LiteralExpression(expr.toString());
        }
        if (expr instanceof NameExpr) {
            var realExpr = ((NameExpr) expr).asNameExpr();
            return new Identifier(realExpr.toString());
        }
        if (expr instanceof ObjectCreationExpr) {
            var realExpr = ((ObjectCreationExpr) expr).asObjectCreationExpr();
            final List<LuauNode> arguments = realExpr.getArguments()
                    .stream().map(param -> StandaloneExprVisitor.visit(param, luauGenerator)).toList();
            return new CallExpression(new Identifier(STR."\{realExpr.getTypeAsString()}.constructor"), arguments, false);
        }
        if (expr instanceof MethodCallExpr) {
            // todo code is a little messy
            var realExpr = ((MethodCallExpr) expr).asMethodCallExpr();

            return MethodCallVisitor.visit(realExpr, luauGenerator, false);
        }

        return null;
    }

    public static LuauNode visit(Node expr, LuauGenerator luauGenerator) {

        LuauNode result = controlLoop(expr, luauGenerator);


        return result;

    }
}