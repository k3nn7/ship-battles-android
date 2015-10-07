package net.lalik.shipbattles.sdk2.client;

public class Response {
    private String body;
    private String sessionToken;
    private boolean success;

    public Response(String body, String sessionToken, boolean success) {
        this.body = body;
        this.sessionToken = sessionToken;
        this.success = success;
    }

    public String getBody() {
        return body;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public boolean isSuccess() {
        return success;
    }
}
