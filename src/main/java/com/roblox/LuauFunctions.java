package com.roblox;

/**
 * Contains all predefined functions.
 */
public final class LuauFunctions {

    public static void print(Object... args) { }
    public static String tostring(Object obj) { return ""; }
    public static int tonumber(Object obj) { return 0; }
    public static void error(String message, int n) { }
    public static void warn(String... args) { }
    public static String type(Object obj) { return ""; }
    public static String typeof(Object obj) { return ""; }
    public static Object getmetatable(Object obj) { return obj; }
    public static void setmetatable(Object obj, Object tab) { }
    public static Object rawget(Object obj, int index) { return obj; }
    public static Object rawset(Object obj, int index, Object value) { return obj; }
    public static int rawlen(Object obj) { return 0; }
    public static boolean raweq(Object obj1, Object obj2) { return true; }

    /** Equivalent to assert (v, k). */
    public static void robloxAssert(Object obj, String message) { }

}
