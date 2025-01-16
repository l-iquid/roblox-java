package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithParameters;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;

import java.util.List;
import java.util.Optional;

/**
 * A collection of utilities to help visitor classes.
 */
public class VisitUtil {



    /**
     * @see ConstructorVisitor
     * @see MethodVisitor
     */
    public static <T extends Node> void MethodVisitRunner(T method, LuauGenerator luauGenerator, LuauNode baseNode) {

        method.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(VariableDeclarator n, Object arg) {
                VariableDeclVisitor.visit(n, luauGenerator, baseNode);
            }

            @Override
            public void visit(AssertStmt n, Object arg) {
                AssertVisitor.visit(n, luauGenerator, baseNode);
            }

            @Override
            public void visit(AssignExpr n, Object arg) {
                VariableReassignVisitor.visit(n, luauGenerator, baseNode);
            }

            @Override
            public void visit(MethodCallExpr n, Object arg) {
                MethodCallVisitor.visit(n, luauGenerator, baseNode, true);
            }

            @Override
            public void visit(ReturnStmt n, Object arg) {
                ReturnVisitor.visit(n, luauGenerator, baseNode);
            }

            @Override
            public void visit(IfStmt n, Object arg) {
                IfVisitor.visit(n, luauGenerator, baseNode, false);
            }

            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                // class in method
                ClassVisitor.visit(n, luauGenerator, Optional.of(baseNode));
            }

        }, null);

    }

    /**
     * Gets the string names of the parameters.
     */
    public static <T extends NodeWithParameters<? extends Node>> List<String> MethodParameterNames(T method) {
        return method.getParameters().stream()
                .map(parameter -> parameter.getName().toString())
                .toList();
    }



}
