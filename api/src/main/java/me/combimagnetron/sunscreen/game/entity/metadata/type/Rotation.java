package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;

public record Rotation(float x, float y, float z) implements MetadataType {

    public static Rotation of(float x, float y, float z) {
        return new Rotation(x, y, z);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, x)
                .write(ByteBuffer.Adapter.FLOAT, y)
                .write(ByteBuffer.Adapter.FLOAT, z);
        return buffer.bytes();
    }
}
