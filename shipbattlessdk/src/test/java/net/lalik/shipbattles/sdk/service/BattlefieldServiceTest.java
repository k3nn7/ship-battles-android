package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattleRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattlefieldRepository;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;
import net.lalik.shipbattles.sdk.repository.BattlefieldRepository;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BattlefieldServiceTest {
    private AccountRepository accountRepository;
    private BattleRepository battleRepository;
    private BattlefieldRepository battlefieldRepository;
    private BattleService battleService;
    private BattlefieldService battlefieldService;

    @Before
    public void setUp() {
        accountRepository = new MemoryAccountRepository();
        battleRepository = new MemoryBattleRepository(accountRepository);
        battlefieldRepository = new MemoryBattlefieldRepository();
        battlefieldService = new BattlefieldService(battlefieldRepository);
        battleService = new BattleService(
                battleRepository,
                accountRepository,
                battlefieldRepository,
                battlefieldService
        );
        battlefieldService.setBattleService(battleService);
    }

    @Test
    public void changeBattleStateAfterBattlefieldsDeployed() throws Exception {
        Account attacker = accountRepository.findById(1);
        Battle battle = battleService.attackRandomOpponent(attacker);
        assertEquals(Battle.STATE.DEPLOY, battle.getState());

        deployAllShipsForBattlefieldId(battle.getLeftBattlefieldId());
        assertEquals(Battle.STATE.DEPLOY, battleRepository.findByid(battle.getId()).getState());

        deployAllShipsForBattlefieldId(battle.getRightBattlefieldId());
        assertEquals(Battle.STATE.FIRE_EXCHANGE, battleRepository.findByid(battle.getId()).getState());
    }

    private void deployAllShipsForBattlefieldId(int battlefieldId) throws Exception {
        Battlefield battlefield = battlefieldRepository.findById(battlefieldId);
        for (ShipsInventory.Item item : battlefield.shipsInInventory())
            for (int i = 0; i < item.getCount(); i++)
                battlefield.deployShip(new Coordinate(i + 1, 1), Orientation.VERTICAL, item.getShipClass());
        battlefieldService.commitBattlefield(battlefield);
    }
}
