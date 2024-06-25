package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;

public record Quaternion(float x, float y, float z, float w) implements MetadataType {

    public static Quaternion of(float x, float y, float z, float w) {
        return new Quaternion(x, y, z, w);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, x)
                .write(ByteBuffer.Adapter.FLOAT, y)
                .write(ByteBuffer.Adapter.FLOAT, z)
                .write(ByteBuffer.Adapter.FLOAT, w);
        return buffer.bytes();
    }
}
