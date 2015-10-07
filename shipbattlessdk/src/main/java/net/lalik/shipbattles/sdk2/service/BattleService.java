package net.lalik.shipbattles.sdk2.service;

import com.google.gson.Gson;

import net.lalik.shipbattles.sdk2.client.Api;
import net.lalik.shipbattles.sdk2.client.Request;
import net.lalik.shipbattles.sdk2.client.Response;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;

public class BattleService {
    private Api api;

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
        return gson.fromJson(
                response.getBody(),
                Battle.class
        );
    }
}
