package me.combimagnetron.sunscreen.v1_20_1.type.mapping;

public class MenuTypeMapping implements Mapping<Integer, String> {
    @Override
    public String convert(Integer integer) {
        return switch(integer) {
            default -> "minecraft:generic_9x1";
            case 1 -> "minecraft:generic_9x2";
            case 2 -> "minecraft:generic_9x3";
            case 3 -> "minecraft:generic_9x4";
            case 4 -> "minecraft:generic_9x5";
            case 5 -> "minecraft:generic_9x6";
            case 6 -> "minecraft:generic_3x3";
            case 7 -> "minecraft:anvil";
            case 8 -> "minecraft:beacon";
            case 9 -> "minecraft:blast_furnace";
            case 10 -> "minecraft:brewing_stand";
            case 11 -> "minecraft:crafting";
            case 12 -> "minecraft:enchantment";
            case 13 -> "minecraft:furnace";
            case 14 -> "minecraft:grindstone";
            case 15 -> "minecraft:hopper";
            case 16 -> "minecraft:lectern";
            case 17 -> "minecraft:loom";
            case 18 -> "minecraft:merchant";
            case 19 -> "minecraft:shulker_box";
            case 20 -> "minecraft:smithing";
            case 21 -> "minecraft:smoker";
            case 22 -> "minecraft:cartography";
            case 23 -> "minecraft:stonecutter";
        };
    }
}
