package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class NilExpression extends LuauNode {

    @Override
    public String Render(int layer) {
        return "nil";
    }
}