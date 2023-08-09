package me.combimagnetron.sunscreen.internal.network.packet;

import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.packet.menu.ClientOpenWindow;
import me.combimagnetron.sunscreen.internal.network.packet.menu.ClientSetScreenContent;

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

    interface Type<T extends PacketContainer> {

        interface Client {
            Type<ClientSetScreenContent> SET_SCREEN_CONTENT = Impl.of(ClientSetScreenContent.class);
            Type<ClientOpenWindow> OPEN_WINDOW = Impl.of(ClientOpenWindow.class);


        }

        record Impl<T extends PacketContainer>(Class<? extends PacketContainer> clazz) implements Type<T> {
            static <T extends PacketContainer> Impl<T> of(Class<T> clazz) {
                return new Impl<T>(clazz);
            }
        }


    }

}