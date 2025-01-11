package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class BinaryExpression extends LuauNode {
    final String operation;
    final LuauNode left, right;

    public BinaryExpression(LuauNode left, LuauNode right, String operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public String Render(int layer) {
        return String.format("%s %s %s", left.Render(layer), operation, right.Render(layer));
    }
}
