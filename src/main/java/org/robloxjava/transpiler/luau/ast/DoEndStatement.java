package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class DoEndStatement extends LuauNode {

    @Override
    public String Render(int layer) {
        StringBuilder retString = new StringBuilder();
        retString.append("\t".repeat(layer)+"do\n");
        children.forEach((_, v) -> retString.append(STR."\{v.Render(layer + 1)}\n"));
        retString.append("\t".repeat(layer)+"end");
        return retString.toString();
    }
}
