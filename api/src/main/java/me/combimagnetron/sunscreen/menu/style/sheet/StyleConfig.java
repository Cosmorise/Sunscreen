package me.combimagnetron.sunscreen.menu.style.sheet;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.config.Config;
import me.combimagnetron.sunscreen.config.element.Node;
import me.combimagnetron.sunscreen.config.reader.Reader;
import me.combimagnetron.sunscreen.config.element.Section;

import java.nio.file.Path;

public class StyleConfig {
    private final String name;

    StyleConfig(String name) {
        this.name = name;
    }

    public void write() {
        Config.config()
                .section(Section.required("style")
                        .section(Section.required("tile")
                                .node(Node.required("type", String.class))
                                .node(Node.required("layout", String[].class))
                                .section(Section.required("color")
                                        .node(Node.required("default", String.class))
                                        .node(Node.required("hover", String.class))
                                        .node(Node.required("click", String.class))
                                ))
                ).save(SunscreenLibrary.library().path().resolve(Path.of(name + ".style")));

    }
}
