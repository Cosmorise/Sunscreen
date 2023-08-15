package me.combimagnetron.sunscreen.internal.network.packet.server;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ServerPacket;

import java.util.Collection;

public class ServerClickContainer implements ServerPacket {
    private final ByteBuffer byteBuffer;
    private final int windowId;
    private final int stateId;
    private final short slot;
    private final byte button;
    private final ClickType clickType;
    private final Collection<ChangedSlot> updated;
    private final Item<?> carried;

    public ServerClickContainer(int windowId, int stateId, short slot, byte button, ClickType clickType, Collection<ChangedSlot> updated, Item<?> carried) {
        this.byteBuffer = ByteBuffer.empty();
        this.windowId = windowId;
        this.stateId = stateId;
        this.slot = slot;
        this.button = button;
        this.clickType = clickType;
        this.updated = updated;
        this.carried = carried;
        write();
    }

    private ServerClickContainer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.windowId = read(ByteBuffer.Adapter.UNSIGNED_BYTE);
        this.stateId = read(ByteBuffer.Adapter.VAR_INT);
        this.slot = read(ByteBuffer.Adapter.SHORT);
        this.button = read(ByteBuffer.Adapter.BYTE);
        this.clickType = byteBuffer.readEnum(ClickType.class);
        this.updated = byteBuffer.readCollection(ChangedSlot::new);
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

    public ClickType clickType() {
        return clickType;
    }

    public Collection<ChangedSlot> updated() {
        return updated;
    }

    public Item<?> carried() {
        return carried;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.UNSIGNED_BYTE, windowId);
        write(ByteBuffer.Adapter.VAR_INT, stateId);
        write(ByteBuffer.Adapter.SHORT, slot);
        write(ByteBuffer.Adapter.BYTE, button);
        write(ByteBuffer.Adapter.VAR_INT, clickType.ordinal());
        byteBuffer.writeCollection(updated);
        write(ByteBuffer.Adapter.ITEM, carried);
        return byteBuffer.bytes();
    }

    public record ChangedSlot(short slot, Item<?> item) implements ByteBuffer.Writeable {
        public ChangedSlot(final ByteBuffer byteBuffer) {
            this(byteBuffer.read(ByteBuffer.Adapter.SHORT), byteBuffer.read(ByteBuffer.Adapter.ITEM));
        }

        @Override
        public void write(ByteBuffer byteBuffer) {
            byteBuffer.write(ByteBuffer.Adapter.SHORT, slot);
            byteBuffer.write(ByteBuffer.Adapter.ITEM, item);
        }
    }

    public enum ClickType {
        PICK_UP,
        FAST_MOVE,
        SWAP,
        DUPE,
        DROP,
        FAST_CRAFT,
        PICK_UP_ALL
    }

}
