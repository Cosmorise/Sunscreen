package me.combimagnetron.sunscreen.internal;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jglrxavpok.hephaistos.nbt.*;
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Item<M> {
    private final static Item<String> EMPTY = Item.item("air");
    private List<Component> lore = new ArrayList<>();
    private MutableNBTCompound nbtCompound;
    private MutableNBTCompound tag;
    private int amount;
    private final M material;
    private int customModelData;
    private Component name;

    private Item(M material, int amount) {
        this.material = material;
        this.amount = amount;
        this.nbtCompound = NBTCompound.EMPTY.toMutableCompound();
        this.nbtCompound.set("id", NBT.String((String) material));
        this.nbtCompound.set("count", NBT.Byte(amount));
        this.tag = nbtCompound.set("tag", NBTCompound.EMPTY);
        this.tag.set("display", NBTCompound.EMPTY);
    }

    public static <T> Item<T> item(T material) {
        return new Item<>(material, 1);
    }

    public static <T> Item<T> item(T material, int amount) {
        return new Item<>(material, amount);
    }

    public static Item<?> empty() {
        return EMPTY;
    }

    public Item<M> customModelData(int customModelData) {
        this.customModelData = customModelData;
        this.nbtCompound.set("CustomModelData", NBT.Int(this.customModelData));
        return this;
    }

    public Item<M> name(Component name) {
        this.name = name;
        this.nbtCompound.getCompound("display")
                .modify(builder -> builder.set("Name", NBT.String(GsonComponentSerializer.gson().serialize(this.name))));
        return this;
    }

    public Item<M> lore(Component... lore) {
        this.lore.addAll(List.of(lore));
        this.nbtCompound.getCompound("display")
                .modify(builder -> {
                    builder.set("Lore", NBT.List(
                            NBTType.TAG_String, this.lore.stream().map(component -> NBT.String(GsonComponentSerializer.gson().serialize(component))).toList()
                    ));
                });
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

    public int amount() {
        return this.amount;
    }

    public Collection<Component> lore() {
        return this.lore;
    }

    public NBTCompound nbt() {
        return nbtCompound.toCompound();
    }

    public record Slot(Item<?> item, int slot) {

    }

    public interface NbtAdapter<T> {
        NbtAdapter<Integer> INT = Impl.of(nbt -> (int) nbt.getValue(), NBT::Int);
        NbtAdapter<String> STRING = Impl.of(nbt -> (String) nbt.getValue(), NBT::String);

        NBT nbt(T object);

        T object(NBT nbt);

        class Impl<T> implements NbtAdapter<T> {
            private final Function<NBT, T> objectFunction;
            private final Function<T, NBT> nbtFunction;

            public static <V> NbtAdapter<V> of(Function<NBT, V> objectFunction, Function<V, NBT> nbtFunction) {
                return new Impl<>(objectFunction, nbtFunction);
            }

            private Impl(Function<NBT, T> objectFunction, Function<T, NBT> nbtFunction) {
                this.nbtFunction = nbtFunction;
                this.objectFunction = objectFunction;
            }

            @Override
            public NBT nbt(T object) {
                return nbtFunction.apply(object);
            }

            @Override
            public T object(NBT nbt) {
                return objectFunction.apply(nbt);
            }
        }

    }


}
