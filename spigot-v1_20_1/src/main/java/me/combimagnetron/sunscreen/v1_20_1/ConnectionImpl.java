package me.combimagnetron.sunscreen.v1_20_1;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import me.combimagnetron.sunscreen.internal.network.VersionRegistry;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;
import me.combimagnetron.sunscreen.user.User;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class ConnectionImpl implements me.combimagnetron.sunscreen.internal.network.Connection {
    private final User<Player> user;
    private final Player player;

    protected ConnectionImpl(User<Player> user) {
        this.user = user;
        this.player = user.player();
    }

    @Override
    public void send(PacketContainer packetHolder) {
        ServerGamePacketListenerImpl serverGamePacketListener = ((CraftPlayer) player).getHandle().connection;
        Connection connection;
        try {
            Field field = serverGamePacketListener.getClass().getDeclaredField("connection");
            field.setAccessible(true);
            connection = (Connection) field.get(serverGamePacketListener);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        connection.channel.pipeline().addLast(new ChannelInjector(player));
    }

    protected static class ChannelInjector extends ChannelDuplexHandler {
        private final Player player;

        protected ChannelInjector(Player player) {
            this.player = player;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
            ByteBuffer buffer = ByteBuffer.of(ctx.alloc().buffer().array());
            int id = buffer.read(ByteBuffer.Adapter.INT);
            Class<? extends PacketContainer> clazz = VersionRegistry.get(id);
            if (clazz == null) {
                super.channelRead(ctx, message);
                return;
            }
            super.channelRead(ctx, message);
        }



    }

}
