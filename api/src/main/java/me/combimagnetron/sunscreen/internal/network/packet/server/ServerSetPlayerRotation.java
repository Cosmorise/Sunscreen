package me.combimagnetron.sunscreen.internal.network.packet.server;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.ServerPacket;

public class ServerSetPlayerRotation implements ServerPacket {
    private final ByteBuffer byteBuffer;
    private final float yaw;
    private final float pitch;
    private final boolean onGround;

    private ServerSetPlayerRotation(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.yaw = read(ByteBuffer.Adapter.FLOAT);
        this.pitch = read(ByteBuffer.Adapter.FLOAT);
        this.onGround = read(ByteBuffer.Adapter.BOOLEAN);
    }

    private ServerSetPlayerRotation(float yaw, float pitch, boolean onGround) {
        this.byteBuffer = ByteBuffer.empty();
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public static ServerSetPlayerRotation from(ByteBuffer byteBuffer) {
        return new ServerSetPlayerRotation(byteBuffer);
    }

    public static ServerSetPlayerRotation of(float yaw, float pitch, boolean onGround) {
        return new ServerSetPlayerRotation(yaw, pitch, onGround);
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    public boolean onGround() {
        return onGround;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.FLOAT, yaw);
        write(ByteBuffer.Adapter.FLOAT, pitch);
        write(ByteBuffer.Adapter.BOOLEAN, onGround);
        return byteBuffer.bytes();
    }
}
