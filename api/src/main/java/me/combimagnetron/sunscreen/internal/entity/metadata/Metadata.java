package me.combimagnetron.sunscreen.internal.entity.metadata;

import com.google.common.collect.ConcurrentHashMultiset;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.*;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Boolean;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Byte;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public interface Metadata {
    Template BASE = Template.of(Byte.class, VarInt.class, OptChat.class, Boolean.class, Boolean.class, Boolean.class, Pose.class, VarInt.class);

    Factory FACTORY = new Factory();

    Holder holder();

    static Metadata inheritAndMerge(Metadata metadata, MetadataType... types) {
        return FACTORY.inheritAndMerge(metadata, types);
    }

    static Metadata merge(Metadata... metadata) {
        return FACTORY.merge(metadata);
    }

    static Metadata of(MetadataType... types) {
        return FACTORY.of(types);
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
                    throw new IllegalArgumentException();
                };
                return FACTORY.of(types);
            }
        }

    }


    final class Holder {
        private final ConcurrentHashMultiset<MetadataType> metadataTypes = ConcurrentHashMultiset.create();

        public void put(MetadataType type) {
            metadataTypes.add(type);
        }

    }

    final class Impl implements Metadata {
        private final Holder holder = new Holder();

        @Override
        public Holder holder() {
            return holder;
        }
    }

    final class Factory {

        public Metadata merge(Metadata... metadata) {
            final Impl impl = new Impl();
            Arrays.stream(metadata).forEachOrdered(m -> {
                Impl impl1 = (Impl) m;
                impl.holder().metadataTypes.addAll(impl1.holder().metadataTypes);
            });
            return impl;
        }

        public Metadata inheritAndMerge(Metadata metadata, MetadataType... metadataTypes) {
            metadata.holder().metadataTypes.addAll(List.of(metadataTypes));
            return metadata;
        }

        public Metadata of(MetadataType... types) {
            final Impl impl = new Impl();
            impl.holder.metadataTypes.addAll(List.of(types));
            return impl;
        }


    }

}