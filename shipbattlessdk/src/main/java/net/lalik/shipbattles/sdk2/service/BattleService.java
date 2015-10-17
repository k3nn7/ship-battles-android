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

        battle.getMyBattlefield().setAvailableShipClasses(
                determineAvailableShipClasses(battle.getMyBattlefield())
        );
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
        HashMap<String, ShipClass> shipClassMap = new HashMap<>();

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
}
