package me.combimagnetron.sunscreen.v1_20_1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.game.Item;
import me.combimagnetron.sunscreen.game.network.ByteBuffer;
import me.combimagnetron.sunscreen.game.network.VersionRegistry;
import me.combimagnetron.sunscreen.game.network.packet.ClientPacket;
import me.combimagnetron.sunscreen.game.network.packet.Packet;
import me.combimagnetron.sunscreen.game.network.packet.ServerPacket;
import me.combimagnetron.sunscreen.game.network.packet.client.*;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Values;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

public class ConnectionImpl implements me.combimagnetron.sunscreen.game.network.Connection {
    //private final static VersionRegistry REGISTRY = new VersionRegistryImpl();
    private final Player player;
    private final User<Player> user;
    private final SunscreenLibrary<JavaPlugin> library;
    private ChannelPipeline channelPipeline;

    public static ConnectionImpl of(User<Player> user, SunscreenLibrary<JavaPlugin> library) {
        return new ConnectionImpl(user, library);
    }

    protected ConnectionImpl(User<Player> user, SunscreenLibrary<JavaPlugin> library) {
        this.player = user.player();
        this.library = library;
        this.user = user;
        inject();
    }

    private void inject() {
        ServerGamePacketListenerImpl serverGamePacketListener = ((CraftPlayer) player).getHandle().connection;
        Connection connection = serverGamePacketListener.connection;
        this.channelPipeline = connection.channel.pipeline().addLast(new ChannelInjector(library, user));
    }

    @Override
    public void send(Packet packetContainer) {
        if (packetContainer == null) return;
        if (!(packetContainer instanceof ClientPacket clientPacket)) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.empty();
        Bukkit.getLogger().info(" before " + packetContainer.getClass().getName());
        buffer.write(ByteBuffer.Adapter.VAR_INT, VersionRegistry.client(clientPacket.getClass()));
        buffer.write(packetContainer.write());
        channelPipeline.write(Unpooled.wrappedBuffer(buffer.bytes()));
    }

    protected static class ChannelInjector extends ChannelDuplexHandler {
        private final SunscreenLibrary<JavaPlugin> library;
        private final User<Player> user;

        protected ChannelInjector(SunscreenLibrary<JavaPlugin> library, User<Player> user) {
            this.library = library;
            this.user = user;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, @NotNull Object message) throws Exception {
            ByteBuffer buffer = ByteBuffer.of(ctx.alloc().buffer().array());
            int id = buffer.read(ByteBuffer.Adapter.INT);
            Class<? extends Packet> clazz = VersionRegistry.server(id);
            if (clazz == null) {
                super.channelRead(ctx, message);
                return;
            }
            Constructor<? extends Packet> constructor = clazz.getDeclaredConstructor(ByteBuffer.class);
            constructor.setAccessible(true);
            Packet packetContainer = constructor.newInstance(buffer);
            boolean cancel = library.network().sniffer().call(user, packetContainer);
            if (!cancel) {
                super.channelRead(ctx, message);
            }
        }

    }

    private interface Transformer<T extends Packet> {
        Transformer<ClientOpenScreen> CLIENT_OPEN_SCREEN = Transformer.of(ClientOpenScreen.class, container -> new ClientboundOpenScreenPacket(container.windowId(), TypeTransformer.INTEGER_MENU_TYPE.transform(container.windowType()), TypeTransformer.COMPONENT_COMPONENT.transform(container.title())));
        Transformer<ClientSetScreenContent> CLIENT_SET_SCREEN_CONTENT = Transformer.of(ClientSetScreenContent.class, container -> new ClientboundContainerSetContentPacket(container.windowId(), container.stateId(), NonNullList.of(TypeTransformer.ITEM_ITEM_STACK.transform(Item.empty()), container.items().stream().map(TypeTransformer.ITEM_ITEM_STACK::transform).toList().toArray(new ItemStack[0])), TypeTransformer.ITEM_ITEM_STACK.transform(container.carried())));
        //Transformer<ClientBundleDelimiter> CLIENT_BUNDLE_DELIMITER = Transformer.of(ClientBundleDelimiter.class, container -> new ClientboundBundlePacket(container.containers().stream().map(Transformer::findAndTransform).toList()));
        Transformer<ClientSetScreenSlot> CLIENT_SET_SCREEN_SLOT = Transformer.of(ClientSetScreenSlot.class, container -> new ClientboundContainerSetSlotPacket(container.windowId(), container.stateId(), container.slot(), TypeTransformer.ITEM_ITEM_STACK.transform(container.item())));
        Transformer<ClientSpawnEntity> CLIENT_SPAWN_ENTITY = Transformer.of(ClientSpawnEntity.class, container -> new ClientboundAddEntityPacket(container.entityId().intValue(), container.uuid(), container.position().x(), container.position().y(), container.position().z(), (float) container.rotation().x(), (float) container.rotation().y(), EntityType.byString(container.type().identifier().key().string()).orElseThrow(), container.data().i(), Vec3.ZERO, container.rotation().z()));
        Transformer<ClientEntityMetadata> CLIENT_ENTITY_METADATA = Transformer.of(ClientEntityMetadata.class, container -> {
            try {
                Method method = ClientboundSetEntityDataPacket.class.getDeclaredMethod("unpack", RegistryFriendlyByteBuf.class);
                method.setAccessible(true);
            List<SynchedEntityData.DataValue<?>> list = (List<SynchedEntityData.DataValue<?>>) method.invoke(null, new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(container.metadata().bytes().bytes()), RegistryAccess.EMPTY));
                if (list == null) {
                    System.out.println(" aaaa ");
                    return null;
                }
                return new ClientboundSetEntityDataPacket(container.entityId().intValue(), list);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                System.out.println(" aaaa " + (container.metadata().bytes().bytes() == null));
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

        Values<Transformer> VALUES = Values.of(CLIENT_OPEN_SCREEN, CLIENT_SET_SCREEN_CONTENT /*,CLIENT_BUNDLE_DELIMITER*/, CLIENT_SET_SCREEN_SLOT, CLIENT_SPAWN_ENTITY, CLIENT_ENTITY_METADATA);

        Function<T, net.minecraft.network.protocol.Packet<ClientGamePacketListener>> transformer();

        Class<T> clazz();

        default net.minecraft.network.protocol.Packet<ClientGamePacketListener> transform(T t) {
            return transformer().apply(t);
        }

        static net.minecraft.network.protocol.Packet<ClientGamePacketListener> findAndTransform(Packet container) {
            return VALUES.values().stream().filter(transformer -> transformer.clazz() == container.getClass()).findAny().orElseThrow().transform(container);
        }

        static <T extends Packet> Transformer<T> of(Class<T> clazz, Function<T, net.minecraft.network.protocol.Packet<ClientGamePacketListener>> transformer) {
            return new Impl<>(clazz, transformer);
        }

        record Impl<T extends Packet>(Class<T> clazz, Function<T, net.minecraft.network.protocol.Packet<ClientGamePacketListener>> transformer) implements Transformer<T> {

        }

    }

    private static class Decoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            final byte[] bytes = in.array();
            final ByteBuffer byteBuffer = ByteBuffer.of(bytes);
            final Class<? extends ServerPacket> clazz = VersionRegistry.server(byteBuffer.read(ByteBuffer.Adapter.VAR_INT));
            ServerPacket packet = (ServerPacket) clazz.getDeclaredMethod("from", ByteBuffer.class).invoke(null, byteBuffer);
            out.add(packet);
        }
    }

}
