package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;
import org.robloxjava.transpiler.luau.LuauWriter;

import java.util.ArrayList;
import java.util.List;

public class TableInitializer extends LuauNode {
    private final List<String> values, keys;
    private final boolean isArray;

    public TableInitializer(List<String> values, List<String> keys, boolean isArray) {
        this.values = values;
        this.keys = keys;
        this.isArray = isArray;
    }

    @Override
    public String Render(int layer) {
        StringBuilder retStr = new StringBuilder("{");
        if (isArray) {
            retStr.append(LuauWriter.separateListCommas(values));
        } else {
            ArrayList<String> keyValueList = new ArrayList<>();
            int index = 0;
            for (var key : keys) {
                keyValueList.add(STR."[\{key}] = \{values.get(index)}");
                index++;
            }
            retStr.append(LuauWriter.separateListCommas(keyValueList));
        }

        retStr.append("}");
        return retStr.toString();
    }
}
