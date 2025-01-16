package org.robloxjava.transpiler;


import org.robloxjava.transpiler.transformer.JavaTransformer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Transpiler {

    /**
     * You **must** run this.
     */
    public static void Init() {
        LuauGenerator.setJavaParserThings();
    }


    public static final String runtimeLibLocation = "game:GetService(\"ReplicatedStorage\").__java.RuntimeLib";
    public static final String runtimeLibName = "Java";

    public static String parseJavaFile(String path, boolean coreTestingMode) {
        try {
            String fileContents = Files.readString(Path.of(path), StandardCharsets.UTF_8);
            return parseJavaSource(fileContents, coreTestingMode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String parseJavaSource(String source, boolean coreTestingMode) {
        source = JavaTransformer.Transform(source);
        LuauGenerator luauGenerator = new LuauGenerator(source);
        luauGenerator.luauAST.Render(0);

        if (coreTestingMode) {
            luauGenerator.luauWriter.writeLine("Program.main({}) -- ◀ Internal testing mode ◀");
        }

        return luauGenerator.luauWriter.getLuauSource();
    }

}
