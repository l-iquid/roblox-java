package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.type.ReferenceType;
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
import java.util.regex.Pattern;

/**
 * This is for expressions like 1 + 1, a + (b - a) * t, etc.
 * Also solves java things, like
 */
public final class StandaloneExprVisitor {
    private static LuauNode controlLoop(Node expr, LuauGenerator luauGenerator) {
        if (expr instanceof BinaryExpr realExpr) {
            return new BinaryExpression(
                    controlLoop(realExpr.getLeft(), luauGenerator),
                    controlLoop(realExpr.getRight(), luauGenerator),
                    realExpr.getOperator().asString()
            );
        }

        if (expr instanceof LiteralExpr realExpr) {
            return new LiteralExpression(expr.toString());
        }
        if (expr instanceof NameExpr realExpr) {
            String variableName = realExpr.toString();
            return new Identifier(variableName);
        }
        if (expr instanceof ArrayInitializerExpr realExpr) {
            final NodeList<Expression> values = realExpr.getValues();
            final List<LuauNode> nodes = values.stream().map(n -> StandaloneExprVisitor.visit(n, luauGenerator)).toList();
            return new TableInitializer(nodes, null, true);
        }
        if (expr instanceof ArrayCreationExpr realExpr) {
            final Optional<ArrayInitializerExpr> initializerExpr = realExpr.getInitializer();
            return StandaloneExprVisitor.visit(
                    initializerExpr.orElse(new ArrayInitializerExpr()),
                    luauGenerator
            );
        }
        if (expr instanceof FieldAccessExpr realExpr) {
            String variableName = realExpr.toString();
            if (variableName.contains("this.")) {
                variableName = STR."self.\{variableName.split(Pattern.quote("."), 2)[1]}";
            }
            return new Identifier(variableName);
        }
        if (expr instanceof ReferenceType realExpr) {
            return new Identifier(realExpr.toString());
        }
        if (expr instanceof ObjectCreationExpr realExpr) {
            final List<LuauNode> arguments = realExpr.getArguments()
                    .stream().map(param -> StandaloneExprVisitor.visit(param, luauGenerator)).toList();
            final String typeName = realExpr.getTypeAsString().split(Pattern.quote("<"), 2)[0];

            // macroing
            if (typeName.equals("ArrayList") || typeName.equals("List")) {
                return StandaloneExprVisitor.visit(new ArrayInitializerExpr(), luauGenerator);
            }

            return new CallExpression(new Identifier(STR."\{typeName}.constructor"), arguments, false);
        }
        if (expr instanceof MethodCallExpr realExpr) {
            return MethodCallVisitor.visit(realExpr, luauGenerator, false);
        }
        if (expr instanceof InstanceOfExpr realExpr) {
            return new BinaryExpression(
                    controlLoop(realExpr.getExpression(), luauGenerator),
                    controlLoop(realExpr.getType(), luauGenerator),
                    "instanceof"
            );
        }

        return null;
    }

    public static LuauNode visit(Node expr, LuauGenerator luauGenerator) {
        return controlLoop(expr, luauGenerator);
    }
}
