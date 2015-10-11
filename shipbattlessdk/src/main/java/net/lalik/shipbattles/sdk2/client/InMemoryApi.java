package net.lalik.shipbattles.sdk2.client;

public class InMemoryApi implements Api {
    @Override
    public Response doRequest(Request request) {
        if (request.getAction().equals("account") && request.getMethod().equals("POST")) {
            return new Response(
                    "{\"id\": \"561439f48d5e0e000c8e7f42\", \"nick\": \"user1444166132\"}",
                    "172bc83648184fe9b296321cd1184900",
                    true
            );
        }

        if (request.getAction().equals("battle") && request.getMethod().equals("POST") && request.getSessionToken().equals("172bc83648184fe9b296321cd1184911")) {
            return new Response(
                    "{\"attacker_id\": \"560affde3492eb00087355f9\", \"id\": \"560affee3492eb00087355fb\", \"state\": 2, \"defender_id\": \"561439f48d5e0e000c8e7f42\"}",
                    "172bc83648184fe9b296321cd1184900",
                    true
            );
        }

        if (request.getAction().equals("battles") && request.getMethod().equals("GET") && request.getSessionToken().equals("172bc83648184fe9b296321cd1184900")) {
            return new Response(
                    "{\"attacker_id\": \"560affde3492eb00087355f9\", \"defender_id\": \"561439f48d5e0e000c8e7f42\", \"opponent_battlefield\": {\"id\": \"56143bec8d5e0e000c8e7f46\", \"ready_for_battle\": false, \"account_id\": \"560affde3492eb00087355f9\", \"battle_id\": \"560affee3492eb00087355fb\", \"shots\": []}, \"my_battlefield\": {\"account_id\": \"561439f48d5e0e000c8e7f42\", \"ships\": [], \"id\": \"56143bec8d5e0e000c8e7f47\", \"ready_for_battle\": false, \"inventory\": {\"is:0\": 1, \"is:1\": 1}, \"battle_id\": \"560affee3492eb00087355fb\", \"shots\": []}, \"id\": \"560affee3492eb00087355fb\", \"state\": 2}",
                    "172bc83648184fe9b296321cd1184900",
                    true
            );
        }

        if (request.getAction().equals("battle") && request.getMethod().equals("POST") && request.getSessionToken().equals("172bc83648184fe9b296321cd1184900")) {
            return new Response(
                    "{\"attacker_id\": \"5618e5c28d5e0e000c8e7f74\", \"id\": \"5618e5d78d5e0e000c8e7f76\", \"state\": 1, \"defender_id\": null}",
                    "172bc83648184fe9b296321cd1184900",
                    true
            );
        }

        if (request.getAction().equals("battle") && request.getMethod().equals("POST") && request.getSessionToken().equals("b8fb6f1d9141fcc4b3ffe4795056194d")) {
            return new Response(
                    "{\"attacker_id\": \"5618e7dc8d5e0e000c8e7f7b\", \"id\": \"5618e7f38d5e0e000c8e7f7d\", \"state\": 2, \"defender_id\": \"5618e7f88d5e0e000c8e7f7e\"}",
                    "172bc83648184fe9b296321cd1184900",
                    true
            );
        }

        if (request.getAction().equals("ship_classes") && request.getMethod().equals("GET")) {
            return new Response(
                    "[{\"size\": 1, \"id\": \"is:0\", \"name\": \"keel\"}, {\"size\": 2, \"id\": \"is:1\", \"name\": \"destroyer\"}]",
                    null,
                    true
            );
        }

        return new Response("", "", false);
    }
}
