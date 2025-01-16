package org.robloxjava.transpiler;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.robloxjava.transpiler.luau.LuauWriter;
import org.robloxjava.transpiler.luau.ast.AST;
import org.robloxjava.transpiler.visitor.ClassVisitor;
import org.robloxjava.transpiler.visitor.VisitUtil;

import java.util.Optional;

/**
 * The main class of the transpiler.
 * Java AST -> Luau AST.
 * @see org.robloxjava.transpiler.transformer.JavaTransformer for hidden Java AST changes.
 */
public class LuauGenerator {
    public static JavaParser javaParser = new JavaParser(new ParserConfiguration()
            .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21)
    );
    public static void setJavaParserThings() {
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());

        // Configure JavaParser to use type resolution
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        javaParser.getParserConfiguration().setSymbolResolver(symbolSolver);
    }


    public final LuauWriter luauWriter = new LuauWriter();
    public final AST luauAST = new AST(luauWriter);
    //public final VisitUtil visitUtil = new VisitUtil();

    // entry point
    public LuauGenerator(String source) {
        ParseResult<CompilationUnit> cu = javaParser.parse(source);

        cu.getResult().get().accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                // we only want the top level declarations aka classes and interfaces
                if (n.isInterface()) return;
                ClassVisitor.visit(n, LuauGenerator.this, Optional.empty());
            }


        }, null);
    }

}
