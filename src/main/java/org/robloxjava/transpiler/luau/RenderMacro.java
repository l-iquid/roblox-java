package org.robloxjava.transpiler.luau;

import org.robloxjava.transpiler.Transpiler;
import org.robloxjava.transpiler.luau.ast.BinaryExpression;

import java.util.Objects;

/**
 * A collection of render macros.
 */
public final class RenderMacro {
    public static String macroInstanceof(LuauNode left, LuauNode right, int layer) {
        return String.format("%s.instanceOf(%s, %s)", Transpiler.runtimeLibName, left.Render(layer), right.Render(layer));
    }



}
