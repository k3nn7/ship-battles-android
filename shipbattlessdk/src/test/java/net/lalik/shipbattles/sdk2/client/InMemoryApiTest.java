package net.lalik.shipbattles.sdk2.client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class InMemoryApiTest {
    private Api api;

    @Before
    public void setUp() {
        api = new InMemoryApi();
    }

    @Test
    public void doExampleAnonymousRequest() {
        Request request = new Request(
                "POST",
                "account"
        );
        Response response = api.doRequest(request);

        String expectedBody = "{\"id\": \"561439f48d5e0e000c8e7f42\", \"nick\": \"user1444166132\"}";
        String expectedSessionToken = "172bc83648184fe9b296321cd1184900";

        assertEquals(expectedBody, response.getBody());
        assertEquals(expectedSessionToken, response.getSessionToken());
    }

    @Test
    public void doExampleAuthenticatedRequest() {
        Request request = new Request(
                "POST",
                "battle",
                "172bc83648184fe9b296321cd1184911"
        );
        Response response = api.doRequest(request);

        String expectedBody = "{\"attacker_id\": \"560affde3492eb00087355f9\", \"id\": \"560affee3492eb00087355fb\", \"state\": 2, \"defender_id\": \"561439f48d5e0e000c8e7f42\"}";
        String expectedSessionToken = "172bc83648184fe9b296321cd1184900";

        assertEquals(expectedBody, response.getBody());
        assertEquals(expectedSessionToken, response.getSessionToken());
    }
}
