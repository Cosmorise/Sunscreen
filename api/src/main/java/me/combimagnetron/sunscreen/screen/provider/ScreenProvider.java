package me.combimagnetron.sunscreen.screen.provider;

import me.combimagnetron.sunscreen.screen.Screen;

public interface ScreenProvider {

    HtmlScreenBuilder html();


    interface ScreenBuilder<T> {

        Screen build();

        T type(Screen.Type type);



    }

    class HtmlScreenBuilder implements ScreenBuilder<HtmlScreenBuilder> {

        @Override
        public Screen build() {
            return null;
        }

        @Override
        public HtmlScreenBuilder type(Screen.Type type) {
            return null;
        }
        
    }

}
