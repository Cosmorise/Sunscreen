package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;

public record Position(int x, int y, int z) implements MetadataType {

    public static Position of(int x, int y, int z) {
        return new Position(x, y, z);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.INT, ((x & 0x3FFFFFF) << 6) | ((z & 0x3FFFFFF) << 12) | (y & 0xFFF));
        return buffer.bytes();
    }
}
