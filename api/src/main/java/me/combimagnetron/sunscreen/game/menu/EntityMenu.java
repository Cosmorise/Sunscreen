package me.combimagnetron.sunscreen.game.menu;

import me.combimagnetron.comet.CometBase;
import me.combimagnetron.comet.feature.menu.element.Element;
import me.combimagnetron.comet.feature.menu.element.Interactable;
import me.combimagnetron.comet.feature.menu.element.Position;
import me.combimagnetron.comet.internal.entity.impl.display.Display;
import me.combimagnetron.comet.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.comet.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.comet.internal.network.packet.Packet;
import me.combimagnetron.comet.internal.network.packet.client.ClientSpawnEntity;
import me.combimagnetron.comet.internal.network.packet.server.ServerSetPlayerRotation;
import me.combimagnetron.comet.internal.network.sniffer.Sniffer;
import me.combimagnetron.comet.user.User;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public final class EntityMenu {
    @Inject
    CometBase<?> comet;
    private final MenuId menuId = MenuId.menuId();
    private final Map<RelativePosition, Display> displays = new LinkedHashMap<>();
    private final Map<Display, Interactable> interactables = new LinkedHashMap<>();
    private final Display cursor;
    private final Position cursorPos;
    private final Input input;
    private final Position position;

    private EntityMenu(Position position, User<?> user) {
        this.input = new Input(comet, user, (event) -> {});
        this.position = position;
        this.cursor = TextDisplay.textDisplay(Vector3d.vec3(0, 0, 0));
        this.cursorPos = Position.pixel(0, 0);
    }

    public void element(Element element) {
        final TextDisplay display = TextDisplay.textDisplay(Vector3d.vec3(0, 0, 0));
        //display.text(element.render().renderAsync());
        display.options().defaultBackgroundColor(false);
        display.transformation(Display.Transformation.of(null, null, null, null));
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

    private void open(User<?> user) {
        displays.forEach((relativePosition, display) -> user.connection().send(ClientSpawnEntity.spawnEntity(display)));
    }

    public interface RenderOrder {

        float offset();

    }

    final class Input {
        private final Sniffer.Node<ServerSetPlayerRotation> node;
        private Consumer<ServerSetPlayerRotation> consumer;

        Input(CometBase<?> library, User<?> user, Consumer<ServerSetPlayerRotation> consumer) {
            this.node = library.network().sniffer().node("input-" + menuId);
            this.consumer = consumer;
            node.subscribe(Packet.Type.Server.SET_PLAYER_ROTATION).receive(wrappedPacket -> {
                library.logger().info("blaa");
            });
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
