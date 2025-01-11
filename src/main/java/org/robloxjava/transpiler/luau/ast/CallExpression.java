package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.LuauWriter;

import java.util.List;

public class CallExpression extends LuauNode {
    final LuauNode functionName;
    final List<LuauNode> parameters;
    final boolean isStatement; // is it all by its own? not a part of another expression?

    public CallExpression(LuauNode functionName, List<LuauNode> parameters, boolean isStatement) {
        this.functionName = functionName;
        this.parameters = parameters;
        this.isStatement = isStatement;
    }

    @Override
    public String Render(int layer) {
        StringBuilder retStr = new StringBuilder();
        if (isStatement) {
            retStr.append("\t".repeat(layer)).append(STR."\{functionName.Render(layer)}(");
        } else {
            retStr.append(STR."\{functionName.Render(layer)}(");
        }
        retStr.append(LuauWriter.separateListCommas(
                parameters.stream()
                        .map(param -> param.Render(layer))
                        .toList()
        ));
        retStr.append(")");
        children.forEach((_, v) -> retStr.append("."+v.Render(layer)));
        return retStr.toString();
    }
}
