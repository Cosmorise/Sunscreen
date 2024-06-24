package me.combimagnetron.sunscreen;

import me.combimagnetron.sunscreen.event.EventBus;
import me.combimagnetron.sunscreen.internal.network.Network;
import me.combimagnetron.sunscreen.internal.network.sniffer.Sniffer;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.provider.impl.ScreenProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class SunscreenLibrarySpigot implements SunscreenLibrary<JavaPlugin> {
    private final JavaPlugin plugin;

    public SunscreenLibrarySpigot(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final Sniffer sniffer = new Sniffer.Impl();

    @Override
    public Menu menu(ScreenProvider<?> provider) {
        return null;
    }

    @Override
    public Network network() {
        return () -> sniffer;
    }

    @Override
    public Path path() {
        return plugin.getDataFolder().toPath();
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
