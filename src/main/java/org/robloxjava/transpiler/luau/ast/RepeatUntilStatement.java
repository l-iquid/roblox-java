package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class RepeatUntilStatement extends LuauNode {
    private final LuauNode untilCondition;

    public RepeatUntilStatement(LuauNode untilCondition) {
        this.untilCondition = untilCondition;
    }

    @Override
    public String Render(int layer) {
        StringBuilder retStr = new StringBuilder("repeat\n");

        children.forEach((_, v) -> retStr.append(STR."\{v.Render(layer)}\n"));

        retStr.append(STR."until not (\{untilCondition.Render(layer)})");
        return retStr.toString();
    }
}
