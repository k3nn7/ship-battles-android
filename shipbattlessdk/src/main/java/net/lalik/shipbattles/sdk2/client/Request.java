package net.lalik.shipbattles.sdk2.client;

public class Request {

    private final String method;
    private final String action;
    private final String sessionToken;

    public Request(String method, String action) {
        this.method = method;
        this.action = action;
        this.sessionToken = null;
    }

    public Request(String method, String action, String sessionToken) {
        this.method = method;
        this.action = action;
        this.sessionToken = sessionToken;
    }

    public String getMethod() {
        return method;
    }

    public String getAction() {
        return action;
    }
}
