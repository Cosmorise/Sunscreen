package me.combimagnetron.sunscreen.game.entity.metadata;

import me.combimagnetron.sunscreen.game.entity.metadata.type.*;
import me.combimagnetron.sunscreen.game.entity.metadata.type.Boolean;
import me.combimagnetron.sunscreen.game.entity.metadata.type.Byte;
import me.combimagnetron.sunscreen.game.entity.metadata.type.Float;
import me.combimagnetron.sunscreen.game.entity.metadata.type.String;
import me.combimagnetron.sunscreen.game.network.ByteBuffer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public interface Metadata {
    Template BASE = Template.of(Byte.class, VarInt.class, OptChat.class, Boolean.class, Boolean.class, Boolean.class, Pose.class, VarInt.class);

    Factory FACTORY = new Factory();

    Holder holder();

    ByteBuffer bytes();

    static Metadata inheritAndMerge(Metadata metadata, MetadataType... types) {
        return FACTORY.inheritAndMerge(metadata, Arrays.stream(types).map(MetadataPair::metadataPair).toArray(MetadataPair[]::new));
    }

    static Metadata merge(Metadata... metadata) {
        return FACTORY.merge(metadata);
    }

    static Metadata of(MetadataType... types) {
        return FACTORY.of(Arrays.stream(types).map(MetadataPair::metadataPair).toArray(MetadataPair[]::new));
    }

    interface Template {

        Collection<Class<? extends MetadataType>> typeClasses();

        @SafeVarargs
        static Template of(Class<? extends MetadataType>... classes) {
            return new Impl(classes);
        }

        Metadata apply(MetadataType... types);

        final class Impl implements Template {
            private final Collection<Class<? extends MetadataType>> classes = new HashSet<>();

            @SafeVarargs
            private Impl(Class<? extends MetadataType>... classes) {
                this.classes.addAll(List.of(classes));
            }

            @Override
            public Collection<Class<? extends MetadataType>> typeClasses() {
                return classes;
            }

            @Override
            public Metadata apply(MetadataType... types) {
                if (Arrays.stream(types).filter(type -> type.getClass() != classes.iterator().next()).count() > 0) {
                    //throw new IllegalArgumentException();
                };
                return FACTORY.of(Arrays.stream(types).map(type -> new MetadataPair(0, type)).toArray(MetadataPair[]::new));
            }
        }

    }

    record MetadataPair(int index, MetadataType type) {
        private static Map<Class<? extends MetadataType>, Integer> TYPE_MAP = new HashMap<>();

        static {
            TYPE_MAP.put(Byte.class, 0);
            TYPE_MAP.put(VarInt.class, 1);
            TYPE_MAP.put(VarLong.class, 2);
            TYPE_MAP.put(Float.class, 3);
            TYPE_MAP.put(String.class, 4);
            TYPE_MAP.put(Chat.class, 5);
            TYPE_MAP.put(OptChat.class, 6);
            TYPE_MAP.put(Slot.class, 7);
            TYPE_MAP.put(Boolean.class, 8);
            TYPE_MAP.put(Rotation.class, 9);
            TYPE_MAP.put(Position.class, 10);
            TYPE_MAP.put(OptPosition.class, 11);
            TYPE_MAP.put(Pose.class, 20);
            TYPE_MAP.put(Vector3d.class, 26);
            TYPE_MAP.put(Quaternion.class, 27);
        }

        public static MetadataPair metadataPair(int index, MetadataType type) {
            return new MetadataPair(index, type);
        }

        public static MetadataPair metadataPair(MetadataType type) {
            return new MetadataPair(TYPE_MAP.get(type.getClass()), type);
        }
    }


    final class Holder {
        private final ConcurrentHashMap<Integer, MetadataPair> metadataTypes = new ConcurrentHashMap<>();
        private int i = 0;

        public void put(MetadataPair type) {
            metadataTypes.put(i++, type);
        }

    }

    final class Impl implements Metadata {
        private final Holder holder = new Holder();

        @Override
        public Holder holder() {
            return holder;
        }

        @Override
        public ByteBuffer bytes() {
            final ByteBuffer buffer = ByteBuffer.empty();
            holder().metadataTypes.forEach((index, type) -> {
                System.out.println(index + " " + type.index + " " + type.type.bytes().length);
                buffer.write(ByteBuffer.Adapter.UNSIGNED_BYTE, index);
                buffer.write(ByteBuffer.Adapter.VAR_INT, type.index);
                buffer.write(type.type.bytes());
            });
            return buffer;
        }
    }

    final class Factory {

        public Metadata merge(Metadata... metadata) {
            final Impl impl = new Impl();
            Arrays.stream(metadata).forEachOrdered(m -> {
                Impl impl1 = (Impl) m;
                impl.holder().metadataTypes.putAll(impl1.holder().metadataTypes);
            });
            return impl;
        }

        public Metadata inheritAndMerge(Metadata metadata, MetadataPair... metadataTypes) {
            for (MetadataPair metadataType : metadataTypes) {
                metadata.holder().put(metadataType);
            }
            return metadata;
        }

        public Metadata of(MetadataPair... types) {
            final Impl impl = new Impl();
            for (MetadataPair metadataType : types) {
                impl.holder().put(metadataType);
            }
            return impl;
        }


    }

}