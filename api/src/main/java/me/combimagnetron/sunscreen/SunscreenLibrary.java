package me.combimagnetron.sunscreen;

import me.combimagnetron.sunscreen.screen.FullScreen;
import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.screen.provider.ScreenProvider;

public interface SunscreenLibrary {

    Screen screen(ScreenProvider provider);

    FullScreen enlarge(Screen screen);

}
