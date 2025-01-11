package org.robloxjava.transpiler.luau.ast;

import org.robloxjava.transpiler.luau.LuauNode;

public class LuaComment extends LuauNode {
    private final boolean isMultiline; // if it is multiline, all \n's will be new lines
    private final String comment;

    public LuaComment(boolean isMultiline, String comment) {
        this.isMultiline = isMultiline;
        this.comment = comment;
    }

    @Override
    public String Render(int layer) {
        StringBuilder retString = new StringBuilder();
        if (isMultiline) {
            retString.append(STR."\{"\t".repeat(layer)}--[[\n");
            // break it up using split
            final String[] splittedString = comment.split("\n");
            for (var str : splittedString) {
                retString.append(STR."\{"\t".repeat(layer)} \{str}");
            }
            retString.append(STR."\{"\t".repeat(layer)}]]\n");
        } else {
            retString.append(STR."\{"\t".repeat(layer)}-- \{comment}");
        }

        return retString.toString();
    }
}
