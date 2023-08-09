package me.combimagnetron.sunscreen.internal.network.packet.menu;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.Collection;
import java.util.HashSet;

public class ClientSetScreenContent implements PacketContainer {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();
    private final Collection<Item<?>> items = new HashSet<>();
    private final Item<?> carried;
    private final int stateId;
    private final int windowId;

    public ClientSetScreenContent(Collection<Item<?>> items, Item<?> carried, int stateId, int windowId) {
        this.items.addAll(items);
        this.carried = carried;
        this.stateId = stateId;
        this.windowId = windowId;
        write();
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.UNSIGNED_BYTE, windowId).
                write(ByteBuffer.Adapter.VAR_INT, stateId).
                write(ByteBuffer.Adapter.VAR_INT, items.size());
        if (!items.isEmpty()) {
            items.forEach(item -> write(ByteBuffer.Adapter.ITEM, item));
        }
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
