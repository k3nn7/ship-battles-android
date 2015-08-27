package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.BattleRepository;

public class BattleService {
    private BattleRepository battleRepository;

    public BattleService(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    public Battle[] getActiveBattlesForAccountId(int accountId) {
        return battleRepository.getActiveBattlesForAccountId(accountId);
    }
}
