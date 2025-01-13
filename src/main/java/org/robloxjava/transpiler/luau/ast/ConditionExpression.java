package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;


public class ConditionExpression extends LuauNode {
    private final LuauNode left, right;
    private final String operator;

    public ConditionExpression(LuauNode left, LuauNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String Render(int layer) {
        return STR."\{left.Render(layer)} \{operator} \{right.Render(layer)}";
    }
}
