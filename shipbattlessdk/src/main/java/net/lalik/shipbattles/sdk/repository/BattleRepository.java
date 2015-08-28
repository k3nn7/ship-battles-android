package net.lalik.shipbattles.sdk.repository;

import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

public interface BattleRepository {
    Battle[] getActiveBattlesForAccountId(int accountId);
    Battle save(Battle battle);
    Battle findByid(int battleId) throws EntityNotFoundException;
}
