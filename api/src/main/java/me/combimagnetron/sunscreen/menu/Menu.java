package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.user.User;

public interface Menu {

    void apply(Draft draft);

    User<?> user();

}
