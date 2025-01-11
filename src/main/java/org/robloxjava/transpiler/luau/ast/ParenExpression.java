package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

import java.util.HashMap;

public class ParenExpression extends LuauNode {

    public ParenExpression(HashMap<String, LuauNode> children) {
        if (children != null) {
            this.children = children;
        }
    }

    @Override
    public String Render(int layer) {
        StringBuilder retStr = new StringBuilder();
        retStr.append("(");
        children.forEach((_, v) -> retStr.append(v.Render(layer)));
        retStr.append(")");
        return retStr.toString();
    }
}
