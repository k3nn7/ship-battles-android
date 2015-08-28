package net.lalik.shipbattles.sdk.service.exception;

import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.entity.ShipClass;
import net.lalik.shipbattles.sdk.repository.BattlefieldRepository;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

import java.util.ArrayList;

public class BattlefieldService {
    private BattlefieldRepository battlefieldRepository;

    public BattlefieldService(BattlefieldRepository battlefieldRepository) {
        this.battlefieldRepository = battlefieldRepository;
    }

    public Battlefield prepareBattlefield() {
        ShipsInventory shipsInventory = new ShipsInventory();
        shipsInventory.addShips(new ShipClass(1, "Keel", 1), 4);
        shipsInventory.addShips(new ShipClass(2, "Destroyer", 2), 3);
        Battlefield battlefield = new Battlefield(0, shipsInventory, new ArrayList<Battlefield.Ship>());
        return battlefieldRepository.save(battlefield);
    }
}
