package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.sunscreen.graphic.Canvas;
import me.combimagnetron.sunscreen.internal.ChestMenu;
import me.combimagnetron.sunscreen.internal.Title;
import me.combimagnetron.sunscreen.menu.annotation.Logic;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.user.User;

import java.awt.image.BufferedImage;

public class Base implements Menu {
    private final User<?> user;
    private final ChestMenu chestMenu;
    private BufferedImage bufferedImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

    Base(User<?> user) {
        this.user = user;
        this.chestMenu = ChestMenu.menu(user);
        chestMenu.title(Title.fixed(Canvas.image(bufferedImage).render()));
    }

    @Override
    public void apply(Draft draft) {
        Draft.Impl impl = (Draft.Impl) draft;
        impl.
    }



    @Override
    public User<?> user() {
        return user;
    }

    static class Test extends Base {

        @Logic
        private void logic(Input<?> input) {
            if (input.type() == Input.Type.CURSOR_MOVE) {

            }


        }


    }

}
