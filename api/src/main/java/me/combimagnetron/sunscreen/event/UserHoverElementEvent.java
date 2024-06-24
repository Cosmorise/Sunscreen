package me.combimagnetron.sunscreen.event;

import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.user.User;

public record UserHoverElementEvent(User<?> user, Element element) implements Event {

    public static UserHoverElementEvent of(User<?> user, Element element) {
        return new UserHoverElementEvent(user, element);
    }

}
