package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.event.UserClickElementEvent;
import me.combimagnetron.sunscreen.event.UserHoverElementEvent;

import java.util.function.Consumer;

public interface Interactable {

    void hover(Consumer<UserHoverElementEvent> consumer);

    void click(Consumer<UserClickElementEvent> consumer);

}
