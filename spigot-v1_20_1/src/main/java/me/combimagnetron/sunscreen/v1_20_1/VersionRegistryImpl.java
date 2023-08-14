package me.combimagnetron.sunscreen.v1_20_1;

import me.combimagnetron.sunscreen.internal.network.VersionRegistry;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientOpenScreen;
import me.combimagnetron.sunscreen.internal.network.packet.client.ClientSetScreenContent;
import me.combimagnetron.sunscreen.util.Values;

public class VersionRegistryImpl extends VersionRegistry {
    public static final Entry<ClientSetScreenContent> SET_SCREEN_CONTENT = Entry.of(ClientSetScreenContent.class, 18);
    public static final Entry<ClientOpenScreen> CLIENT_OPEN_WINDOW = Entry.of(ClientOpenScreen.class, 48);
    public static final Values<Entry<?>> VALUES = Values.of(SET_SCREEN_CONTENT, CLIENT_OPEN_WINDOW);

    static {
        register(VALUES.values());
    }

}
