package me.combimagnetron.sunscreen.internal.network.packet;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;

public interface PacketContainer {

    ByteBuffer byteBuffer();

    byte[] write();
    default <T> T read(ByteBuffer.Adapter<T> type) {
        return byteBuffer().read(type);
    }
    default <T> PacketContainer write(ByteBuffer.Adapter<T> type, T object) {
        byteBuffer().write(type, object);
        return this;
    }

    static PacketContainer empty() {
        return new Impl();
    }
    class Impl implements PacketContainer {
        private final ByteBuffer byteBuffer = ByteBuffer.empty();

        @Override
        public ByteBuffer byteBuffer() {
            return byteBuffer;
        }

        @Override
        public byte[] write() {
            return byteBuffer.bytes();
        }
    }
}