package me.combimagnetron.sunscreen;

import me.combimagnetron.sunscreen.event.EventBus;
import me.combimagnetron.sunscreen.internal.network.Network;
import me.combimagnetron.sunscreen.screen.FullScreen;
import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.provider.impl.ScreenProvider;

public interface SunscreenLibrary<T> {

    Screen screen(ScreenProvider<?> provider);

    FullScreen enlarge(Screen screen);

    Network network();

    EventBus eventBus();

    T plugin();

}
