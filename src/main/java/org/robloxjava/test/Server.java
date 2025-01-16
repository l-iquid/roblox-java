package org.robloxjava.test;

import com.roblox.RbxNative;

import java.util.Objects;

import static com.roblox.LuauFunctions.*;

class SomeNativeClass {

    @RbxNative
    public void One() {

    }
}

public class Server {
    public static void main(String[] args) {
        print("Hello, world!");
        if (Objects.equals(typeof(3), "number")) {
            print("3 is a number");
        }

    }
}
