package me.combimagnetron.sunscreen.internal.network.packet.server;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ServerPacket;

public class ServerCloseContainer implements ServerPacket {
    private final ByteBuffer byteBuffer;
    private final int windowId;

    private ServerCloseContainer(int windowId) {
        this.byteBuffer = ByteBuffer.empty();
        this.windowId = windowId;
        write();
    }

    private ServerCloseContainer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.windowId = read(ByteBuffer.Adapter.VAR_INT);
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.VAR_INT, windowId);
        return byteBuffer.bytes();
    }
}
