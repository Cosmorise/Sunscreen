package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.comet.event.impl.menu.UserClickElementEvent;
import me.combimagnetron.comet.event.impl.menu.UserHoverElementEvent;

import java.util.function.Consumer;

public interface Interactable {

    void hover(Consumer<UserHoverElementEvent> consumer);

    void click(Consumer<UserClickElementEvent> consumer);

}
