package me.combimagnetron.sunscreen.script;

import java.util.regex.Pattern;

public class Token {
    private final Type type;
    private final String captured;

    public Token(Type type, String captured) {
        this.type = type;
        this.captured = captured;
    }

    public Type type() {
        return this.type;
    }

    public String captured() {
        return this.captured;
    }


    public enum Type {
        OBJECT("/([A-Z]\\w*)/g"), METHOD_REFERENCE("/(\\.[a-z]+)*/g"), TEXT("/\"(.*?)\"/g"), NUMBER("/[\\d\\.]+/g");

        private final String pattern;

        Type(String pattern) {
            this.pattern = pattern;
        }

        public String patternLiteral() {
            return pattern;
        }

        public Pattern pattern() {
            return Pattern.compile(pattern);
        }

    }

}
