package me.combimagnetron.sunscreen.user;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class UserManager implements Listener {
    private final Map<UUID, User<Player>> userMap = new WeakHashMap<>();
    private final SunscreenLibrary<JavaPlugin> library;

    public UserManager(SunscreenLibrary<JavaPlugin> library) {
        this.library = library;
        Bukkit.getServer().getPluginManager().registerEvents(this, library.plugin());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        userMap.put(player.getUniqueId(), UserImpl.of(library, player));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        userMap.remove(player.getUniqueId());
    }

    public User<Player> user(Player player) {
        return userMap.get(player.getUniqueId());
    }

}
