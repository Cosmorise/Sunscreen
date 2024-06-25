package me.combimagnetron.sunscreen.v1_20_1;

public interface Mapping<T, V> {
    Mapping<Integer, String> MENU_TYPE = new MenuTypeMapping();

    V convert(T t);

}
