package me.combimagnetron.sunscreen.graphic;

import java.awt.*;
import java.io.IOException;

public record Text(String content, Font font) {

    public static Text text(String content, Font font) {
        return new Text(content, font);
    }

    public static Text text(String content) {
        return new Text(content, Font.vanilla());
    }

    public record Font(java.awt.Font internal, int size) {
        static final Font VANILLA = new Font(Registry.VANILLA, 7);

        public static Font vanilla() {
            return VANILLA;
        }

        private final static class Registry {
            private static final java.awt.Font VANILLA;

            static {
                try {
                    VANILLA = java.awt.Font.createFont(0, Font.class.getResourceAsStream("vanilla.ttf"));
                } catch (FontFormatException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

}
