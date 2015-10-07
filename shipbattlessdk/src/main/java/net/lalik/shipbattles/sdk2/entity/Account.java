package net.lalik.shipbattles.sdk2.entity;

import java.util.BitSet;

public class Account {
    private final String id;
    private final String nick;
    private final String sessionToken;

    public Account(String id, String nick, String sessionToken) {
        this.id = id;
        this.nick = nick;
        this.sessionToken = sessionToken;
    }

    public String getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
