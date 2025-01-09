package org.robloxjava.transpiler;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Transpiler {

    public static String parseJavaFile(String path) {
        try {
            String fileContents = Files.readString(Path.of(path), StandardCharsets.UTF_8);
            return parseJavaSource(fileContents);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String parseJavaSource(String source) {
        LuauGenerator luauGenerator = new LuauGenerator(source);
        luauGenerator.luauAST.Render(0);

        return luauGenerator.luauWriter.getLuauSource();
    }

}
