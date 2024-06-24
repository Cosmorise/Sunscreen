package me.combimagnetron.sunscreen.internal.network.packet.client;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.UUID;

public class ClientSpawnEntity implements PacketContainer {
    private final ByteBuffer byteBuffer;
    private final int entityId;
    private final UUID uuid;
    private final int mobType;
    private final double x, y, z;
    private final

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        return new byte[0];
    }
}
