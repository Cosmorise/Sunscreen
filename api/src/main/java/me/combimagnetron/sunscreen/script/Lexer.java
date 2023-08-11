package me.combimagnetron.sunscreen.script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Lexer {
    private final StringBuilder content = new StringBuilder();
    private Token current;

    private Lexer(File file) {
        try (Stream<String> stream = Files.lines(Path.of(file.getAbsolutePath()))) {
            stream.forEach(content::append);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        advance();
    }

    private void advance() {

    }

}
