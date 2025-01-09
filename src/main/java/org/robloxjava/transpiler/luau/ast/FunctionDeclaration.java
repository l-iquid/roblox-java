package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.LuauWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FunctionDeclaration extends LuauNode {
    private final String functionName;
    private final List<String> parameters;

    public FunctionDeclaration(String functionName, List<String> parameters, Optional<HashMap<String, LuauNode>> children) {
        this.functionName = functionName;
        this.parameters = parameters;
        children.ifPresent(stringLuauNodeHashMap -> this.children = stringLuauNodeHashMap);
    }

    @Override
    public String Render(int layer) {
        StringBuilder ret = new StringBuilder();
        ret.append("\t".repeat(layer)+String.format("function %s(", functionName));
        ret.append(LuauWriter.separateListCommas(parameters));
        ret.append(")\n");

        children.forEach((_, v) -> ret.append(STR."\{v.Render(layer + 1)}\n"));

        ret.append("\t".repeat(layer)+"end");
        return ret.toString();
    }
}
