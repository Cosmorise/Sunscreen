package me.combimagnetron.sunscreen.internal.entity.metadata.type;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;

public record Int(int val) implements MetadataType {

    public static Int of(int val) {
        return new Int(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.INT, val);
        return buffer.bytes();
    }
}
