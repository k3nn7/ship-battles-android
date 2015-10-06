package net.lalik.shipbattles.sdk2.client;

public class InMemoryApi implements Api {
    @Override
    public Response doRequest(Request request) {
        if (request.getAction().equals("account") && request.getMethod().equals("POST")) {
            return new Response(
                    "{\"id\": \"561439f48d5e0e000c8e7f42\", \"nick\": \"user1444166132\"}",
                    "172bc83648184fe9b296321cd1184900"
            );
        }

        if (request.getAction().equals("battle") && request.getMethod().equals("POST")) {
            return new Response(
                    "{\"attacker_id\": \"560affde3492eb00087355f9\", \"id\": \"560affee3492eb00087355fb\", \"state\": 2, \"defender_id\": \"561439f48d5e0e000c8e7f42\"}",
                    "172bc83648184fe9b296321cd1184900"
            );
        }
        return null;
    }
}
