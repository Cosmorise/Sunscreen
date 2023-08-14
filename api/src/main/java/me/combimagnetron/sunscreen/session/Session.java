package me.combimagnetron.sunscreen.session;

import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.user.User;

public interface Session {

    Screen screen();

    User<?> user();

    boolean close();

}
