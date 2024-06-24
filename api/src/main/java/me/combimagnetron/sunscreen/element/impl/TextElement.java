package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.graphic.Text;
import me.combimagnetron.sunscreen.util.Pos2D;

import java.awt.*;

public class TextElement extends SimpleBufferedElement {
    private final Text text;

    public static TextElement textElement(Text text) {
        return new TextElement(Pos2D.of(256, 256), text);
    }

    private TextElement(Pos2D size, Text text) {
        super(size);
        this.text = text;
        Graphics2D graphics = image().createGraphics();
        graphics.setFont(text.font().internal());
        graphics.drawString(text.content(), 0, 0);

    }

    public Text text() {
        return text;
    }

}
