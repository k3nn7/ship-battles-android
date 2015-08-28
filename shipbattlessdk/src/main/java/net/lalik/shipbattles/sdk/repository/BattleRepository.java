package net.lalik.shipbattles.sdk.repository;

import net.lalik.shipbattles.sdk.entity.Battle;

public interface BattleRepository {
    Battle[] getActiveBattlesForAccountId(int accountId);
    Battle save(Battle battle);
}
