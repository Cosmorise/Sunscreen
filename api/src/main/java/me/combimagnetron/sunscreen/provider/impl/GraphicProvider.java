package me.combimagnetron.sunscreen.provider.impl;

import gui.ava.html.image.generator.HtmlImageGenerator;
import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.graphic.html.HtmlDocument;
import me.combimagnetron.sunscreen.provider.Provider;
import me.combimagnetron.sunscreen.util.Pos2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface GraphicProvider extends Provider<Canvas> {

    static HtmlGraphicProvider html(HtmlDocument document, Pos2D size) {
        return new HtmlGraphicProvider(document, size);
    }

    class HtmlGraphicProvider implements GraphicProvider {
        private final HtmlDocument document;
        private final Pos2D size;

        private HtmlGraphicProvider(HtmlDocument document, Pos2D size) {
            this.document = document;
            this.size = size;
        }

        private BufferedImage image() throws IOException {
            HtmlImageGenerator generator = new HtmlImageGenerator();
            generator.loadHtml(document.content());
            generator.setSize(new Dimension((int) size.x(), (int) size.y()));
            return generator.getBufferedImage();
        }

        @Override
        public Canvas provided() {
            try {
                return Canvas.image(image());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
