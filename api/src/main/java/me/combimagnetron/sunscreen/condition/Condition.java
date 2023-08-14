package me.combimagnetron.sunscreen.condition;

public interface Condition<T> {

    Result eval(T object);

    record Result(boolean result) {

    }

}
