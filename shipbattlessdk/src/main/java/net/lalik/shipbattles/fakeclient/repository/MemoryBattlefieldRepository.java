package net.lalik.shipbattles.fakeclient.repository;

import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.repository.BattlefieldRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

import java.util.ArrayList;

public class MemoryBattlefieldRepository implements BattlefieldRepository {
    private ArrayList<Battlefield> battlefields;

    public MemoryBattlefieldRepository() {
        battlefields = new ArrayList<>();
    }

    @Override
    public Battlefield save(Battlefield battlefield) {
        int newId = battlefields.size() + 1;
        battlefield.setId(newId);
        battlefields.add(battlefield);
        return battlefield;
    }

    @Override
    public Battlefield findById(int battlefieldId) throws EntityNotFoundException{
        for (Battlefield battlefield : battlefields) {
            if (battlefield.getId() == battlefieldId)
                return battlefield;
        }
        throw new EntityNotFoundException();
    }
}
