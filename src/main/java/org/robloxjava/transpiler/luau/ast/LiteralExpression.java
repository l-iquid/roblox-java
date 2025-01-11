package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

import java.util.Objects;

public class LiteralExpression extends LuauNode {
    private final String value;

    public LiteralExpression(String value) {
        this.value = value;
    }

    @Override
    public String Render(int layer) {
        return Objects.equals(value, "null") ? "nil" : value;
    }
}
