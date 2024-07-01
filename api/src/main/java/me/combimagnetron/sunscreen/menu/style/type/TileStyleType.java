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
        if (type == Type.SOLID) {
            applySolid(image);
        } else if (type == Type.TILED && file != null) {
            applyTiledFile(image);
        } else if (type == Type.TILED) {
            applyTiled(image);
        }
        return canvas;
    }

    private void applySolid(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, color.color().get(layout[0]).hashCode());
            }
        }
    }

    public void applyTiled(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int tileWidth = width / layout[0].length();
        int tileHeight = height / layout.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int tileX = x / tileWidth;
                int tileY = y / tileHeight;
                image.setRGB(x, y, color.color().get(layout[tileY].substring(tileX, tileX + 1)).hashCode());
            }
        }
    }

    public void applyTiledFile(BufferedImage image) {
        // TODO
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
