package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class ReturnStatement extends LuauNode {
    private final LuauNode value;

    public ReturnStatement(LuauNode value) {
        this.value = value;
    }

    @Override
    public String Render(int layer) {
        return "\t".repeat(layer)+String.format("return %s", value == null ? "" : value.Render(layer));
    }
}
