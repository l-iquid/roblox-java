package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.LuauWriter;

/**
 * The root node.
 */
public class AST extends LuauNode {
    private final LuauWriter luauWriter; // we need a reference of the luauWriter

    public AST(LuauWriter luauWriter1) {
        luauWriter = luauWriter1;
    }

    @Override
    public String Render(int layer) {
        children.forEach((_, v) -> luauWriter.writeLine(v.Render(layer)));
        return "";
    }
}
