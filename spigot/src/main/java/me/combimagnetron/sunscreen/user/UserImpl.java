package me.combimagnetron.sunscreen.user;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.internal.network.Connection;
import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.session.Session;
import me.combimagnetron.sunscreen.v1_20_1.ConnectionImpl;
import org.bukkit.entity.Player;

public class UserImpl implements User<Player> {
    private final SunscreenLibrary library;
    private final Player player;
    private final Connection connection;

    public static UserImpl of(SunscreenLibrary library, Player player) {
        return new UserImpl(library, player);
    }

    private UserImpl(SunscreenLibrary library, Player player) {
        this.library = library;
        this.player = player;
        this.connection = ConnectionImpl.of(this, library);
    }

    @Override
    public Connection connection() {
        return connection;
    }

    @Override
    public Player player() {
        return player;
    }

    @Override
    public Session show(Screen screen) {
        return null;
    }
}
