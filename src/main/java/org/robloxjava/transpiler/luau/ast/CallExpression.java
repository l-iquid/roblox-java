package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.LuauWriter;
import org.robloxjava.transpiler.luau.RenderMacro;

import java.util.List;
import java.util.Objects;

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

        if (functionName instanceof Identifier realFunctionName) {
            // some special cases on functionName
            if (Objects.equals(realFunctionName.value, "Objects.equals")) {
                return RenderMacro.macroObjectEquals(functionName, parameters, layer);
            }
            if (Objects.equals(realFunctionName.value, "Objects.isNull")) {
                return RenderMacro.macroObjectIsNull(functionName, parameters, layer);
            }
            if (Objects.equals(realFunctionName.value, "Objects.nonNull")) {
                return RenderMacro.macroObjectNotNull(functionName, parameters, layer);
            }
            if (Objects.equals(realFunctionName.value, "robloxAssert")) {
                return new CallExpression(new Identifier("assert"), parameters, isStatement).Render(layer);
            }
        }

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
