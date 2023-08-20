package me.combimagnetron.sunscreen.internal.network.packet.client;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ClientPacket;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public class ClientOpenScreen implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final int windowId;
    private final int windowType;
    private final Component title;

    public static ClientOpenScreen of(int windowId, int windowType, Component title) {
        return new ClientOpenScreen(windowId, windowType, title);
    }

    public static ClientOpenScreen from(ByteBuffer byteBuffer) {
        return new ClientOpenScreen(byteBuffer);
    }

    private ClientOpenScreen(int windowId, int windowType, Component title) {
        this.byteBuffer = ByteBuffer.empty();
        this.windowId = windowId;
        this.windowType = windowType;
        this.title = title;
        write();
    }

    private ClientOpenScreen(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.windowId = read(ByteBuffer.Adapter.VAR_INT);
        this.windowType = read(ByteBuffer.Adapter.VAR_INT);
        this.title = GsonComponentSerializer.gson().deserialize(read(ByteBuffer.Adapter.STRING));
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.VAR_INT, windowId)
                .write(ByteBuffer.Adapter.VAR_INT, windowType);
                //.write(ByteBuffer.Adapter.STRING, GsonComponentSerializer.gson().serialize(title));
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
