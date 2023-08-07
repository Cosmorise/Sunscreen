package me.combimagnetron.sunscreen.internal;

import me.combimagnetron.sunscreen.util.Pos2D;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.LinkedList;

public interface InternalMenu {

    Item<?>[][] contentsByCoordinates();

    Collection<Item<?>> contents();

    Title title();

    Network network();

    void item(Pos2D pos2D, Item<?> item);

    interface Network {

    }

    interface Title {

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
            private final Collection<Component> titles = new LinkedList<>();
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

}
