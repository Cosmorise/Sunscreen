package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.internal.ChestMenu;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.sunscreen.internal.network.sniffer.Sniffer;
import me.combimagnetron.sunscreen.user.User;

public interface Menu {

    void apply(Draft draft);

    User<?> user();

}
