package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.entity.ShipClass;
import net.lalik.shipbattles.sdk.repository.BattlefieldRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import java.util.ArrayList;

public class BattlefieldService {
    private BattlefieldRepository battlefieldRepository;
    private BattleService battleService;

    public BattlefieldService(BattlefieldRepository battlefieldRepository) {
        this.battlefieldRepository = battlefieldRepository;
    }

    public void setBattleService(BattleService battleService) {
        this.battleService = battleService;
    }

    public Battlefield prepareBattlefield(Battle battle) {
        ShipsInventory shipsInventory = new ShipsInventory();
        shipsInventory.addShips(new ShipClass(1, "Keel", 1), 1);
        shipsInventory.addShips(new ShipClass(2, "Destroyer", 2), 3);
        Battlefield battlefield = new Battlefield(0, shipsInventory, new ArrayList<Battlefield.Ship>());
        battlefield.setBattleId(battle.getId());
        return battlefieldRepository.save(battlefield);
    }

    public void commitBattlefield(Battlefield battlefield) throws EntityNotFoundException {
        battlefieldRepository.save(battlefield);
        battleService.handleBattlefieldChange(battlefield);
    }
}
