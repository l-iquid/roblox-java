package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.ElseStatement;
import org.robloxjava.transpiler.luau.ast.IfStatement;

import java.util.Optional;

public final class IfVisitor {
    public static void visit(IfStmt ifStmt, LuauGenerator luauGenerator, LuauNode baseLuauNode, boolean isElseIf) {
        final var condition = ifStmt.getCondition();

        IfStatement statement = new IfStatement(StandaloneExprVisitor.visit(condition, luauGenerator), isElseIf);

        VisitUtil.MethodVisitRunner(ifStmt.getThenStmt(), luauGenerator, statement);
        Optional<Statement> elseBranch = ifStmt.getElseStmt();
        if (ifStmt.hasCascadingIfStmt()) {
            // else if
            elseBranch.ifPresent(value -> visit(value.asIfStmt(), luauGenerator, statement, true));
        } else {
            elseBranch.ifPresent(value -> {
                ElseStatement elseExpression = new ElseStatement();
                VisitUtil.MethodVisitRunner(value, luauGenerator, elseExpression);
                statement.addChildNoKey(elseExpression);
            });
        }

        baseLuauNode.addChildNoKey(statement);
    }

}
