package me.combimagnetron.sunscreen.internal.network.packet.client;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ClientPacket;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.Collection;
import java.util.List;

public class ClientBundleDelimiter implements ClientPacket {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();
    private final Collection<PacketContainer> containers;

    public static ClientBundleDelimiter bundleDelimiter(PacketContainer... containers) {
        return new ClientBundleDelimiter(List.of(containers));
    }

    public static ClientBundleDelimiter from(ByteBuffer byteBuffer) {
        return new ClientBundleDelimiter(List.of());
    }

    private ClientBundleDelimiter(Collection<PacketContainer> containers) {
        this.containers = containers;
    }

    public Collection<PacketContainer> containers() {
        return this.containers;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        return byteBuffer.bytes();
    }
}
