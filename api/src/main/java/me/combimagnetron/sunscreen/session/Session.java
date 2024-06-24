package me.combimagnetron.sunscreen.session;

import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.user.User;

public interface Session {

    Menu menu();

    User<?> user();

    boolean close();

}
