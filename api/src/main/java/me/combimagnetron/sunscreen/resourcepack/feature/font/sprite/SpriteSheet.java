package me.combimagnetron.sunscreen.resourcepack.feature.font.sprite;

import me.combimagnetron.sunscreen.util.Pos2D;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.graphic.Canvas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpriteSheet {
    private final Canvas raster;
    private final Pos2D tileSize;
    private final Map<Identifier, Sprite> sprites = new LinkedHashMap<>();

    public static SpriteSheet of(Canvas raster, BufferedImage dataImage, Pos2D tileSize) {
        return new SpriteSheet(raster, dataImage, tileSize);
    }

    protected SpriteSheet(Canvas raster, BufferedImage dataImage, Pos2D tileSize) {
        this.raster = raster;
        this.tileSize = tileSize;
        if (raster.dimensions().x() % tileSize.x() != 0 || raster.dimensions().y() % tileSize.y() != 0)
            throw new RuntimeException("Raster doesn't tile correctly with given size!");
        int xSize = (int) ((raster.dimensions().x() / tileSize.x()) - 1);
        int ySize = (int) ((raster.dimensions().y() / tileSize.y()) - 1);
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                raster.splice(tileSize, Pos2D.of(x, y));
                TileType type = TileType.byColor(new Color(dataImage.getRGB(x, y)));
                sprites.put(Identifier.of("sprite_sheet", type.name().toLowerCase()), null);
            }
        }
    }



    public Map<Identifier, Sprite> sprites() {
        return sprites;
    }

    public enum TileType {
        TOGGLE_BACKDROP(new Color(255, 255, 255)), TOGGLE_OFF(new Color(199, 220, 208)), TOGGLE_ON(new Color(166, 171, 178)),
        BUTTON_VARIANT_1_START(new Color(249, 194, 43)), BUTTON_VARIANT_1_CENTER(new Color(247, 150, 23)), BUTTON_VARIANT_1_END(new Color(251, 107, 29)),
        BUTTON_VARIANT_2_START(new Color(251, 185, 84)), BUTTON_VARIANT_2_CENTER(new Color(230, 144, 78)), BUTTON_VARIANT_2_END(new Color(205, 104, 61)),
        TAB_VARIANT_1(new Color(234, 173, 237)), TAB_VARIANT_2(new Color(168, 132, 243)), TAB_VARIANT_3(new Color(144, 94, 169)),
        TEXT_BOX_START(new Color(143, 211, 255)), TEXT_BOX_CENTER(new Color(77, 155, 230)), TEXT_BOX_END(new Color(77, 101, 180)),
        MINIMIZE(new Color(178, 186, 144)), ENLARGE(new Color(146, 169, 132)), CLOSE(new Color(84, 126, 100)),
        MINIMIZE_OFF(new Color(143, 248, 226)), ENLARGE_OFF(new Color(48, 225, 185)), CLOSE_OFF(new Color(14, 175, 155)),
        WINDOW_TOP_LEFT(new Color(235, 203, 176)), WINDOW_TOP_CENTER(new Color(252, 167, 144)), WINDOW_TOP_RIGHT(new Color(246, 129, 129)),
        WINDOW_MIDDLE_LEFT(new Color(240, 79, 120)), WINDOW_MIDDLE_CENTER(new Color(195, 36, 84)), WINDOW_MIDDLE_RIGHT(new Color(131, 28, 93)),
        WINDOW_BOTTOM_LEFT(new Color(237, 128, 153)), WINDOW_BOTTOM_CENTER(new Color(207, 101, 127)), WINDOW_BOTTOM_RIGHT(new Color(162, 75, 111)),
        UNKNOWN(new Color(0,0,0));//continue with rest of data file and set color.

        private final Color color;

        TileType(Color color) {
            this.color = color;
        }

        public Color color() {
            return color;
        }

        public static TileType byColor(Color color) {
            return Arrays.stream(TileType.values()).filter(type -> type.color == color).findFirst().orElse(UNKNOWN);
        }

    }

}
