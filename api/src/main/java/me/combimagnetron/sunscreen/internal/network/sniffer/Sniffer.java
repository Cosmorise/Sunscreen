package me.combimagnetron.sunscreen.internal.network.sniffer;

import me.combimagnetron.sunscreen.condition.Condition;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public interface Sniffer {

    <T extends PacketContainer> Node<T> node(String name);

    void call(PacketContainer container);

    class Impl implements Sniffer {
        private final Map<String, Node<PacketContainer>> nodeMap = new TreeMap<>();

        @Override
        public Node<PacketContainer> node(String name) {
            return nodeMap.computeIfAbsent(name, Node::new);
        }

        @Override
        public void call(PacketContainer container) {
            nodeMap.forEach((string, node) -> node.post(container));
        }
    }

    class Node<T extends PacketContainer> {
        private final Map<PacketContainer.Type<T>, Ticket<T>> ticketMap = new HashMap<>();
        private final String name;
        private Condition<PacketContainer> condition;

        protected Node(String name) {
            this.name = name;
        }

        protected Node(String name, Condition<PacketContainer> condition) {
            this.name = name;
            this.condition = condition;
        }

        public Condition<PacketContainer> condition() {
            return condition;
        }

        public Ticket<T> subscribe(PacketContainer.Type<T> type) {
            final Ticket<T> ticket = new Ticket<>();
            this.ticketMap.put(type, ticket);
            return ticket;
        }

        public void post(T container) {
            if (condition == null || !condition.eval(container).result()) {
                return;
            }
            ticketMap.entrySet().stream()
                    .filter(typeTicketEntry -> typeTicketEntry.getKey().clazz().equals(container.getClass()))
                    .forEach(typeTicketEntry -> typeTicketEntry.getValue().receiveConsumer.accept(container));
        }

    }

    class Ticket<T extends PacketContainer> {
        private Consumer<T> sendConsumer = event -> {};
        private Consumer<T> receiveConsumer = event -> {};

        public Ticket<T> receive(Consumer<T> receiveConsumer) {
            this.receiveConsumer = receiveConsumer;
            return this;
        }

        public Ticket<T> send(Consumer<T> sendConsumer) {
            this.receiveConsumer = sendConsumer;
            return this;
        }

    }


}
