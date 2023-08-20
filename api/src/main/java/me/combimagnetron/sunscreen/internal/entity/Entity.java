package me.combimagnetron.sunscreen.internal.entity;

import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.internal.entity.metadata.Metadata;
import net.kyori.adventure.text.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public interface Entity {

    Data data();

    interface Data {

    }

    abstract class AbstractEntity implements Entity {
        private final EntityId id = EntityId.next();
        private final UUID uuid = UUID.randomUUID();
        private Component name;


    }

    record EntityId(int intValue) {
        private static final AtomicInteger INTEGER = new AtomicInteger(Integer.MIN_VALUE);

        public static EntityId next() {
            return new EntityId(INTEGER.getAndIncrement());
        }

    }

    interface Type {

        int id();

        Identifier identifier();

        Metadata metadata();

        record Impl(int id, Identifier identifier, Metadata metadata) {

        }

    }

}
