package org.robloxjava;

import org.robloxjava.transpiler.Transpiler;


public class Main {

    public static void main(String[] args) {

        System.out.println(Transpiler.parseJavaFile("src/main/java/org/robloxjava/test/Program.java"));

    }

}