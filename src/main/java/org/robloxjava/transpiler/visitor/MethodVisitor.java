package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.FunctionDeclaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public final class MethodVisitor {
    public static void visit(MethodDeclaration method, LuauGenerator luauGenerator, String baseClassName, Optional<LuauNode> baseNode) {
        final String methodName = method.getName().toString();
        ArrayList<String> parameters = new ArrayList<>();
        final LuauNode nodeRoot = baseNode.orElseGet(() -> luauGenerator.luauAST);

        method.getParameters().forEach(parameter ->
                parameters.add(parameter.getName().toString()));

        final FunctionDeclaration funcDeclaration =
                new FunctionDeclaration(String.format("%s%s%s", baseClassName, method.isStatic() ? "." : ":", methodName),
                        parameters, Optional.empty());

        method.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(VariableDeclarator n, Object arg) {
                VariableDeclVisitor.visit(n, luauGenerator, funcDeclaration);
            }

            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                // class in method
                ClassVisitor.visit(n, luauGenerator, Optional.of(funcDeclaration));
            }

        }, null);





        nodeRoot.addChildNoKey(funcDeclaration);

    }
}
