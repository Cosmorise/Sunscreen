package me.combimagnetron.sunscreen.game.network.sniffer;

import me.combimagnetron.sunscreen.game.network.packet.Packet;
import me.combimagnetron.sunscreen.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Sniffer {

    <T extends Packet> Node<T> node(String name);

    boolean call(User<?> user, Packet container);

    class Impl implements Sniffer {
        private final Map<String, Node<Packet>> nodeMap = new TreeMap<>();

        @Override
        public Node<Packet> node(String name) {
            return nodeMap.computeIfAbsent(name, Node::new);
        }

        @Override
        public boolean call(User<?> user, Packet container) {
            return nodeMap.entrySet().stream().anyMatch((entry) -> entry.getValue().post(user, container));
        }
    }

    class Node<T extends Packet> {
        private final Map<Packet.Type<T>, Ticket<T>> ticketMap = new HashMap<>();
        private final String name;

        protected Node(String name) {
            this.name = name;
        }

        public Ticket<T> subscribe(Packet.Type<T> type) {
            final Ticket<T> ticket = new Ticket<>();
            this.ticketMap.put(type, ticket);
            return ticket;
        }

        public boolean post(User<?> user, T container) {
            return ticketMap.entrySet().stream()
                    .filter(typeTicketEntry -> typeTicketEntry.getKey().clazz().equals(container.getClass()))
                    .anyMatch(typeTicketEntry -> {
                        PacketEvent<T> packetEvent = new PacketEvent<>(user, container);
                        typeTicketEntry.getValue().receiveConsumer.accept(packetEvent);
                        return packetEvent.cancelled();
                    });
        }

    }

    final class PacketEvent<T extends Packet> {
        private final User<?> user;
        private final T packet;
        private boolean cancelled = false;

        public PacketEvent(User<?> user, T packet) {
            this.user = user;
            this.packet = packet;
        }

        public User<?> user() {
            return user;
        }

        public T packet() {
            return packet;
        }

        public void cancel() {
            this.cancelled = !cancelled;
        }

        public void cancel(boolean bool) {
            this.cancelled = bool;
        }

        public boolean cancelled() {
            return cancelled;
        }

    }

    class Ticket<T extends Packet> {
        private BiConsumer<User<?>, T> sendConsumer = (user, event) -> {};
        private Consumer<PacketEvent<T>> receiveConsumer = wrappedPacket -> {};

        public Ticket<T> receive(Consumer<PacketEvent<T>> receiveConsumer) {
            this.receiveConsumer = receiveConsumer;
            return this;
        }

        public Ticket<T> send(BiConsumer<User<?>, T> sendConsumer) {
            this.sendConsumer = sendConsumer;
            return this;
        }

    }


}
