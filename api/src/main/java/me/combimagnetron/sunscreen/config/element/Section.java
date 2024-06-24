package me.combimagnetron.sunscreen.config.element;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface Section extends ConfigElement {

    <T> @NotNull Section node(Node<T> node);

    @NotNull Section section(Section section);

    <T extends ConfigElement> @NotNull T find(String name);

    <T extends ConfigElement> @Nullable T next(@NotNull Class<T> clazz);

    Collection<? super ConfigElement> elements();

    String name();

    static Section required(String name) {
        return new RequiredSection(name);
    }

    final class RequiredSection implements Section {
        private final Map<String, Object> elements = new ConcurrentHashMap();
        private final String name;

        private RequiredSection(String name) {
            this.name = name;
        }

        @Override
        public <T> @NotNull Section node(Node<T> node) {
            elements.put(node.name(), node.value());
            return this;
        }

        @Override
        public @NotNull Section section(Section section) {
            elements.put(section.name(), section);
            return this;
        }

        @Override
        public <T extends ConfigElement> @NotNull T find(String name) {
            return (T) elements.get(name);
        }

        @Override
        public <T extends ConfigElement> @Nullable T next(@NotNull Class<T> clazz) {
            return (T) elements.values().parallelStream().filter(element -> element.getClass() == clazz).findFirst().orElse(null);
        }

        @Override
        public Collection<? super ConfigElement> elements() {
            return elements.values().stream().collect(Collectors.toUnmodifiableSet());
        }

        @Override
        public String name() {
            return name;
        }
    }

}
