package me.combimagnetron.sunscreen.script;

import me.combimagnetron.sunscreen.util.Values;

import java.util.regex.Pattern;

public record Token(Type type, String captured) {

    public static Token token(Type type, String captured) {
        return new Token(type, captured);
    }

    public interface Type {
        Type OBJECT = Impl.of("([A-Z]\\w*)");
        Type METHOD_REFERENCE = Impl.of("(\\.[a-z]+)*");
        Type TEXT = Impl.of("(\"(.*?)\")");
        Type NUMBER = Impl.of("([\\d\\.]+)");
        Type SECTION_OPEN = Impl.of("{");
        Type SECTION_CLOSE = Impl.of("}");
        Type CONDITION = Impl.of("(\\[.*?\\])");
        Type FUNCTION = Impl.of("([a-z]+(\\(.*?\\)))");
        Type REFERENCE = Impl.of("(((?<!\\w)\\(.*?\\))\\s*(\\[.*?\\])* ->)");
        Values<Type> VALUES = Values.of(OBJECT, METHOD_REFERENCE, TEXT, NUMBER, SECTION_CLOSE, SECTION_OPEN, CONDITION, FUNCTION, REFERENCE);

        Pattern pattern();

        record Impl(String literalPattern) implements Type {
            public static Impl of(String literalPattern) {
                return new Impl(literalPattern);
            }

            @Override
            public Pattern pattern() {
                return Pattern.compile(literalPattern);
            }
        }

    }

}
