package org.robloxjava.transpiler.visitor;


import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public final class ConstructorVisitor {
    public static void visit(Optional<ConstructorDeclaration> constructor, LuauGenerator luauGenerator, DoEndStatement classWrapperNode, String className, Optional<String> constructorName) {
        HashMap<String, LuauNode> children = new HashMap<>();

        // #1 basic stuff
        children.put("01", new VariableDeclaration("self", new Identifier(String.format("setmetatable({}, %s)", className))));

        FunctionDeclaration funcDeclaration;

        if (constructor.isPresent()) {
            final ConstructorDeclaration realConstructor = constructor.get();
            // #2 constructor parameters
            final List<String> parameterNames = VisitUtil.MethodParameterNames(realConstructor);

            funcDeclaration = new FunctionDeclaration(constructorName.orElse("constructor"), parameterNames, Optional.of(children));

        } else {
            funcDeclaration = new FunctionDeclaration(constructorName.orElse("constructor"), Collections.emptyList(), Optional.of(children));
        }


        classWrapperNode.children.put("__constructor", funcDeclaration);


    }
}
