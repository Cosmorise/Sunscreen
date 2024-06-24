package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.util.Pos2D;

import java.awt.image.BufferedImage;

public class SimpleBufferedElement implements Element {
    private final BufferedImage image;
    private final Pos2D size;

    public SimpleBufferedElement(Pos2D size) {
        this.size = size;
        this.image = new BufferedImage(size.xi(), size.yi(), 2);
    }

    @Override
    public Canvas render() {
        return Canvas.image(image);
    }

    @Override
    public Pos2D size() {
        return size;
    }

    protected BufferedImage image() {
        return image;
    }

}
