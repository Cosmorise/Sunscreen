package me.combimagnetron.sunscreen.event;

import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.user.User;

public record UserClickElementEvent(User<?> user, Element element) implements Event {

    public static UserClickElementEvent of(User<?> user, Element element) {
        return new UserClickElementEvent(user, element);
    }

}
