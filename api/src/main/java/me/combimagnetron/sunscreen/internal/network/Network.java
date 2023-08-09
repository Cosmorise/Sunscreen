package me.combimagnetron.sunscreen.internal.network;

import me.combimagnetron.sunscreen.internal.network.sniffer.Sniffer;
import me.combimagnetron.sunscreen.user.User;

public interface Network {

    ConnectedUser connectedUser(User user);

    Sniffer sniffer();

}
