package me.combimagnetron.sunscreen.internal.network;

import me.combimagnetron.sunscreen.internal.network.packet.PacketContainer;
import me.combimagnetron.sunscreen.user.User;

public interface Connection {

    void send(PacketContainer packetHolder);

}