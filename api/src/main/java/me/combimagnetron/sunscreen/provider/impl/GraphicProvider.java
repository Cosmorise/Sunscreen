package me.combimagnetron.sunscreen.provider.impl;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.graphic.html.HtmlDocument;
import me.combimagnetron.sunscreen.provider.Provider;
import me.combimagnetron.sunscreen.util.Pos2D;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public interface GraphicProvider extends Provider<Canvas> {

    class HtmlGraphicProvider implements GraphicProvider {
        private final HtmlDocument document;
        private final Pos2D size;

        private HtmlGraphicProvider(HtmlDocument document, Pos2D size) {
            this.document = document;
            this.size = size;
        }

        public static HtmlGraphicProvider of(HtmlDocument document, Pos2D size) {
            return new HtmlGraphicProvider(document, size);
        }

        private BufferedImage image() throws IOException {
            BufferedImage image = new BufferedImage(2, (int) size.x(), (int) size.y());
            String htmlString = document.content();
            JEditorPane editorPane = new JEditorPane();
            editorPane.setContentType("text/html");
            editorPane.setText(htmlString);
            SwingUtilities.paintComponent(image.createGraphics(), editorPane, new JPanel(), 0, 0, image.getWidth(), image.getHeight());
            return image;
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
