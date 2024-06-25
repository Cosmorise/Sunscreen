package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.util.Pos2D;

import java.awt.image.BufferedImage;

public abstract class SimpleBufferedElement implements Element {
    private final BufferedImage image;
    private final Pos2D size;
    private final Identifier identifier;
    private final Position position;

    public SimpleBufferedElement(Pos2D size, Identifier identifier, Position position) {
        this.size = size;
        this.image = new BufferedImage(size.xi(), size.yi(), 2);
        this.identifier = identifier;
        this.position = position;
    }

    protected abstract BufferedImage render(BufferedImage image);

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Canvas canvas() {
        return Canvas.image(render(image));
    }

    @Override
    public Position position() {
        return position;
    }

    public Pos2D size() {
        return size;
    }

    protected BufferedImage image() {
        return image;
    }

}
