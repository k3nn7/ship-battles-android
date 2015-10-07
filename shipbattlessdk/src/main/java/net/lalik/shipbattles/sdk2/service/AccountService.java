package net.lalik.shipbattles.sdk2.service;

import com.google.gson.Gson;

import net.lalik.shipbattles.sdk2.client.Api;
import net.lalik.shipbattles.sdk2.client.Request;
import net.lalik.shipbattles.sdk2.client.Response;
import net.lalik.shipbattles.sdk2.entity.Account;

public class AccountService {
    private Api api;

    public AccountService(Api api) {
        this.api = api;
    }

    public Account createRandomAccount() {
        Response response = api.doRequest(new Request("POST", "account"));
        Gson gson = new Gson();
        AccountBody accountBody = gson.fromJson(
                response.getBody(),
                AccountBody.class
        );
        return new Account(
                accountBody.getId(),
                accountBody.getNick(),
                response.getSessionToken()
        );
    }

    class AccountBody {
        private String id;
        private String nick;

        public String getId() {
            return id;
        }

        public String getNick() {
            return nick;
        }
    }
}
