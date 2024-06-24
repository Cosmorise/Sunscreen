package me.combimagnetron.sunscreen.internal.entity.impl.display;

import me.combimagnetron.sunscreen.internal.entity.metadata.Metadata;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Boolean;
import net.kyori.adventure.text.Component;

@SuppressWarnings(value = "unused")
public class TextDisplay extends Display {
    private Component text = Component.empty();
    private int lineWidth = Integer.MAX_VALUE;
    private int backgroundColor = 0x40000000;
    private byte textOpacity = -1;
    private Options options = Options.options();

    public Component text() {
        return text;
    }

    public static TextDisplay textDisplay() {
        return new TextDisplay();
    }

    public void text(Component text) {
        this.text = text;
    }

    public int lineWidth() {
        return lineWidth;
    }

    public void lineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int backgroundColor() {
        return backgroundColor;
    }

    public void backgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public byte textOpacity() {
        return textOpacity;
    }

    public void textOpacity(byte textOpacity) {
        this.textOpacity = textOpacity;
    }

    public Options options() {
        return options;
    }

    public void options(Options options) {
        this.options = options;
    }

    public static class Options {
        private static final byte SHADOW_MASK = 0x01;
        private static final byte SEE_THROUGH_MASK = 0x02;
        private static final byte DEFAULT_BACKGROUND_COLOR_MASK = 0x04;
        private static final byte ALIGNMENT_MASK = 0x08;
        private byte base = 0;
        private boolean shadow = false;
        private boolean seeThrough = false;
        private boolean defaultBackgroundColor = false;

        public static Options options() {
            return new Options();
        }

        public Options shadow(final boolean bool) {
            if (bool) {
                base = (byte) (base & SHADOW_MASK);
            }
            this.shadow = bool;
            return this;
        }

        public Options shadow() {
            return shadow(!shadow);
        }

        public Options seeThrough(final boolean bool) {
            if (bool) {
                base = (byte) (base & SEE_THROUGH_MASK);
            }
            this.seeThrough = bool;
            return this;
        }

        public Options seeThrough() {
            return seeThrough(!seeThrough);
        }

        public Options defaultBackgroundColor(final boolean bool) {
            if (bool) {
                base = (byte) (base & DEFAULT_BACKGROUND_COLOR_MASK);
            }
            this.defaultBackgroundColor = bool;
            return this;
        }

        public Options defaultBackgroundColor() {
            return defaultBackgroundColor(!defaultBackgroundColor);
        }

        public byte complete() {
            return base;
        }

    }


    @Override
    public Metadata extend() {
        return Metadata.of(Boolean.of(false));
    }
}
