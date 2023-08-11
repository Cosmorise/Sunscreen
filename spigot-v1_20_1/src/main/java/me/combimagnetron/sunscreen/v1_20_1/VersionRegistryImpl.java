package me.combimagnetron.sunscreen.v1_20_1;

import me.combimagnetron.sunscreen.internal.network.VersionRegistry;
import me.combimagnetron.sunscreen.internal.network.packet.menu.ClientOpenWindow;
import me.combimagnetron.sunscreen.internal.network.packet.menu.ClientSetScreenContent;

public class VersionRegistryImpl extends VersionRegistry {
    public static final Entry<ClientSetScreenContent> SET_SCREEN_CONTENT = Entry.of(ClientSetScreenContent.class, 18);
    public static final Entry<ClientOpenWindow> CLIENT_OPEN_WINDOW = Entry.of(ClientOpenWindow.class, 48);
    public static final Entry<?>[] VALUES = new Entry[]{SET_SCREEN_CONTENT, CLIENT_OPEN_WINDOW};

    static {
        register(VALUES);
    }

}
