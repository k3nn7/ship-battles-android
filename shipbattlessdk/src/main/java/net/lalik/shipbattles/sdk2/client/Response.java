package net.lalik.shipbattles.sdk2.client;

public class Response {
    private String body;
    private String sessionToken;

    public Response(String body, String sessionToken) {
        this.body = body;
        this.sessionToken = sessionToken;
    }

    public String getBody() {
        return body;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
