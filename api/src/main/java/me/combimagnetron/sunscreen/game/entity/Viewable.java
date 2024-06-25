package me.combimagnetron.sunscreen.game.entity;

import me.combimagnetron.sunscreen.user.User;

import java.util.Collection;

public interface Viewable {

    Collection<Viewer> viewers();

    void show();

    void hide();

    boolean visible();

    record Viewer(User<?> user, double distance) {

        public static Viewer viewer(User<?> user, double distance) {
            return new Viewer(user, distance);
        }

    }

}
