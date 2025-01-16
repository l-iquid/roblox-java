package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class VariableReassign extends LuauNode {
    public final String variableName;
    private final LuauNode variableValue;

    public VariableReassign(String variableName, LuauNode variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    @Override
    public String Render(int layer) {
        return "\t".repeat(layer)+String.format("%s = %s", variableName, variableValue.Render(layer));
    }
}
