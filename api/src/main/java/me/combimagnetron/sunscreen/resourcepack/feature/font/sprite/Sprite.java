package me.combimagnetron.sunscreen.resourcepack.feature.font.sprite;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.resourcepack.ResourcePackPath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public interface Sprite {

    ResourcePackPath path();

    Canvas canvas();

    /**
     * @return Pair with the height and ascent, the shift value and null or null.
     */
    Pair<Integer, Integer> heightAndAscent();

    Identifier identifier();

    char character();

    static Builder sprite() {
        return new Builder();
    }

    class Builder {
        private Identifier identifier;
        private Type type = Type.BITMAP_SINGLE;
        private Source source = Source.FILE;
        private Path location;
        private URL url;
        private int height;
        private int ascent;

        public Builder identifier(Identifier identifier) {
            if (!check(this.identifier))
                return this;
            this.identifier = identifier;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder source(Source source) {
            this.source = source;
            return this;
        }

        public Builder location(Path path) {
            if (this.source != Source.FILE)
                return this;
            this.location = path;
            return this;
        }

        public Builder url(String url) {
            if (this.source != Source.URL)
                return this;
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder ascent(int ascent) {
            this.ascent = ascent;
            return this;
        }

        public Sprite build() {
            CharCount charCount = new CharCount();
            if (this.source == Source.URL && this.url == null || this.source == Source.FILE && this.location == null)
                throw new IllegalArgumentException();
            BufferedImage image = switch (source) {
                case URL -> {
                    try {
                        yield ImageIO.read(url);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case FILE -> {
                    try {
                        yield ImageIO.read(location.toFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            return new Sprite() {
                @Override
                public ResourcePackPath path() {
                    return null;
                }

                @Override
                public Canvas canvas() {
                    return new Canvas(image.getWidth() , height, image);
                }

                @Override
                public Pair<Integer, Integer> heightAndAscent() {
                    return Pair.of(height, ascent);
                }

                @Override
                public Identifier identifier() {
                    return identifier;
                }

                @Override
                public char character() {
                    return ' ';
                }

            };
        }

        class CharCount {
            public int chInt = 57344;

            public char next() {
                return (char) chInt++;
            }
        }

        private boolean check(Object object) {
            return object == null;
        }

        enum Type {
            BITMAP_SINGLE, BITMAP_SHEET
        }

        enum Source {
            URL, FILE
        }
    }


}
