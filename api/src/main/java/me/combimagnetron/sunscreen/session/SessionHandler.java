package me.combimagnetron.sunscreen.session;

import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

public class SessionHandler {
    private final Collection<Session> activeSessions = new HashSet<>();
    private final Function<Identifier, Session> sessionProvider;

    public SessionHandler(Function<Identifier, Session> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public Session session(Identifier identifier) {
        return sessionProvider.apply(identifier);
    }

    public Collection<Session> activeSessions() {
        return activeSessions;
    }


}
