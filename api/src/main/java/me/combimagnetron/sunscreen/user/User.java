package me.combimagnetron.sunscreen.user;

import me.combimagnetron.sunscreen.internal.network.Connection;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.session.Session;

public interface User<T> {

    Connection connection();

    T player();

    Session show(Menu menu);

}
