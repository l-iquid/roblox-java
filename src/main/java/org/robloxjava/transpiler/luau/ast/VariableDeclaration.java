package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class VariableDeclaration extends LuauNode {
    private final String variableName;
    private final LuauNode variableValue;

    public VariableDeclaration(String variableName, LuauNode variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    @Override
    public String Render(int layer) {
        return "\t".repeat(layer)+(variableValue == null ? String.format("local %s", variableName) :
                String.format("local %s = %s", variableName, variableValue.Render(layer)));
    }
}
