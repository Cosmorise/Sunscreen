package me.combimagnetron.sunscreen.provider.impl;

import me.combimagnetron.sunscreen.provider.Provider;
import me.combimagnetron.sunscreen.screen.Screen;

public interface ScreenProvider<T extends ScreenProvider.ScreenBuilder<?>> extends Provider<Screen> {

    static HtmlScreenProvider html() {
        return new HtmlScreenProvider();
    }

    T builder();

    default Screen provided() {
        return builder().build();
    }

    interface ScreenBuilder<T> {

        T type(Screen.Type type);

        Screen build();

    }

    class HtmlScreenProvider implements ScreenProvider<HtmlScreenProvider.HtmlScreenBuilder> {
        private final HtmlScreenBuilder builder = new HtmlScreenBuilder();

        public HtmlScreenBuilder builder() {
            return builder;
        }

        public static class HtmlScreenBuilder implements ScreenBuilder<HtmlScreenBuilder> {
            @Override
            public HtmlScreenBuilder type(Screen.Type type) {
                return null;
            }

            @Override
            public Screen build() {
                return null;
            }
        }

    }

    class ImageScreenProvider implements ScreenProvider<ImageScreenProvider.ImageScreenBuilder> {
        private final ImageScreenBuilder builder = new ImageScreenBuilder();

        @Override
        public ImageScreenBuilder builder() {
            return builder;
        }

        public static class ImageScreenBuilder implements ScreenBuilder<ImageScreenBuilder> {

            @Override
            public ImageScreenBuilder type(Screen.Type type) {
                return null;
            }

            @Override
            public Screen build() {
                return null;
            }

        }

    }

}
