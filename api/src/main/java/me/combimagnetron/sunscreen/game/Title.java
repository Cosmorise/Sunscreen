package me.combimagnetron.sunscreen.game;

import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface Title {

        Component next();

        static FixedTitle fixed(Component title) {
            return new FixedTitle(title);
        }

        static AnimatedTitle animated(Collection<Component> titles) {
            return new AnimatedTitle(titles);
        }

        record FixedTitle(Component next) implements Title {

        }

        class AnimatedTitle implements Title {
            private final List<Component> titles = new LinkedList<>();
            private int frame = 0;

            AnimatedTitle(Collection<Component> titles) {
                this.titles.addAll(titles);
            }

            @Override
            public Component next() {
                this.frame = titles.size() > frame + 1 ? 0 : frame + 1;
                return titles.get(this.frame);
            }
        }

    }