package net.lalik.shipbattles.sdk2.service;

import net.lalik.shipbattles.sdk2.client.InMemoryApi;
import net.lalik.shipbattles.sdk2.entity.Account;
import net.lalik.shipbattles.sdk2.entity.MyBattlefield;
import net.lalik.shipbattles.sdk2.entity.ShipClass;
import net.lalik.shipbattles.sdk2.value.Coordinate;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BattlefieldServiceTest {
    private Account account;
    private Account defenderAccount;
    private BattlefieldService battlefieldService;
    private BattleService battleService;

    @Before
    public void setUp() {
        account = new Account(
                "561439f48d5e0e000c8e7f42",
                "user1444166132",
                "172bc83648184fe9b296321cd1184900"
        );
        battleService = new BattleService(new InMemoryApi());
        battlefieldService = new BattlefieldService(new InMemoryApi(), battleService);
    }

    @Test
    public void deployShipSuccessfully() {
        ShipClass[] shipClasses = battleService.getShipClasses().values().toArray(
                new ShipClass[battleService.getShipClasses().values().size()]
        );
        MyBattlefield result = battlefieldService.deployShip(account, shipClasses[0], new Coordinate(3, 4));

        assertEquals(1, result.getInventory().get("is:1").intValue());
        assertEquals(0, result.getInventory().get("is:0").intValue());
    }
}
