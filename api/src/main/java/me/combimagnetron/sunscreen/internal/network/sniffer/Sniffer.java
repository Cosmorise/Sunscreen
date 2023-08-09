package me.combimagnetron.sunscreen.internal.network.sniffer;

import me.combimagnetron.sunscreen.condition.Condition;
import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface Sniffer {

    Node node(String name);

    abstract class Node {
        private final Map<PacketContainer.Type<? extends PacketContainer>, Ticket<? extends PacketContainer>> ticketMap = new HashMap<>();
        private final String name;
        private Condition condition;

        protected Node(String name) {
            this.name = name;
        }

        protected Node(String name, Condition condition) {
            this.name = name;
            this.condition = condition;
        }

        public Condition condition() {
            return condition;
        }

        public <T extends PacketContainer> Ticket<T> subscribe(PacketContainer.Type<T> type) {
            final Ticket<T> ticket = new Ticket<>();
            this.ticketMap.put(type, ticket);
            return ticket;
        }

        public abstract boolean post(PacketContainer container);

    }

    class Ticket<T extends PacketContainer> {
        private Consumer<T> sendConsumer;
        private Consumer<T> receiveConsumer;

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
