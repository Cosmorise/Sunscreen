package me.combimagnetron.sunscreen.internal.network.packet.menu;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class PacketPlayClientOpenWindow implements PacketContainer {
    private final ByteBuffer byteBuffer = ByteBuffer.empty();
    private final int windowId;
    private final int windowType;
    private final Component title;

    public static PacketPlayClientOpenWindow of(int windowId, int windowType, Component title) {
        return new PacketPlayClientOpenWindow(windowId, windowType, title);
    }

    public static PacketPlayClientOpenWindow from(byte[] bytes) {
        return new PacketPlayClientOpenWindow(bytes);
    }

    private PacketPlayClientOpenWindow(int windowId, int windowType, Component title) {
        this.windowId = windowId;
        this.windowType = windowType;
        this.title = title;
        write();
    }

    private PacketPlayClientOpenWindow(byte[] bytes) {
        this.windowId = read(ByteBuffer.Adapter.VAR_INT);
        this.windowType = read(ByteBuffer.Adapter.VAR_INT);
        this.title = GsonComponentSerializer.gson().deserialize(read(ByteBuffer.Adapter.STRING));
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.VAR_INT, windowId)
                .write(ByteBuffer.Adapter.VAR_INT, windowType)
                .write(ByteBuffer.Adapter.STRING, GsonComponentSerializer.gson().serialize(title));
        return byteBuffer.bytes();
    }

    public int windowId() {
        return windowId;
    }

    public int windowType() {
        return windowType;
    }

    public Component title() {
        return title;
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

}
