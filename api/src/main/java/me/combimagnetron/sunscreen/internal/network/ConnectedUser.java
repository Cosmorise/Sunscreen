package me.combimagnetron.sunscreen.internal.network;

import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;

public interface ConnectedUser {

    void send(PacketContainer packetHolder);

}