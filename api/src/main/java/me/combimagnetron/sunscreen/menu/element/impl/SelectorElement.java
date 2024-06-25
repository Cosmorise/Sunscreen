package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.util.Pos2D;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class SelectorElement implements Element {
    private final LinkedList<ButtonElement> buttons = new LinkedList<>();
    private Pos2D size;

    protected SelectorElement(Pos2D size, Identifier identifier, Position position) {

    }

    protected BufferedImage render() {
        int x = buttons.get(0).size().xi();
        int y = buttons.size() * 10 + (buttons.size() + 1) * 2 + 2;

        return null;
    }

    public static Builder selectorElement(Pos2D pos2D, Identifier identifier, Position position) {
        return new Builder(pos2D, identifier, position);
    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public Canvas canvas() {
        return Canvas.image(render());
    }

    @Override
    public Position position() {
        return null;
    }


    public static class Builder {
        private final LinkedList<ButtonElement> buttons = new LinkedList<>();
        private final Pos2D size;
        private final Identifier identifier;
        private final Position position;

        public Builder(Pos2D size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder button(ButtonElement button) {
            buttons.add(button);
            return this;
        }

        public SelectorElement build() {
            return new SelectorElement(size, identifier, position);
        }
    }

}
