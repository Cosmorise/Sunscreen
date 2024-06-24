package me.combimagnetron.sunscreen.config;

import com.typesafe.config.*;
import me.combimagnetron.sunscreen.config.annotation.processor.Processor;
import me.combimagnetron.sunscreen.config.element.Node;
import me.combimagnetron.sunscreen.config.element.Section;
import me.combimagnetron.sunscreen.config.reader.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public sealed interface Config permits Config.Impl {

    <T> Config node(Node<T> node);

    Config section(Section section);

    Collection<Node<?>> nodes();

    Collection<Section> sections();

    Reader reader();

    void save(Path path);

    static Config config() {
        return new Impl();
    }

    static Config config(Settings settings) {
        return new Impl(settings);
    }

    static Config file(Path path) {
        return Processor.read(path);
    }

    static <T> T map(Class<T> clazz, Path path) {
        return Processor.load(clazz, path);
    }

    final class Impl implements Config {
        private final TreeMap<String, Section> sections = new TreeMap<>();
        private final TreeMap<String, Node<?>> nodes = new TreeMap<>();
        private Settings settings = Settings.settings();

        void test() {
            // Maps a file to a Config annotated class.

            // Reads config from file
            String foo = Config.file(Path.of("baz")).reader().<String>node("bar").value();

            // Creates and saves new config
            Config.config()
                    .node(Node.required("a", 0))
                    .section(
                            Section.required("foo")
                                    .node(Node.required("bar", Boolean.class))
                                    .node(Node.required("baz", Double.class))
                    )
                    .node(Node.required("b", Integer.class))
                    .node(Node.anonymous(Integer.class))
                    .save(Path.of("baz/bar"));
        }
        /*
        a = 0 // Default value means it'll always override when saved
        foo { // Section
            bar = false // Boolean
            baz = 1.0 // Double
        }
        b = 6 // Integer
        anything = 36 // Anonymous class, can be any name and any value, must conform to the type.
        */

        Impl(Settings settings) {
            this.settings = settings;
        }

        Impl() {

        }


        @Override
        public <T> Config node(Node<T> node) {
            this.nodes.put(node.name(), node);
            return this;
        }

        @Override
        public Config section(Section section) {
            this.sections.put(section.name(), section);
            return this;
        }

        @Override
        public Collection<Node<?>> nodes() {
            return nodes.values();
        }

        @Override
        public Collection<Section> sections() {
            return sections.values();
        }

        @Override
        public Reader reader() {
            return new Reader(sections, nodes);
        }

        @Override
        public void save(Path path) {
            com.typesafe.config.ConfigObject config = ConfigFactory.empty().root();
            for (Node<?> value : nodes.values()) {
                if (check(value)) {
                    continue;
                }
                config.put(value.name(), ConfigValueFactory.fromAnyRef(value.value()));
            }
            for (Section value : sections.values()) {
                for (Object element : value.elements()) {
                    if (!(element instanceof Node<?> node) || check(node)) {
                        continue;
                    }
                    config.put(value.name() + "." + node.name(), ConfigValueFactory.fromAnyRef(node.value()));
                }
            }
            ConfigRenderOptions configRenderOptions = ConfigRenderOptions.defaults().setOriginComments(false).setJson(false).setFormatted(true);
            try {
                Files.write(path, List.of(config.render(configRenderOptions)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static boolean check(Node<?> node) {
            return node instanceof Node.OptionalNode<?> || node instanceof Node.AnonymousNode<?>;
        }
    }

}
