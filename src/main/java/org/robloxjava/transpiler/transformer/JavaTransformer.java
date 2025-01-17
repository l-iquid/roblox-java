package org.robloxjava.transpiler.transformer;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.robloxjava.transpiler.LuauGenerator;
import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.ast.VariableReassign;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * FYI this is an internal transformer to change the AST a bit, to make sure the core transpiler works properly.
 * The 'LuauGenerator' isn't considered a real transformer.
 */
public final class JavaTransformer {
    private static void fieldReferenceTransformer(CompilationUnit cu) {
        // edit the nameExpressions that reference fields to have either 'this.' or '<className>.'
        cu.accept(new VoidVisitorAdapter<>() {
            @Override
            public void visit(NameExpr n, Object arg) {
                Node curNode = n.getParentNode().get();
                final var nParent = n.getParentNode().get();

                while (true) {
                    // do some checks
                    if (curNode instanceof AssignExpr realCurNode) {
                        if (realCurNode.getTarget().isFieldAccessExpr() &&
                                Objects.equals(realCurNode.getTarget().asFieldAccessExpr().getNameAsString(), n.getNameAsString())) {
                            break;
                        }
                    }

                    if (curNode instanceof ClassOrInterfaceDeclaration realCurNode) {
                        List<FieldDeclaration> fieldDeclarationList = realCurNode.getFields();
                        boolean breakOut = false;

                        for (var field : fieldDeclarationList) {
                            var variablesList = field.getVariables();
                            for (var variable : variablesList) {
                                if (Objects.equals(variable.getNameAsString(), n.getNameAsString())) {
                                    // fr
                                    final boolean fieldIsStatic = field.isStatic();
                                    FieldAccessExpr fieldAccessExpr = new FieldAccessExpr();

                                    fieldAccessExpr.setName(String.valueOf(n));
                                    if (fieldIsStatic) {
                                        fieldAccessExpr.setScope(realCurNode.getNameAsExpression());
                                    }

                                    n.replace(fieldAccessExpr);

                                    breakOut = true;
                                }
                            }
                        }

                        if (breakOut) {
                            break;
                        }


                    }

                    var parentNode = curNode.getParentNode();

                    if (parentNode.isEmpty()) {
                        break;
                    }
                    curNode = parentNode.get();
                }
            }

        }, null);
    }

    public static String Transform(String source) {
        ParseResult<CompilationUnit> cu = LuauGenerator.javaParser.parse(source);
        var res = cu.getResult().orElse(new CompilationUnit());
        fieldReferenceTransformer(res);

        return res.toString();
    }
}
