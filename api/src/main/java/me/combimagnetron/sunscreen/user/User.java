package me.combimagnetron.sunscreen.user;

import me.combimagnetron.sunscreen.internal.network.Connection;
import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.session.Session;

public interface User<T> {

    Connection connection();

    T player();

    Session show(Screen screen);

}
