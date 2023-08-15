package me.combimagnetron.sunscreen.event;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.user.User;

public record UserClickScreenEvent(User<?> user, Screen screen, Item.Slot slot) implements Event {

    public static UserClickScreenEvent of(User<?> user, Screen screen, Item.Slot slot) {
        return new UserClickScreenEvent(user, screen, slot);
    }

}
