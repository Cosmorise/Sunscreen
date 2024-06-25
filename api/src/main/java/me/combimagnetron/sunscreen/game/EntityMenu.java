package me.combimagnetron.sunscreen.game;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.game.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.game.entity.impl.display.Display;
import me.combimagnetron.sunscreen.game.entity.impl.display.TextDisplay;
import me.combimagnetron.sunscreen.game.network.packet.Packet;
import me.combimagnetron.sunscreen.game.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.sunscreen.game.network.sniffer.Sniffer;
import me.combimagnetron.sunscreen.user.User;

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
    private final User<?> user;
    private final Position position;

    private EntityMenu(User<?> user, Position position) {
        this.input = new Input(SunscreenLibrary.library(), (event) -> {});
        this.user = user;
        this.position = position;
    }

    public void element(Element element) {
        final TextDisplay display = TextDisplay.nonTracked(Vector3d.vec3(position.x(), position.y(), position.z()),user);
        display.text(element.canvas().renderAsync());
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
        private Consumer<Sniffer.PacketEvent<ServerSetPlayerRotation>> consumer;

        Input(SunscreenLibrary<?> library, Consumer<Sniffer.PacketEvent<ServerSetPlayerRotation>> consumer) {
            this.node = library.network().sniffer().node("input-" + menuId);
            this.consumer = consumer;
            node.subscribe(Packet.Type.Server.SET_PLAYER_ROTATION).receive(consumer);
        }

        public void consumer(Consumer<Sniffer.PacketEvent<ServerSetPlayerRotation>> consumer) {
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
