package me.combimagnetron.sunscreen.internal;

import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;

public class Item<M> {

    private final M material;
    private int customModelData;
    private Component name;
    private Collection<Component> lore;

    private Item(M material) {
        this.material = material;
    }

    public static <T> Item<T> item(T material) {
        return new Item<>(material);
    }

    public Item<M> customModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public Item<M> name(Component name) {
        this.name = name;
        return this;
    }

    public Item<M> lore(Component... lore) {
        this.lore = List.of(lore);
        return this;
    }

    public M material() {
        return this.material;
    }

    public int customModelData() {
        return this.customModelData;
    }

    public Component name() {
        return this.name;
    }

    public Collection<Component> lore() {
        return this.lore;
    }


}
