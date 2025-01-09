package org.robloxjava.transpiler.luau;

import java.util.HashMap;

/**
 * The base class for all luau nodes.
 */
public abstract class LuauNode {
    public HashMap<String, LuauNode> children = new HashMap<>();

    /**
     * Adds a child node without a key specified.
     */
    public void addChildNoKey(LuauNode node) {
        children.put(Integer.toString(children.size()), node);
    }

    public abstract String Render(int layer);

}