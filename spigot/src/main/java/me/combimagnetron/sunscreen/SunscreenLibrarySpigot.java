package me.combimagnetron.sunscreen;

import me.combimagnetron.sunscreen.event.EventBus;
import me.combimagnetron.sunscreen.internal.network.Network;
import me.combimagnetron.sunscreen.internal.network.sniffer.Sniffer;
import me.combimagnetron.sunscreen.provider.impl.ScreenProvider;
import me.combimagnetron.sunscreen.screen.FullScreen;
import me.combimagnetron.sunscreen.screen.Screen;
import org.bukkit.plugin.java.JavaPlugin;

public class SunscreenLibrarySpigot implements SunscreenLibrary<JavaPlugin> {
    private final JavaPlugin plugin;

    public SunscreenLibrarySpigot(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final Sniffer sniffer = new Sniffer.Impl();
    @Override
    public Screen screen(ScreenProvider<?> provider) {
        return provider.provided();
    }

    @Override
    public FullScreen enlarge(Screen screen) {
        return null;
    }

    @Override
    public Network network() {
        return () -> sniffer;
    }

    @Override
    public EventBus eventBus() {
        return null;
    }

    @Override
    public JavaPlugin plugin() {
        return plugin;
    }
}
