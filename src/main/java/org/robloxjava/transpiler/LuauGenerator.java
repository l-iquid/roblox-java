package org.robloxjava.transpiler;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.luau.LuauWriter;
import org.robloxjava.transpiler.luau.ast.AST;
import org.robloxjava.transpiler.visitor.ClassVisitor;

import java.util.Optional;

/**
 * The main class of the transpiler.
 * Java AST -> Luau AST.
 */
public class LuauGenerator {
    public final LuauWriter luauWriter = new LuauWriter();
    public final AST luauAST = new AST(luauWriter);



    // entry point
    public LuauGenerator(String source) {
        CompilationUnit cu = StaticJavaParser.parse(source);


        cu.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                // we only want the top level declarations aka classes and interfaces

                ClassVisitor.visit(n, LuauGenerator.this, Optional.empty());
            }


        }, null);
    }

}
