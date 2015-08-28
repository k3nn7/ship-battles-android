package net.lalik.shipbattles.sdk.repository;

import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

public interface BattlefieldRepository {
    Battlefield save(Battlefield battlefield);
    Battlefield findById(int battlefieldId) throws EntityNotFoundException;
}
