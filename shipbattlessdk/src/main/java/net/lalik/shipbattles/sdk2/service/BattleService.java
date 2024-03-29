package net.lalik.shipbattles.sdk2.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import net.lalik.shipbattles.sdk2.client.Api;
import net.lalik.shipbattles.sdk2.client.Request;
import net.lalik.shipbattles.sdk2.client.Response;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.Battlefield;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.value.Coordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BattleService {
    private Api api;
    private HashMap<String, ShipClass> shipClassMap = null;

    public BattleService(Api api) {
        this.api = api;
    }

    public Battle getCurrentBattles(Account account) {
        Response response = api.doRequest(
                new Request("GET", "battles", account.getSessionToken())
        );
        if (!response.isSuccess()) {
            return null;
        }
        Gson gson = new Gson();
        Battle battle = gson.fromJson(
                response.getBody(),
                Battle.class
        );

        if (battle.getMyBattlefield() != null) {
            battle.getMyBattlefield().setAvailableShipClasses(
                    determineAvailableShipClasses(battle.getMyBattlefield())
            );
        }
        return battle;
    }

    public ShipClass[] determineAvailableShipClasses(MyBattlefield battlefield) {
        Map<String, ShipClass> allShipClasses = getShipClasses();
        Stack<ShipClass> availableShipClasses = new Stack<>();
        for (Map.Entry<String, Integer> entry : battlefield.getInventory().entrySet()) {
            String shipClassId = entry.getKey();
            Integer shipsCount = entry.getValue();
            if (shipsCount > 0)
                availableShipClasses.push(allShipClasses.get(shipClassId));
        }
        return availableShipClasses.toArray(new ShipClass[availableShipClasses.size()]);
    }

    public Battle newBattle(Account account) {
        Response response = api.doRequest(
                new Request("POST", "battle", account.getSessionToken())
        );
        Gson gson = new Gson();
        return gson.fromJson(
                response.getBody(),
                Battle.class
        );
    }

    public HashMap<String, ShipClass> getShipClasses() {
        if (shipClassMap != null) {
            return shipClassMap;
        }
        shipClassMap = new HashMap<>();

        Response response = api.doRequest(
                new Request("GET", "ship_classes")
        );
        Gson gson = new Gson();
        ShipClass[] classes = gson.fromJson(
                response.getBody(),
                ShipClass[].class
        );

        for (ShipClass shipClass : classes) {
            shipClassMap.put(shipClass.getId(), shipClass);
        }
        return shipClassMap;
    }

    public void readyForBattle(Account account) {
        Response response = api.doRequest(
                new Request("PUT", "battle/ready", account.getSessionToken())
        );
    }

    public int fire(Account account, Coordinate coordinate) {
        FireRequestBody requestBody = new FireRequestBody(coordinate.getX(), coordinate.getY());
        Gson gson = new Gson();
        String requestJson = gson.toJson(requestBody);
        Response response = api.doRequest(
                new Request("PUT", "battle/shots", requestJson, account.getSessionToken())
        );
        FireResultBody resultBody = gson.fromJson(response.getBody(), FireResultBody.class);
        return resultBody.getFireResult();
    }

    private class FireRequestBody {
        @SerializedName("x")
        private int x;
        @SerializedName("y")
        private int y;

        public FireRequestBody(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private class FireResultBody {
        @SerializedName("fire_result")
        private int fireResult;

        public int getFireResult() {
            return fireResult;
        }
    }
}
