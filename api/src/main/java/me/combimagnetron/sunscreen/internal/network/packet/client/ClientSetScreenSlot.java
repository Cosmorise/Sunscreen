package me.combimagnetron.sunscreen.internal.network.packet.client;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ClientPacket;
import org.checkerframework.checker.units.qual.C;

public class ClientSetScreenSlot implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final int windowId;
    private final int stateId;
    private final short slot;
    private final Item<?> item;

    public static ClientSetScreenSlot of(int windowId, int stateId, short slot, Item<?> item) {
        return new ClientSetScreenSlot(windowId, stateId, slot, item);
    }

    public static ClientSetScreenSlot from(ByteBuffer byteBuffer) {
        return new ClientSetScreenSlot(byteBuffer);
    }

    private ClientSetScreenSlot(int windowId, int stateId, short slot, Item<?> item) {
        this.byteBuffer = ByteBuffer.empty();
        this.windowId = windowId;
        this.stateId = stateId;
        this.slot = slot;
        this.item = item;
        write();
    }

    private ClientSetScreenSlot(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.windowId = read(ByteBuffer.Adapter.UNSIGNED_BYTE);
        this.stateId = read(ByteBuffer.Adapter.VAR_INT);
        this.slot = read(ByteBuffer.Adapter.SHORT);
        this.item = read(ByteBuffer.Adapter.ITEM);
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.UNSIGNED_BYTE, windowId);
        write(ByteBuffer.Adapter.VAR_INT, stateId);
        write(ByteBuffer.Adapter.SHORT, slot);
        write(ByteBuffer.Adapter.ITEM, item);
        return new byte[0];
    }
}
