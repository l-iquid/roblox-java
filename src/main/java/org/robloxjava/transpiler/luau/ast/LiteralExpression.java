package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class LiteralExpression extends LuauNode {
    private final String value;

    public LiteralExpression(String value) {
        this.value = value;
    }

    @Override
    public String Render(int layer) {
        return value;
    }
}
