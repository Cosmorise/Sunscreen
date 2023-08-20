package me.combimagnetron.sunscreen.internal.entity.metadata;

import com.google.common.collect.Collections2;
import com.google.common.collect.ConcurrentHashMultiset;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.MetadataType;

public interface Metadata {

    final class Holder {
        private final ConcurrentHashMultiset<MetadataType> metadataTypes = ConcurrentHashMultiset.create();

        public void put(MetadataType type) {

        }


    }

}