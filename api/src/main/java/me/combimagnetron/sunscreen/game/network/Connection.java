package me.combimagnetron.sunscreen.game.network;

import me.combimagnetron.sunscreen.game.network.packet.Packet;

public interface Connection {

    void send(Packet packetHolder);

}