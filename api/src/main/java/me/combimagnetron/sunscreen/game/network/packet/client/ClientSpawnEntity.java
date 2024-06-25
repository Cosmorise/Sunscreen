package me.combimagnetron.sunscreen.game.network.packet.client;

import me.combimagnetron.sunscreen.game.entity.Entity;
import me.combimagnetron.sunscreen.game.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.game.network.ByteBuffer;
import me.combimagnetron.sunscreen.game.network.packet.ClientPacket;

import java.util.UUID;

public class ClientSpawnEntity implements ClientPacket {
    private final ByteBuffer byteBuffer;
    private final Entity.EntityId entityId;
    private final UUID uuid;
    private final Entity.Type type;
    private final Vector3d position;
    private final Vector3d rotation;
    private final Vector3d velocity;
    private final Entity.Data data;


    public static ClientSpawnEntity spawnEntity(Entity entity) {
        return new ClientSpawnEntity(entity);
    }

    private ClientSpawnEntity(Entity entity) {
        this.byteBuffer = ByteBuffer.empty();
        this.entityId = entity.id();
        this.uuid = entity.uuid();
        this.type = entity.type();
        this.position = entity.position();
        this.rotation = entity.rotation();
        this.velocity = entity.velocity();
        this.data = entity.data();
    }

    private ClientSpawnEntity(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.entityId = Entity.EntityId.of(read(ByteBuffer.Adapter.VAR_INT));
        this.uuid = read(ByteBuffer.Adapter.UUID);
        this.type = Entity.Type.find(read(ByteBuffer.Adapter.VAR_INT));
        this.position = Vector3d.vec3(read(ByteBuffer.Adapter.DOUBLE), read(ByteBuffer.Adapter.DOUBLE),  read(ByteBuffer.Adapter.DOUBLE));
        this.rotation = Vector3d.vec3(read(ByteBuffer.Adapter.BYTE) * 360f / 256f, read(ByteBuffer.Adapter.BYTE) * 360f / 256f,  read(ByteBuffer.Adapter.BYTE) * 360f / 256f);
        this.velocity = Vector3d.vec3(read(ByteBuffer.Adapter.SHORT), read(ByteBuffer.Adapter.SHORT),  read(ByteBuffer.Adapter.SHORT));
        this.data = Entity.Data.of(read(ByteBuffer.Adapter.INT));
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    @Override
    public byte[] write() {
        write(ByteBuffer.Adapter.VAR_INT, entityId.intValue());
        write(ByteBuffer.Adapter.UUID, uuid);
        write(ByteBuffer.Adapter.VAR_INT, type.id());

        write(ByteBuffer.Adapter.DOUBLE, position.x())
                .write(ByteBuffer.Adapter.DOUBLE, position.y())
                .write(ByteBuffer.Adapter.DOUBLE, position.z());

        write(ByteBuffer.Adapter.BYTE, (byte) (rotation.x() * 256 / 360))
                .write(ByteBuffer.Adapter.BYTE, (byte) (rotation.y() * 256 / 360))
                .write(ByteBuffer.Adapter.BYTE, (byte) (rotation.z() * 256 / 360));

        write(ByteBuffer.Adapter.VAR_INT, data.i());

        write(ByteBuffer.Adapter.SHORT,(short) velocity.x())
                .write(ByteBuffer.Adapter.SHORT,(short) velocity.y())
                .write(ByteBuffer.Adapter.SHORT,(short) velocity.z());
        return byteBuffer.bytes();
    }

    public Entity.EntityId entityId() {
        return entityId;
    }

    public UUID uuid() {
        return uuid;
    }

    public Entity.Type type() {
        return type;
    }

    public Vector3d position() {
        return position;
    }

    public Vector3d rotation() {
        return rotation;
    }

    public Vector3d velocity() {
        return velocity;
    }

    public Entity.Data data() {
        return data;
    }
}
