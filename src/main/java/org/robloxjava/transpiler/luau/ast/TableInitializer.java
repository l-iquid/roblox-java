package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.LuauWriter;

import java.util.ArrayList;
import java.util.List;

public class TableInitializer extends LuauNode {
    private final List<LuauNode> values, keys;
    private final boolean isArray;

    public TableInitializer(List<LuauNode> values, List<LuauNode> keys, boolean isArray) {
        this.values = values;
        this.keys = keys;
        this.isArray = isArray;
    }

    @Override
    public String Render(int layer) {
        StringBuilder retStr = new StringBuilder("{");
        if (isArray) {
            final ArrayList<String> vals = new ArrayList<>();
            values.forEach(n -> vals.add(n.Render(layer)));
            retStr.append(LuauWriter.separateListCommas(vals));
        } else {
            ArrayList<String> keyValueList = new ArrayList<>();
            int index = 0;
            for (var key : keys) {
                keyValueList.add(STR."[\{key.Render(layer)}] = \{values.get(index).Render(layer)}");
                index++;
            }
            retStr.append(LuauWriter.separateListCommas(keyValueList));
        }

        retStr.append("}");
        return retStr.toString();
    }
}
