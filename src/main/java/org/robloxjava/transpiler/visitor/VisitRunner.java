package org.robloxjava.transpiler.visitor;


import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;

public final class VisitRunner {

    public static void statementVisitRunner(Node baseNode, LuauGenerator luauGenerator) {
        baseNode.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(VariableDeclarator n, Object arg) {
                //VariableDeclVisitor.visit(n, luauGenerator);
            }


        }, null);
    }

}
