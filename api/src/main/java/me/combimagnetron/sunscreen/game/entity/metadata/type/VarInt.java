package me.combimagnetron.sunscreen.game.entity.metadata.type;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;

public record VarInt(int val) implements MetadataType {

    public static VarInt of(int val) {
        return new VarInt(val);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.VAR_INT, val);
        return buffer.bytes();
    }

}
