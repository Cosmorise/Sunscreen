package me.combimagnetron.sunscreen.menu.input;

import org.jetbrains.annotations.Nullable;

public interface Input<T> {

    @Nullable T value();

    Type type();

    record Impl<T>(T value, Type type) implements Input<T> {

    }

    static <T> Input<T> of(T value, Type type) {
        return new Impl<>(value, type);
    }

    enum Type {
        RIGHT_CLICK, LEFT_CLICK, KEY_PRESS, CURSOR_MOVE, SCROLL_UP, SCROLL_DOWN, SNEAK, JUMP
    }

}
