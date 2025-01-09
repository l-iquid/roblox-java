package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.*;

import java.util.*;

// TODO Nested classes are WIP

public final class ClassVisitor {
    public static void visit(ClassOrInterfaceDeclaration declaration, LuauGenerator luauGenerator, Optional<LuauNode> baseNode) {
        final boolean isNested = declaration.isInnerClass();
        final String className = isNested ? STR."self.\{declaration.getName().asString()}" : declaration.getName().asString();
        final LuauNode nodeRoot = baseNode.orElseGet(() -> luauGenerator.luauAST);

        // #1 basic stuff
        nodeRoot.addChildNoKey( isNested ? new VariableReassign(className, new Identifier("{}")) :
                new VariableDeclaration(className, new Identifier("{}")) );

        nodeRoot.addChildNoKey(
                new VariableReassign(String.format("%s.__index", className), new Identifier(className)));

        // #2 constructor code
        List<ConstructorDeclaration> constructors = declaration.getConstructors();

        if (constructors.isEmpty()) { // there are no constructors, auto generate one
            HashMap<String, LuauNode> children = new HashMap<>();

            final String selfName = isNested ? "_self" : "self"; // TODO

            children.put("01", new VariableDeclaration("self", new Identifier(String.format("setmetatable({}, %s)", className))));

            FunctionDeclaration funcDeclaration =
                    new FunctionDeclaration(String.format("%s.constructor", className), Collections.emptyList(), Optional.of(children));


            declaration.getChildNodes().forEach(node -> {
                if (node instanceof ClassOrInterfaceDeclaration) {
                    // ugly code to detect nested classes since accept does not work
                    ClassVisitor.visit(((ClassOrInterfaceDeclaration) node).asClassOrInterfaceDeclaration(), luauGenerator, Optional.of(funcDeclaration));
                }
            });


            funcDeclaration.addChildNoKey(new ReturnStatement("self"));

            nodeRoot.addChildNoKey(funcDeclaration);
        }


        // #3 visit child nodes

        declaration.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                MethodVisitor.visit(n, luauGenerator, className, Optional.of(nodeRoot));
            }

        }, null);

    }
}