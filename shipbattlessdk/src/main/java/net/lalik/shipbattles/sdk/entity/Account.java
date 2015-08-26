package net.lalik.shipbattles.sdk.entity;

public class Account {
    private int id;
    private String nick;
    private String authToken;
    private String password;

    public Account(String nick, String authToken) {
        this.nick = nick;
        this.authToken = authToken;
    }

    public Account(int id, String nick, String password, String authToken) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.authToken = authToken;
    }

    public int getId() {
        return id;
    }

    public String getNick() {
        return nick;
    }

    public String getAuthToken() {
        return authToken;
    }

    public boolean isPasswordValid(String password) {
        return this.password.equals(password);
    }

    public String getPassword() {
        return password;
    }
}
