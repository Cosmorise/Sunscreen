package me.combimagnetron.sunscreen.internal.network.packet.menu;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

public class PacketPlayClientSetScreenContent implements PacketContainer {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        return new byte[0];
    }
}
