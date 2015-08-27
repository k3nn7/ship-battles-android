package net.lalik.shipbattles.fakeclient.repository;

import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.BattleRepository;

import java.util.ArrayList;

public class MemoryBattleRepository implements BattleRepository {
    ArrayList<Battle> battles;

    public MemoryBattleRepository() {
        battles = new ArrayList<>();
        battles.add(new Battle(1, 2, Battle.STATE.FINISHED));
        battles.add(new Battle(2, 1, Battle.STATE.LEFT_ATTACKS));
    }

    @Override
    public Battle[] getActiveBattlesForAccountId(int accountId) {
        ArrayList<Battle> activeBattles = new ArrayList<>();
        for (Battle battle : battles) {
            if (battle.isActive() && battle.isAccountIdParticipant(accountId)) {
                activeBattles.add(battle);
            }
        }

        return activeBattles.toArray(new Battle[activeBattles.size()]);
    }
}
