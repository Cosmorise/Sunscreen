package me.combimagnetron.sunscreen.resourcepack.feature.font.sprite;

import me.combimagnetron.sunscreen.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class SpriteRegistry {
    private static final Map<Identifier, Sprite> SPRITES = new HashMap<>();
    private static final Map<Identifier, SpriteSheet> SPRITE_SHEETS = new HashMap<>();

    public static void sprite(Identifier identifier, Sprite sprite) {
         if (sprite.heightAndAscent().k() == null || sprite.heightAndAscent().v() == null)
             return;
         SPRITES.put(identifier, sprite);
    }

    public static void sheet(Identifier identifier, SpriteSheet spriteSheet) {
        SPRITE_SHEETS.put(identifier, spriteSheet);
        SPRITES.putAll(spriteSheet.sprites());
    }


}
