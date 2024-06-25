package me.combimagnetron.sunscreen.game.menu;

import me.combimagnetron.sunscreen.util.Pos2D;
import me.combimagnetron.sunscreen.game.Item;
import me.combimagnetron.sunscreen.game.network.Connection;
import me.combimagnetron.sunscreen.game.network.packet.client.ClientBundleDelimiter;
import me.combimagnetron.sunscreen.game.network.packet.client.ClientOpenScreen;
import me.combimagnetron.sunscreen.game.network.packet.client.ClientSetScreenSlot;
import me.combimagnetron.sunscreen.game.network.packet.server.ServerClickContainer;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Pair;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface ChestMenu {

    Contents contents();

    Title title();

    void title(Title title);

    void item(Pos2D pos2D, Item<?> item);

    void click(ServerClickContainer packet);

    final class Impl implements ChestMenu {
        private final Contents contents = new Contents(c -> this.refresh());
        private final int windowId = WindowIdProvider.next(this);
        private final User<?> viewer;
        private final Connection connection;
        private Title title;

        public Impl(User<?> viewer) {
            this.viewer = viewer;
            this.connection = viewer.connection();
            this.title = Title.fixed(Component.text("Sunscreen Menu #" + (windowId * -1)));
            open();
        }

        private void open() {
            final ClientOpenScreen openScreen = ClientOpenScreen.of(windowId, 5, title.next());
            //final ClientSetScreenContent setScreenContent = ClientSetScreenContent.of(contents.all(), Item.empty(), 0, windowId);
            connection.send(openScreen);
            //connection.send(setScreenContent);
            //refresh();
        }

        private void refresh() {
            connection.send(ClientBundleDelimiter.bundleDelimiter(contents.pairStream().map(pair -> ClientSetScreenSlot.of(windowId, 0, pair.k().shortValue(), pair.v())).toList().toArray(new ClientSetScreenSlot[0])));
        }

        @Override
        public Contents contents() {
            return contents;
        }

        @Override
        public Title title() {
            return title;
        }

        public void title(Title title) {
            this.title = title;
            open();
        }

        @Override
        public void item(Pos2D pos2D, Item<?> item) {

        }

        @Override
        public void click(ServerClickContainer packet) {

        }
    }

    final class Contents {
        private final List<Row> rows = new LinkedList<>();
        private final Consumer<Contents> updateConsumer;

        private Contents(Consumer<Contents> updateConsumer) {
            this.updateConsumer = updateConsumer;
            for (int i = 0; i < 6; i++) {
                rows.add(i, Row.empty());
            }
            for (Row row : rows) {
                for (int i = 0; i < 9; i++) {
                    row.list.add(Item.empty());
                }
            }
        }

        public void set(Pos2D pos, Item<?> item) {
            Row row = rows.get((int) pos.y());
            row.list.set((int) pos.x(), item);
            updateConsumer.accept(this);
        }

        public Item<?> get(Pos2D pos) {
            Row row = rows.get((int) pos.y());
            return row.list.get((int) pos.x());
        }

        public Collection<Item<?>> all() {
            final LinkedHashSet<Item<?>> items = new LinkedHashSet<>();
            rows.forEach(row -> items.addAll(row.list));
            return items;
        }

        public Row row(int index) {
            return rows.get(index);
        }

        public List<Row> rows() {
            return rows;
        }

        public Stream<Pair<Integer, Item<?>>> pairStream() {
            final LinkedList<Pair<Integer, Item<?>>> list = new LinkedList<>();
            AtomicInteger slot = new AtomicInteger();
            rows.forEach(row -> list.addAll((Collection<? extends Pair<Integer, Item<?>>>) row.list.stream().map(item -> Pair.of(slot.getAndIncrement(), item)).toList()));
            return list.stream();
        }

        public Column column(int index) {
            return Column.from(this, index);
        }

        public int sizeVertical() {
            return rows.size();
        }

        public record Row(LinkedList<Item<?>> list) {

            static Row empty() {
                return new Row(new LinkedList<>());
            }

            static Row of(LinkedList<Item<?>> list) {
                return new Row(list);
            }

        }

        public record Column(LinkedList<Item<?>> list) {

            static Column of(LinkedList<Item<?>> list) {
                return new Column(list);
            }

            static Column from(Contents contents, int index) {
                final LinkedList<Item<?>> itemList = new LinkedList<>();
                contents.rows.forEach(row -> itemList.add(row.list.get(index)));
                return new Column(itemList);
            }

        }

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

}
