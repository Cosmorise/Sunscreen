package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.event.UserClickElementEvent;
import me.combimagnetron.sunscreen.event.UserHoverElementEvent;
import me.combimagnetron.sunscreen.util.Pos2D;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.user.User;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class ButtonElement extends SimpleBufferedElement implements Interactable {
    private BufferedImage icon;

    private Consumer<UserHoverElementEvent> hoverElementEventConsumer = (x) -> {};
    private Consumer<UserClickElementEvent> clickElementEventConsumer = (x) -> {};

    public ButtonElement(int width, Position position, Identifier identifier) {
        super(Pos2D.of(width, 10), identifier, position);
    }

    public void icon(BufferedImage icon) {
        this.icon = icon;
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

    @Override
    protected BufferedImage render(BufferedImage image) {
        if (icon != null) {
            image.getGraphics().drawImage(icon, 0, 0, null);
        }

        return null;
    }
}
