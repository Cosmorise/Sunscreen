package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.comet.data.Identifier;
import me.combimagnetron.comet.feature.menu.Pos2D;
import me.combimagnetron.comet.feature.menu.element.Position;
import me.combimagnetron.comet.feature.menu.element.SimpleBufferedElement;
import me.combimagnetron.comet.feature.menu.style.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextElement extends SimpleBufferedElement {
    private final Text text;

    public static TextElement textElement(Identifier identifier, Position position, Text text) {
        return new TextElement(identifier, position, Pos2D.of(256, 256), text);
    }

    private TextElement(Identifier identifier, Position position, Pos2D size, Text text) {
        super(size, identifier, position);
        this.text = text;
        Graphics2D graphics = image().createGraphics();
        graphics.setFont(text.font().internal());
        graphics.drawString(text.content(), 0, 0);

    }

    public Text text() {
        return text;
    }

    @Override
    protected BufferedImage render(BufferedImage image) {
        return null;
    }
}
