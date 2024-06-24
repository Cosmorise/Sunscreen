package me.combimagnetron.sunscreen;

import me.combimagnetron.sunscreen.event.EventBus;
import me.combimagnetron.sunscreen.internal.network.Network;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.provider.impl.ScreenProvider;

import java.nio.file.Path;

public interface SunscreenLibrary<T> {
    static <T> SunscreenLibrary<T> library() {
        return (SunscreenLibrary<T>) Holder.INSTANCE;
    }

    Menu menu(ScreenProvider<?> provider);

    Network network();

    Path path();

    EventBus eventBus();

    T plugin();

    final class Holder {
        public static SunscreenLibrary<?> INSTANCE = null;

    }

}
