package net.lalik.shipbattles.sdk2.service;

import net.lalik.shipbattles.sdk2.client.InMemoryApi;
import net.lalik.shipbattles.sdk2.entity.Account;

import org.junit.Before;
import org.junit.Test;

public class BattlefieldServiceTest {
    private Account account;
    private Account defenderAccount;
    private BattlefieldService battlefieldService;

    @Before
    public void setUp() {
        account = new Account(
                "561439f48d5e0e000c8e7f42",
                "user1444166132",
                "172bc83648184fe9b296321cd1184900"
        );
        defenderAccount = new Account(
                "5618e7dc8d5e0e000c8e7f7b",
                "user1444472796",
                "b8fb6f1d9141fcc4b3ffe4795056194d"
        );
        battlefieldService = new BattlefieldService(new InMemoryApi());
    }

    @Test
    public void deployShipSuccessfully() {

    }
}
