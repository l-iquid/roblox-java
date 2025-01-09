package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.*;

import java.util.*;

public final class ClassVisitor {
    public static void visit(ClassOrInterfaceDeclaration declaration, LuauGenerator luauGenerator, Optional<LuauNode> baseNode) {
        final String className = declaration.getName().asString();
        final LuauNode nodeRoot = baseNode.orElseGet(() -> luauGenerator.luauAST);

        nodeRoot.addChildNoKey(
                new VariableDeclaration(className, new Identifier("{}")));

        nodeRoot.addChildNoKey(
                new VariableReassign(String.format("%s.__index", className), new Identifier(className)));

        List<ConstructorDeclaration> constructors = declaration.getConstructors();

        if (constructors.isEmpty()) { // there are no constructors, auto generate one
            HashMap<String, LuauNode> children = new HashMap<>();

            children.put("01", new VariableDeclaration("self", new Identifier(String.format("setmetatable({}, %s)", className))));

            children.put("02", new ReturnStatement("self"));

            FunctionDeclaration funcDeclaration =
                    new FunctionDeclaration(String.format("%s.constructor", className), Collections.emptyList(), Optional.of(children));

            declaration.accept(new VoidVisitorAdapter<>() {
                @Override
                public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                    if (!Objects.equals(n.getName().asString(), className))
                        ClassVisitor.visit(n, luauGenerator, Optional.of(funcDeclaration));
                }

            }, null);

            nodeRoot.addChildNoKey(funcDeclaration);
        }


        // add children

        declaration.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                MethodVisitor.visit(n, luauGenerator, className, Optional.of(nodeRoot));
            }

        }, null);

    }
}