package org.robloxjava.transpiler.luau;

import org.robloxjava.transpiler.Transpiler;
import org.robloxjava.transpiler.luau.ast.BinaryExpression;

import java.util.List;
import java.util.Objects;

/**
 * A collection of render macros.
 */
public final class RenderMacro {
    public static String macroInstanceof(LuauNode left, LuauNode right, int layer) {
        return String.format("%s.instanceOf(%s, %s)", Transpiler.runtimeLibName, left.Render(layer), right.Render(layer));
    }

    // Object.equals(thing1, thing2) -> thing1 == thing2
    public static String macroObjectEquals(LuauNode functionName, List<LuauNode> parameters, int layer) {
        return STR."(\{parameters.getFirst().Render(layer)} == \{parameters.getLast().Render(layer)})";
    }

    public static String macroObjectIsNull(LuauNode functionName, List<LuauNode> parameters, int layer) {
        return STR."(\{parameters.getFirst().Render(layer)} == nil)";
    }
    public static String macroObjectNotNull(LuauNode functionName, List<LuauNode> parameters, int layer) {
        return STR."(\{parameters.getFirst().Render(layer)} ~= nil)";
    }


}
