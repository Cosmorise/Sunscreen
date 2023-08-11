package me.combimagnetron.sunscreen.user;

import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.session.Session;

public interface User<T> {

    T player();

    Session show(Screen screen);

}
