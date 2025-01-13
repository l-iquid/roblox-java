package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.VariableDeclaration;
import org.robloxjava.transpiler.luau.ast.VariableReassign;

import java.util.Optional;
import java.util.regex.Pattern;

public final class VariableReassignVisitor {

    public static void visit(AssignExpr variable, LuauGenerator luauGenerator, LuauNode baseLuauNode) {
        String variableName = variable.getTarget().toString();

        if (variableName.contains("this.")) {
            variableName = STR."self.\{variableName.split(Pattern.quote("."), 2)[1]}";
        }

        Expression initializer = variable.getValue();

        baseLuauNode.addChildNoKey(
                new VariableReassign(variableName, StandaloneExprVisitor.visit(initializer, luauGenerator)));

    }


}
