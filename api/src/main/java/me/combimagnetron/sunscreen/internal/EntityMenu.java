package me.combimagnetron.sunscreen.internal;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.event.UserClickElementEvent;
import me.combimagnetron.sunscreen.event.UserHoverElementEvent;
import me.combimagnetron.sunscreen.internal.entity.impl.display.Display;
import me.combimagnetron.sunscreen.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;
import me.combimagnetron.sunscreen.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.sunscreen.internal.network.sniffer.Sniffer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public final class EntityMenu {
    private final MenuId menuId = MenuId.menuId();
    private final Map<RelativePosition, Display> displays = new LinkedHashMap<>();
    private final Map<Display, Interactable> interactables = new LinkedHashMap<>();
    private final Input input;
    private final Position position;

    private EntityMenu(SunscreenLibrary<?> library, Position position) {
        this.input = new Input(library, (event) -> {});
        this.position = position;
    }

    public void element(Element element) {
        final TextDisplay display = TextDisplay.textDisplay();
        display.text(element.render().renderAsync());
        display.options().defaultBackgroundColor(false);
        displays.put(RelativePosition.relative(0, 0, 0), display);
        if (!(element instanceof Interactable interactable)) {
            return;
        }
        interactables.put(display, interactable);
    }

    record RelativePosition(float x, float y, float z) {

        public static RelativePosition relative(float x, float y, float z) {
            return new RelativePosition(x, y, z);
        }

    }

    private void open() {

    }

    public interface RenderOrder {

        float offset();

    }

    final class Input {
        private final Sniffer.Node<ServerSetPlayerRotation> node;
        private Consumer<ServerSetPlayerRotation> consumer;

        Input(SunscreenLibrary<?> library, Consumer<ServerSetPlayerRotation> consumer) {
            this.node = library.network().sniffer().node("input-" + menuId);
            this.consumer = consumer;
            node.subscribe(PacketContainer.Type.Server.SET_PLAYER_ROTATION).receive(consumer);
        }

        public void consumer(Consumer<ServerSetPlayerRotation> consumer) {
            this.consumer = consumer;
        }

    }

    record MenuId(String code, UUID uuid) {

        public static MenuId menuId() {
            final byte[] bytes = new byte[7];
            ThreadLocalRandom.current().nextBytes(bytes);
            return new MenuId(new String(bytes), UUID.randomUUID());
        }


    }


}
