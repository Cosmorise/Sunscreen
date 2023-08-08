package me.combimagnetron.sunscreen.internal;

import net.kyori.adventure.text.Component;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTInt;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Item<M> {

    private final M material;
    private int customModelData;
    private Component name;
    private Collection<Component> lore;
    private NBTCompound nbtCompound;

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

    public NBTCompound nbtCompound() {
        return nbtCompound;
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
