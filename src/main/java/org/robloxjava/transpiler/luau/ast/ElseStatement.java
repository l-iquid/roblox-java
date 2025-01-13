package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

import java.util.concurrent.atomic.AtomicInteger;

public class ElseStatement extends LuauNode {


    @Override
    public String Render(int layer) {
        StringBuilder retStr = new StringBuilder(STR."\{"\t".repeat(layer-1)}else\n");
        AtomicInteger count = new AtomicInteger(); // WE NEED TO MAKE IT LOOK PRETTY, IF NOT THERE WILL BE A EXTRA TAB
        children.forEach((_, v) -> {
            retStr.append(STR."\{v.Render(layer)}\{count.get() == children.size() - 1 ? "" : "\n"}");
            count.getAndIncrement();
        });

        return retStr.toString();
    }
}
