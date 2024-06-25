package me.combimagnetron.sunscreen.util;

public record Identifier(Namespace namespace, Key key) {
    public static final Namespace DEFAULT_NAMESPACE = Namespace.of("sunscreen");

    public static Identifier of(Key key) {
        return new Identifier(DEFAULT_NAMESPACE, key);
    }

    public static Identifier of(String key) {
        return new Identifier(DEFAULT_NAMESPACE, Key.of(key));
    }

    public static Identifier of(Namespace namespace, Key key) {
        return new Identifier(namespace, key);
    }

    public static Identifier of(String namespace, String key) {
        return new Identifier(Namespace.of(namespace), Key.of(key));
    }

    public String string() {
        return namespace.string() + ":" + key.string();
    }

    public record Namespace(String string) {
        public static Namespace of(String string) {
            return new Namespace(string);
        }
    }

    public record Key(String string) {
        public static Key of(String string) {
            return new Key(string);
        }
    }

}
