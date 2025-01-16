package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.DoEndStatement;
import org.robloxjava.transpiler.luau.ast.FunctionDeclaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class MethodVisitor {
    public static void visit(MethodDeclaration method, LuauGenerator luauGenerator, String baseClassName, Optional<LuauNode> baseNode) {
        final String methodName = method.getName().toString();
        List<String> parameters = VisitUtil.MethodParameterNames(method);
        final LuauNode nodeRoot = baseNode.orElseGet(() -> luauGenerator.luauAST);
        // parent node is the parent class fyi

        final FunctionDeclaration funcDeclaration =
                new FunctionDeclaration(String.format("%s%s%s", baseClassName, method.isStatic() ? "." : ":", methodName),
                        parameters, Optional.empty(),
                        nodeRoot instanceof DoEndStatement ?(DoEndStatement) nodeRoot : null);

        nodeRoot.addChildNoKey(funcDeclaration);
        VisitUtil.MethodVisitRunner(method, luauGenerator, funcDeclaration);

    }
}
