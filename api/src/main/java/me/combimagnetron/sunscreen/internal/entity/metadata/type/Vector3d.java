package me.combimagnetron.sunscreen.internal.entity.metadata.type;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;

public record Vector3d(float x, float y, float z) implements MetadataType {

    public static Vector3d vector3d() {
        return new Vector3d(1, 1, 1);
    }

    public static Vector3d empty() {
        return new Vector3d(0, 0, 0);
    }

    public static Vector3d vec3(float x, float y, float z) {
        return new Vector3d(x, y, z);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.FLOAT, x).write(ByteBuffer.Adapter.FLOAT, y).write(ByteBuffer.Adapter.FLOAT, z);
        return buffer.bytes();
    }
}
