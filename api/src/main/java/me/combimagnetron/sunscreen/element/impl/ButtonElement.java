package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.event.UserClickElementEvent;
import me.combimagnetron.sunscreen.event.UserHoverElementEvent;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Pos2D;

import java.util.function.Consumer;

public class ButtonElement extends SimpleBufferedElement implements Interactable {

    private Consumer<UserHoverElementEvent> hoverElementEventConsumer = (x) -> {};
    private Consumer<UserClickElementEvent> clickElementEventConsumer = (x) -> {};

    public ButtonElement(Pos2D size) {
        super(size);
    }

    public void click(User<?> user) {
        this.clickElementEventConsumer.accept(UserClickElementEvent.of(user, this));
    }

    public void hover(User<?> user) {
        this.hoverElementEventConsumer.accept(UserHoverElementEvent.of(user, this));
    }

    @Override
    public void hover(Consumer<UserHoverElementEvent> consumer) {
        this.hoverElementEventConsumer = consumer;
    }

    @Override
    public void click(Consumer<UserClickElementEvent> consumer) {
        this.clickElementEventConsumer = consumer;
    }
}
