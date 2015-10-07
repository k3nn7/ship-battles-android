package net.lalik.shipbattles.sdk2;

import net.lalik.shipbattles.sdk2.client.Api;
import net.lalik.shipbattles.sdk2.client.HttpApi;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.service.AccountService;
import net.lalik.shipbattles.sdk2.service.BattleService;

public class ShipBattles {
    private static ShipBattles instance = null;
    private final AccountService accountService;
    private final BattleService battleService;
    private Account authenticatedAccount = null;

    private ShipBattles() {
        Api api = new HttpApi();
        accountService = new AccountService(api);
        battleService = new BattleService(api);
    }

    static public ShipBattles getInstance() {
        if (instance == null) {
            instance = new ShipBattles();
        }
        return instance;
    }

    public Account createRandomAccount() {
        return authenticatedAccount = accountService.createRandomAccount();
    }

    public Account authenticate(String accessToken) {
        if (null != authenticatedAccount &&
                authenticatedAccount.getSessionToken().equals(accessToken))
            return authenticatedAccount;
        return null;
    }

    public Battle getCurrentBattleForAccount(Account account) {
        return battleService.getCurrentBattles(account);
    }
}
