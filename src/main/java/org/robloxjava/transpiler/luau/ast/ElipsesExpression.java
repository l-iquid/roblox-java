package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

// vararg
public class ElipsesExpression extends LuauNode {

    @Override
    public String Render(int layer) {
        return "...";
    }
}
