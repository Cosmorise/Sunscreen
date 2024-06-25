package me.combimagnetron.sunscreen.game.network.packet.client;

import me.combimagnetron.sunscreen.game.network.ByteBuffer;
import me.combimagnetron.sunscreen.game.network.packet.ClientPacket;
import me.combimagnetron.sunscreen.game.network.packet.Packet;

import java.util.Collection;
import java.util.List;

public class ClientBundleDelimiter implements ClientPacket {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();
    private final Collection<Packet> containers;

    public static ClientBundleDelimiter bundleDelimiter(Packet... containers) {
        return new ClientBundleDelimiter(List.of(containers));
    }

    public static ClientBundleDelimiter from(ByteBuffer byteBuffer) {
        return new ClientBundleDelimiter(List.of());
    }

    private ClientBundleDelimiter(Collection<Packet> containers) {
        this.containers = containers;
    }

    public Collection<Packet> containers() {
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
