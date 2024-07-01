package me.combimagnetron.sunscreen.desktop;

import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Pos2D;

public final class WindowedChestMenu implements Menu {
    private final Menu menu;
    private final Pos2D position;
    private final Pos2D size;

    public WindowedChestMenu(Menu menu, Pos2D position, Pos2D size) {
        this.menu = menu;
        this.position = position;
        this.size = size;
    }

    public void relay(Input<?> input) {

    }

    public Menu menu() {
        return menu;
    }

    public Pos2D position() {
        return position;
    }

    public Pos2D size() {
        return size;
    }

    @Override
    public String toString() {
        return "WindowedChestMenu[" +
                "menu=" + menu + ", " +
                "position=" + position + ", " +
                "size=" + size + ']';
    }


    @Override
    public void apply(Draft draft) {

    }

    @Override
    public User<?> user() {
        return null;
    }

}
