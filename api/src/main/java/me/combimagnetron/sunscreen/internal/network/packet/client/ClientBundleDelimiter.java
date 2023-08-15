package me.combimagnetron.sunscreen.internal.network.packet.client;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ClientPacket;

public class ClientBundleDelimiter implements ClientPacket {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();

    public static ClientBundleDelimiter bundleDelimiter() {
        return new ClientBundleDelimiter();
    }

    private ClientBundleDelimiter() {

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
