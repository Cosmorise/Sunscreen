package me.combimagnetron.sunscreen.config.reader;

import me.combimagnetron.sunscreen.config.element.Node;
import me.combimagnetron.sunscreen.config.element.Section;

import java.util.TreeMap;

public final class Reader {
    private final TreeMap<String, Section> sections;
    private final TreeMap<String, Node<?>> nodes;

    public Reader(TreeMap<String, Section> sections, TreeMap<String, Node<?>> nodes) {
        this.sections = sections;
        this.nodes = nodes;
    }

    public <T> Node<T> node(String name) {
        return (Node<T>) nodes.get(name);
    }

    public Section section(String name) {
        return sections.get(name);
    }

}
