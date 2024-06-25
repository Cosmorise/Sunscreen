package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;

public record VarLong(long val) implements MetadataType {

    public static VarLong of(long val) {
        return new VarLong(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.VAR_LONG, val);
        return buffer.bytes();
    }
}
