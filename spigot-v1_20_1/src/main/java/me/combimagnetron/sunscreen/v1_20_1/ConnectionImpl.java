package me.combimagnetron.sunscreen.v1_20_1;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.VersionRegistry;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientOpenScreen;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Values;
import me.combimagnetron.sunscreen.v1_20_1.type.TypeTransformer;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.function.Function;

public class ConnectionImpl implements me.combimagnetron.sunscreen.internal.network.Connection {
    private final Player player;
    private final SunscreenLibrary library;
    private ChannelPipeline channelPipeline;

    protected ConnectionImpl(User<Player> user, SunscreenLibrary library) {
        this.player = user.player();
        this.library = library;
        inject();
    }

    private void inject() {
        ServerGamePacketListenerImpl serverGamePacketListener = ((CraftPlayer) player).getHandle().connection;
        Connection connection;
        try {
            Field field = serverGamePacketListener.getClass().getDeclaredField("connection");
            field.setAccessible(true);
            connection = (Connection) field.get(serverGamePacketListener);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.channelPipeline = connection.channel.pipeline().addLast(new ChannelInjector(library));
    }

    @Override
    public void send(PacketContainer packetContainer) {
        channelPipeline.write(Transformer.findAndTransform(packetContainer));
    }

    protected static class ChannelInjector extends ChannelDuplexHandler {
        private final SunscreenLibrary library;

        protected ChannelInjector(SunscreenLibrary library) {
            this.library = library;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, @NotNull Object message) throws Exception {
            ByteBuffer buffer = ByteBuffer.of(ctx.alloc().buffer().array());
            int id = buffer.read(ByteBuffer.Adapter.INT);
            Class<? extends PacketContainer> clazz = VersionRegistry.get(id);
            if (clazz == null) {
                super.channelRead(ctx, message);
                return;
            }
            PacketContainer packetContainer = (PacketContainer) clazz.getDeclaredMethod("from", ByteBuffer.class).invoke(null, buffer);
            library.network().sniffer().call(packetContainer);
            super.channelRead(ctx, message);
        }

    }

    private interface Transformer<T extends PacketContainer> {
        Transformer<ClientOpenScreen> CLIENT_OPEN_SCREEN = Transformer.of(ClientOpenScreen.class, container -> new ClientboundOpenScreenPacket(container.windowId(), TypeTransformer.INTEGER_MENU_TYPE.transform(container.windowType()), TypeTransformer.COMPONENT_COMPONENT.transform(container.title())));
        Values<Transformer> VALUES = Values.of(CLIENT_OPEN_SCREEN);

        Function<T, Packet<?>> transformer();

        Class<T> clazz();

        default Packet<?> transform(T t) {
            return transformer().apply(t);
        }

        static Packet<?> findAndTransform(PacketContainer container) {
            return VALUES.values().stream().filter(transformer -> transformer.clazz() == container.getClass()).findAny().orElseThrow().transform(container);
        }

        static <T extends PacketContainer> Transformer<T> of(Class<T> clazz, Function<T, Packet<?>> transformer) {
            return new Impl<>(clazz, transformer);
        }

        record Impl<T extends PacketContainer>(Class<T> clazz, Function<T, Packet<?>> transformer) implements Transformer<T> {

        }

    }

}
