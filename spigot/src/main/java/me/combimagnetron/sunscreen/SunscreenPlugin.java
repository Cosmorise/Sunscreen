package me.combimagnetron.sunscreen;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.game.ChestMenu;
import me.combimagnetron.sunscreen.game.Title;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SunscreenPlugin extends JavaPlugin implements Listener {
    private SunscreenLibrary<JavaPlugin> library;
    private UserManager userManager;

    @Override
    public void onEnable() {
        this.library = new SunscreenLibrarySpigot(this);
        this.userManager = new UserManager(library);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        final User<Player> user = userManager.user(event.getPlayer());
        final Canvas canvas = Canvas.url("https://i.imgur.com/IBpr6QO.jpg");
        ChestMenu chestMenu = ChestMenu.menu(user);
        chestMenu.title(Title.fixed(canvas.renderAsync()));
        //chestMenu.contents().set(Pos2D.of(3, 3), Item.item(Material.STONE));
    }


}
