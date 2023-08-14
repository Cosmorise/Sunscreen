package me.combimagnetron.sunscreen.internal.network.packet.server;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.Collection;

public class ServerClickContainer implements PacketContainer {
    private final ByteBuffer byteBuffer;
    private final int windowId;
    private final int stateId;
    private final short slot;
    private final byte button;
    private final int mode;
    private final Collection<Item<?>> updated;
    private final Item<?> carried;

    private ServerClickContainer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.windowId = read(ByteBuffer.Adapter.UNSIGNED_BYTE);
        this.stateId = read(ByteBuffer.Adapter.VAR_INT);
        this.slot = read(ByteBuffer.Adapter.SHORT);
        this.button = read(ByteBuffer.Adapter.BYTE);
        this.mode = read(ByteBuffer.Adapter.VAR_INT);
        this.updated = null;
        this.carried = read(ByteBuffer.Adapter.ITEM);
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    public int windowId() {
        return windowId;
    }

    public int stateId() {
        return stateId;
    }

    public short slot() {
        return slot;
    }

    public byte button() {
        return button;
    }

    public int mode() {
        return mode;
    }

    public Collection<Item<?>> updated() {
        return updated;
    }

    public Item<?> carried() {
        return carried;
    }

    @Override
    public byte[] write() {
        return new byte[0];
    }
}
