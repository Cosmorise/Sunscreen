package me.combimagnetron.sunscreen.v1_20_1;

import me.combimagnetron.sunscreen.internal.ChestMenu;
import me.combimagnetron.sunscreen.internal.network.Network;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;
import me.combimagnetron.sunscreen.internal.network.packet.server.ServerClickContainer;
import me.combimagnetron.sunscreen.internal.network.sniffer.Sniffer;
import me.combimagnetron.sunscreen.provider.impl.WindowIdProvider;

public class NetworkImpl implements Network {
    private final Sniffer sniffer = new Sniffer.Impl();
    private final DefaultListener listener = new DefaultListener();

    @Override
    public Sniffer sniffer() {
        return sniffer;
    }

    final class DefaultListener {
        private final Sniffer.Node<ServerClickContainer> node = sniffer().node("internal");
        private final Sniffer.Ticket<ServerClickContainer> ticket;

        DefaultListener() {
            this.ticket = node.subscribe(PacketContainer.Type.Server.CLICK_CONTAINER);
            ticket.receive(packet -> {
                final int id = packet.windowId();
                ChestMenu menu = WindowIdProvider.get(id);
                menu.click(packet);
            });
        }


    }

}
