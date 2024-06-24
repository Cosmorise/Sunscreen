package me.combimagnetron.sunscreen.config.element;

import org.jetbrains.annotations.Nullable;

public sealed interface Node<T> extends ConfigElement permits Node.SimpleNode {

    String name();

    @Nullable T value();

    Class<T> type();

    static <T> Node<T> required(String name, T value) {
        return new RequiredNode<>(name, value);
    }

    static <T> Node<T> required(String name, Class<T> clazz) {
        return new RequiredNode<>(name, clazz);
    }

    static <T> Node<T> optional(String name, Class<T> clazz) {
        return new OptionalNode<>(name, clazz);
    }

    static <T> Node<T> anonymous(Class<T> clazz) {
        return new AnonymousNode<>(clazz);
    }

    abstract sealed class SimpleNode<T> implements Node<T> permits AnonymousNode, OptionalNode, RequiredNode {
        private final String name;
        private final T t;
        private final Class<T> clazz;

        public SimpleNode(String name, T t) {
            this.name = name;
            this.t = t;
            this.clazz = (Class<T>) t.getClass();
        }

        public SimpleNode(String name, Class<T> clazz) {
            this.name = name;
            this.t = null;
            this.clazz = clazz;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public T value() {
            return t;
        }

        @Override
        public Class<T> type() {
            return clazz;
        }

    }

    final class RequiredNode<T> extends SimpleNode<T> {

        public RequiredNode(String name, T value) {
            super(name, value);
        }

        public RequiredNode(String name, Class<T> clazz) {
            super(name, clazz);
        }

    }

    final class OptionalNode<T> extends SimpleNode<T> {

        public OptionalNode(String name, T value) {
            super(name, value);
        }

        public OptionalNode(String name, Class<T> clazz) {
            super(name, clazz);
        }

    }

    final class AnonymousNode<T> extends SimpleNode<T> {

        public AnonymousNode(Class<T> clazz) {
            super("", clazz);
        }
    }

}
