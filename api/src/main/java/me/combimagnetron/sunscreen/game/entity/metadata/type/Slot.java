package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.Item;
import me.combimagnetron.sunscreen.game.network.ByteBuffer;
import org.checkerframework.checker.nullness.qual.NonNull;

public record Slot(@NonNull Item<?> item) implements MetadataType {

    public static Slot of(Item<?> item) {
        return new Slot(item);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.ITEM, item);
        return buffer.bytes();
    }
}
