package me.combimagnetron.sunscreen.game.menu;

import me.combimagnetron.comet.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WindowIdProvider {
    private static final Map<Integer, ChestMenu> MENU_MAP = new HashMap<>();
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    public static ChestMenu get(int windowId) {
        return MENU_MAP.get(windowId);
    }

    public static Collection<Pair<Integer, ChestMenu>> menus() {
        return MENU_MAP.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).toList();
    }

    public static Integer next(ChestMenu chestMenu) {
        final int id = ATOMIC_INTEGER.decrementAndGet();
        MENU_MAP.put(id, chestMenu);
        return id;
    }
}
