package net.lalik.shipbattles.sdk2;

import net.lalik.shipbattles.sdk2.client.Api;
import net.lalik.shipbattles.sdk2.client.HttpApi;
import net.lalik.shipbattles.sdk2.client.InMemoryApi;
import net.lalik.shipbattles.sdk2.client.TestApi;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.Battle;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.service.AccountService;
import net.lalik.shipbattles.sdk2.service.BattleService;
import net.lalik.shipbattles.sdk2.service.BattlefieldService;
import net.lalik.shipbattles.sdk2.value.Coordinate;
import net.lalik.shipbattles.sdk2.value.Orientation;

import java.util.Map;

public class ShipBattles {
    private static ShipBattles instance = null;
    private final AccountService accountService;
    private final BattleService battleService;
    private final BattlefieldService battlefieldService;
    private Account authenticatedAccount = null;

    private ShipBattles() {
        Api api = new TestApi();
        accountService = new AccountService(api);
        battleService = new BattleService(api);
        battlefieldService = new BattlefieldService(api, battleService);
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

    public Account authenticate(String sessionToken) {
        if (null != authenticatedAccount &&
                authenticatedAccount.getSessionToken().equals(sessionToken))
            return authenticatedAccount;
        return authenticatedAccount = accountService.getForSessionToken(sessionToken);
    }

    public Battle getCurrentBattleForAccount(Account account) {
        return battleService.getCurrentBattles(account);
    }

    public Battle newBattle(Account account) {
        return battleService.newBattle(account);
    }

    public Map<String, ShipClass> getShipClasses() {
        return battleService.getShipClasses();
    }

    public MyBattlefield deployShip(Account account, ShipClass shipClass, Coordinate coordinate, Orientation orientation) {
        return battlefieldService.deployShip(account, shipClass, coordinate, orientation);
    }

    public void readyForBattle(Account account) {
        battleService.readyForBattle(account);
    }

    public int fire(Account account, Coordinate coordinate) {
        return battleService.fire(account, coordinate);
    }
}
