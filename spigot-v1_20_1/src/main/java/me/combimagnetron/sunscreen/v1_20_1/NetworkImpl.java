package me.combimagnetron.sunscreen.v1_20_1;

import me.combimagnetron.sunscreen.game.menu.ChestMenu;
import me.combimagnetron.sunscreen.game.menu.WindowIdProvider;
import me.combimagnetron.sunscreen.game.network.Network;
import me.combimagnetron.sunscreen.game.network.packet.Packet;
import me.combimagnetron.sunscreen.game.network.packet.server.ServerClickContainer;
import me.combimagnetron.sunscreen.game.network.sniffer.Sniffer;

public class NetworkImpl implements Network {
    private final Sniffer sniffer = new Sniffer.Impl();
    private final DefaultListener listener = new DefaultListener();

    @Override
    public Sniffer sniffer() {
        return sniffer;
    }

    final class DefaultListener {
        private final Sniffer.Node<ServerClickContainer> node = sniffer().node("default");
        private final Sniffer.Ticket<ServerClickContainer> ticket;

        DefaultListener() {
            this.ticket = node.subscribe(Packet.Type.Server.CLICK_CONTAINER);
            ticket.receive(wrappedPacket -> {
                final int id = wrappedPacket.packet().windowId();
                ChestMenu menu = WindowIdProvider.get(id);
                menu.click(wrappedPacket.packet());
            });
        }


    }

}
