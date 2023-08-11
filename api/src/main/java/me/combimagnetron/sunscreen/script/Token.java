package me.combimagnetron.sunscreen.script;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public record Token(Type<?> type, String captured) {

    public interface Type<T> {
        Type<Object> OBJECT = Impl.of("/([A-Z]\\w*)/g");
        Type<Method> METHOD_REFERENCE = Impl.of("/(\\.[a-z]+)*/g");
        Type<String> TEXT = Impl.of("/\"(.*?)\"/g");
        Type<Double> NUMBER = Impl.of("/[\\d\\.]+/g");
        Type<?>[] VALUES = new Type[]{OBJECT, METHOD_REFERENCE, TEXT, NUMBER};

        Pattern pattern();

        record Impl<T>(String literalPattern) implements Type<T> {
            public static <T> Impl<T> of(String literalPattern) {
                return new Impl<>(literalPattern);
            }

            @Override
            public Pattern pattern() {
                return Pattern.compile(literalPattern);
            }
        }

    }

}
