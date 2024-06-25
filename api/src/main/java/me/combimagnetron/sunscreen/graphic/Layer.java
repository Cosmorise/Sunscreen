package me.combimagnetron.sunscreen.graphic;

import me.combimagnetron.sunscreen.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public record Layer(int weight, Identifier name, BufferedImage bufferedImage) implements Comparable<Layer> {

    public static Layer of(int weight, Identifier name, BufferedImage bufferedImage) {
        return new Layer(weight, name, bufferedImage);
    }

    public static Layer background(BufferedImage bufferedImage) {
        return new Layer(Integer.MIN_VALUE, Identifier.of("background"), bufferedImage);
    }

    @Override
    public int compareTo(@NotNull Layer o) {
        return Integer.compare(weight, o.weight());
    }
}
