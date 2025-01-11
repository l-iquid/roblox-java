package org.robloxjava;

import org.robloxjava.transpiler.Transpiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        // tempout folder is for testing purposes

        Transpiler.Init();

        FileWriter tempOut = new FileWriter("src/main/java/org/robloxjava/tempout/TestOut.lua");
        tempOut.write(Transpiler.parseJavaFile("src/main/java/org/robloxjava/test/Program.java"));
        tempOut.close();

    }

}