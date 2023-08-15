package me.combimagnetron.sunscreen.v1_20_1;

import me.combimagnetron.sunscreen.internal.network.VersionRegistry;
import me.combimagnetron.sunscreen.internal.network.packet.ClientPacket;
import me.combimagnetron.sunscreen.internal.network.packet.ServerPacket;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientBundleDelimiter;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientOpenScreen;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientSetScreenContent;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientSetScreenSlot;
import me.combimagnetron.sunscreen.internal.network.packet.server.ServerClickContainer;
import me.combimagnetron.sunscreen.util.Values;

public class VersionRegistryImpl extends VersionRegistry {
    public static final Entry<ClientBundleDelimiter> BUNDLE_DELIMITER = Entry.of(ClientBundleDelimiter.class, 0x00, Entry.Type.CLIENT);
    public static final Entry<ClientSetScreenContent> SET_SCREEN_CONTENT = Entry.of(ClientSetScreenContent.class, 0x12, Entry.Type.CLIENT);
    public static final Entry<ClientSetScreenSlot> SET_SCREEN_SLOT = Entry.of(ClientSetScreenSlot.class, 0x14, Entry.Type.CLIENT);
    public static final Entry<ClientOpenScreen> CLIENT_OPEN_WINDOW = Entry.of(ClientOpenScreen.class, 0x30, Entry.Type.CLIENT);
    public static final Values<Entry<? extends ClientPacket>> CLIENT = Values.of(BUNDLE_DELIMITER, SET_SCREEN_CONTENT, SET_SCREEN_SLOT, CLIENT_OPEN_WINDOW);
    public static final Entry<ServerClickContainer> CLICK_CONTAINER = Entry.of(ServerClickContainer.class, 0x0B, Entry.Type.SERVER);
    public static final Values<Entry<? extends ServerPacket>> SERVER = Values.of(CLICK_CONTAINER);

    static {
        client(CLIENT.values());
        server(SERVER.values());
    }

}
