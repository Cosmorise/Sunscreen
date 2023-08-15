package me.combimagnetron.sunscreen.internal.network.packet.client;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ClientPacket;

import java.util.Collection;
import java.util.HashSet;

public class ClientSetScreenContent implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final Collection<Item<?>> items;
    private final Item<?> carried;
    private final int stateId;
    private final int windowId;

    public static ClientSetScreenContent of(Collection<Item<?>> items, Item<?> carried, int stateId, int windowId) {
        return new ClientSetScreenContent(items, carried, stateId, windowId);
    }

    public static ClientSetScreenContent from(ByteBuffer byteBuffer) {
        return new ClientSetScreenContent(byteBuffer);
    }

    private ClientSetScreenContent(Collection<Item<?>> items, Item<?> carried, int stateId, int windowId) {
        this.byteBuffer = ByteBuffer.empty();
        this.items = new HashSet<>(items);
        this.carried = carried;
        this.stateId = stateId;
        this.windowId = windowId;
        write();
    }

    private ClientSetScreenContent(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.windowId = read(ByteBuffer.Adapter.UNSIGNED_BYTE);
        this.stateId = read(ByteBuffer.Adapter.VAR_INT);
        final int size = read(ByteBuffer.Adapter.VAR_INT);
        this.items = new HashSet<>(size);
        if (size != 0) {
            for (int i = 0; i < size ; i++) {
                items.add(read(ByteBuffer.Adapter.ITEM));
            }
        }
        this.carried = read(ByteBuffer.Adapter.ITEM);
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.UNSIGNED_BYTE, windowId).
                write(ByteBuffer.Adapter.VAR_INT, stateId).
                write(ByteBuffer.Adapter.VAR_INT, items.size());
        if (!items.isEmpty()) {
            items.forEach(item -> write(ByteBuffer.Adapter.ITEM, item));
        }
        write(ByteBuffer.Adapter.ITEM, carried);
        return byteBuffer.bytes();
    }

    public Collection<Item<?>> items() {
        return items;
    }

    public Item<?> carried() {
        return carried;
    }

    public int stateId() {
        return stateId;
    }

    public int windowId() {
        return windowId;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

}
