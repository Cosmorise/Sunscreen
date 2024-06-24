package me.combimagnetron.sunscreen.script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public class Lexer {
    private final static Set<Character> IGNORED = Set.of('\r', '\n', (char) 8, (char) 9, (char) 11, (char) 12, (char) 32);
    private final List<Token> tokenList = new LinkedList<>();
    private final StringBuilder content = new StringBuilder();
    private Token current;

    private Lexer(File file) {
        try (Stream<String> stream = Files.lines(Path.of(file.getAbsolutePath()))) {
            stream.forEach(content::append);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        advance();
        removeSpaces();
    }

    private void removeSpaces() {
        int found = 0;
        for(int i = 0; i < content.length(); i++) {
            if (!Character.isWhitespace(content.charAt(i))) {
                content.setCharAt(found++, content.charAt(i));
            }
        }
        content.delete(found, content.length());
    }

    private void advance() {
        Token.Type.VALUES.values().forEach(type -> {
            int i = -1;
            Matcher matcher = type.pattern().matcher(content);
            if (matcher.find()) {
                i = matcher.end();
            }
            if (i != -1) {
                current = Token.token(type, content.substring(0, i));
                content.delete(0, i);
            }
        });
    }

}
