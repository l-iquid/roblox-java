package org.robloxjava.transpiler.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.*;

import java.util.*;

// TODO Nested classes are WIP

public final class ClassVisitor {
    final String selfName = "self";

    // the ultimate origin
    public static void visit(ClassOrInterfaceDeclaration declaration, LuauGenerator luauGenerator, Optional<LuauNode> baseNode) {
        final boolean isNested = declaration.isInnerClass();
        final String className = isNested ? STR."self.\{declaration.getName().asString()}" : declaration.getName().asString();

        final DoEndStatement classWrapperNode = new DoEndStatement();
        final LuauNode baseNodeForWrapper = baseNode.orElse(luauGenerator.luauAST);
        baseNodeForWrapper.addChildNoKey(new LuaComment(false, String.format("▼ Class definition '%s' ▼", className)));

        // #1 basic stuff

        baseNodeForWrapper.addChildNoKey( isNested ? new VariableReassign(className, new Identifier("{}")) :
                new VariableDeclaration(className, new Identifier("{}")) );

        baseNodeForWrapper.addChildNoKey(classWrapperNode);

        classWrapperNode.addChildNoKey(
                new VariableReassign(String.format("%s.__className", className), new LiteralExpression(STR."\"\{className}\"")));

        classWrapperNode.addChildNoKey(
                new VariableReassign(String.format("%s.__index", className), new Identifier(className)));

        classWrapperNode.addChildNoKey(
                new FunctionDeclaration(String.format("%s.__tostring", className), Collections.emptyList(),
                        Optional.of(new HashMap<>() {{
                            put("01", new ReturnStatement(new LiteralExpression(STR."\"\{className}\"")));
                        }}))
        );

        // #2 constructor code
        List<ConstructorDeclaration> constructors = declaration.getConstructors();

        if (constructors.isEmpty()) { // there are no constructors, auto generate one
            ConstructorVisitor.visit(Optional.empty(), luauGenerator, classWrapperNode, className,
                    Optional.ofNullable(String.format("%s.constructor", className)));
        } else {
            int counter = 0;
            for (ConstructorDeclaration constructor : constructors) {
                final String constructorName = constructors.size() < 2 ? String.format("%s.constructor", className) :
                        String.format("%s.constructor%d", className, counter);
                ConstructorVisitor.visit(Optional.of(constructor), luauGenerator, classWrapperNode, className,
                        Optional.ofNullable(constructorName));
                counter++;
            }
        }

        // #3 look for nested classes
        declaration.getChildNodes().forEach(node -> {
            if (node instanceof ClassOrInterfaceDeclaration) {
                // ugly code to detect nested classes since accept does not work
                ClassVisitor.visit(((ClassOrInterfaceDeclaration) node).asClassOrInterfaceDeclaration(), luauGenerator,
                        Optional.of(classWrapperNode));
            }
        });

        // #4 visit child nodes

        declaration.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                if (!n.isConstructorDeclaration())
                    MethodVisitor.visit(n, luauGenerator, className, Optional.of(classWrapperNode));
            }

            @Override
            public void visit(FieldDeclaration n, Object arg) {
                FieldVisitor.visit(n, luauGenerator, classWrapperNode, className);
            }

        }, null);

        // FYI I transported some of the ConstructorVisitor code for the FieldVisitor to work correctly
        // run constructor code
        for (ConstructorDeclaration constructor : constructors) {
            VisitUtil.MethodVisitRunner(constructor, luauGenerator, classWrapperNode.children.get("__constructor"));
        }
        // return may appear before the fields
        classWrapperNode.children.get("__constructor").addChildNoKey(new ReturnStatement(new Identifier("self")));

        baseNodeForWrapper.addChildNoKey(new LuaComment(false, String.format("▲ Class definition '%s' ▲", className)));

    }
}