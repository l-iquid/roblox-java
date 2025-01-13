package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

// can double as a else if
public class IfStatement extends LuauNode {
    private final LuauNode cond;
    private final boolean isElseIf;

    public IfStatement(LuauNode cond, boolean isElseIf) {
        this.cond = cond;
        this.isElseIf = isElseIf;
    }

    @Override
    public String Render(int layer) {
        // java ahh caused edge cases
        StringBuilder retStr = new StringBuilder();
        retStr.append(STR."\{"\t".repeat(isElseIf ? layer - 1 : layer)}\{isElseIf ? "elseif" : "if"} ");
        retStr.append(cond.Render(layer));
        retStr.append(" then\n");

        children.forEach((_, v) -> retStr.append(STR."\{v.Render(isElseIf ?
                layer : layer + 1)}\{v instanceof ElseStatement ? "" : "\n"}"));

        if (!isElseIf)
            retStr.append(STR."\{"\t".repeat(layer)}end");
        return retStr.toString();
    }
}
