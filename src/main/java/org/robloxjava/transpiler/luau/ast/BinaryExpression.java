package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.RenderMacro;

import java.util.Objects;

public class BinaryExpression extends LuauNode {
    public final String operation;
    final LuauNode left, right;

    public BinaryExpression(LuauNode left, LuauNode right, String operation) {
        this.left = left;
        this.right = right;
        if (Objects.equals(operation, "||"))
            this.operation = "or";
        else if (Objects.equals(operation, "&&"))
            this.operation = "and";
        else
            this.operation = operation;
    }

    @Override
    public String Render(int layer) {
        // macro check
        if (Objects.equals(operation, "instanceof")) {
            return RenderMacro.macroInstanceof(left, right, layer);
        }

        return String.format("%s %s %s", left.Render(layer), operation, right.Render(layer));
    }
}
