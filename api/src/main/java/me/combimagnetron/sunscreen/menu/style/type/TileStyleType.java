package me.combimagnetron.sunscreen.menu.style.type;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.graphic.Layer;
import me.combimagnetron.sunscreen.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TileStyleType implements StyleType<Canvas> {
    private Type type = Type.SOLID;
    private String[] layout = {"A"};
    private Color color = Color.of("default", Map.of("A", "#000000"));
    private String file;

    @Override
    public Canvas apply(Canvas canvas) {
        Layer background = canvas.layer(Identifier.of("background"));
        if (background == null) {
            return canvas;
        }
        BufferedImage image = background.bufferedImage();
        return canvas;
    }

    public enum Type {
        SOLID,
        TILED
    }

    public record Color(String state, Map<String, String> color) {
        public static Color of(String state, Map<String, String> color) {
            return new Color(state, color);
        }

    }

}
