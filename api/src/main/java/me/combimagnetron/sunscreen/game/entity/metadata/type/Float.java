package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;

public record Float(float val) implements MetadataType {

    public static Float of(float val) {
        return new Float(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, val);
        return buffer.bytes();
    }
}
