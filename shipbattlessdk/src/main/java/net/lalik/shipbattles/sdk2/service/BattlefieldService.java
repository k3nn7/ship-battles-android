package net.lalik.shipbattles.sdk2.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import net.lalik.shipbattles.sdk2.client.Api;
import net.lalik.shipbattles.sdk2.client.Request;
import net.lalik.shipbattles.sdk2.client.Response;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battlefield;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.value.Coordinate;

public class BattlefieldService {
    private final Api api;
    private BattleService battleService;

    public BattlefieldService(Api api, BattleService battleService) {
        this.api = api;
        this.battleService = battleService;
    }

    public MyBattlefield deployShip(Account account, ShipClass shipClass, Coordinate coordinate) {
        DeployShipItem[] items = new DeployShipItem[1];
        items[0] = new DeployShipItem(
                shipClass.getId(),
                coordinate.getX(),
                coordinate.getY()
        );
        DeployShipRequestBody requestBody = new DeployShipRequestBody(items);
        Gson gson = new Gson();
        String requestJson = gson.toJson(requestBody);

        Response response = api.doRequest(
                new Request("PUT", "battle/my-battlefield", requestJson, account.getSessionToken())
        );
        MyBattlefield battlefield = gson.fromJson(response.getBody(), MyBattlefield.class);
        battlefield.setAvailableShipClasses(battleService.determineAvailableShipClasses(battlefield));
        return battlefield;
    }

    private class DeployShipRequestBody {
        @SerializedName("ships")
        DeployShipItem[] ships;

        public DeployShipRequestBody(DeployShipItem[] ships) {
            this.ships = ships;
        }
    }

    private class DeployShipItem {
        @SerializedName("id")
        private final String shipClassId;
        @SerializedName("x")
        private final int x;
        @SerializedName("y")
        private final int y;

        public DeployShipItem(String shipClassId, int x, int y) {
            this.shipClassId = shipClassId;
            this.x = x;
            this.y = y;
        }
    }
}
