package me.combimagnetron.sunscreen.internal.network;

import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public abstract class VersionRegistry {
    private static final HashMap<Integer, Class<? extends PacketContainer>> MAP = new HashMap<>();

    public static Class<? extends PacketContainer> get(int id) {
        return MAP.get(id);
    }

    @SafeVarargs
    public static void register(Entry<? extends PacketContainer>... entries) {
        Arrays.stream(entries).forEach(entry -> MAP.put(entry.id(), entry.clazz()));
    }

    public static void register(Collection<Entry<? extends PacketContainer>> collection) {
        collection.forEach(entry -> MAP.put(entry.id(), entry.clazz()));
    }

    public interface Entry<T extends PacketContainer> {

        static <T extends PacketContainer> Entry<T> of(Class<T> clazz, int id, Type type) {
            return new Impl<>(clazz, id, type);
        }

        enum Type {
            CLIENT, SERVER
        }

        Class<T> clazz();

        int id();

        record Impl<T extends PacketContainer>(Class<T> clazz, int id, Type type) implements Entry<T> {

            public static <T extends PacketContainer> Impl<T> of(Class<T> clazz, int id, Type type) {
                return new Impl<>(clazz, id, type);
            }

        }

    }

}
