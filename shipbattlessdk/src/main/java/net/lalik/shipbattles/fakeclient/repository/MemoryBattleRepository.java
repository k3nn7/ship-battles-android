package net.lalik.shipbattles.fakeclient.repository;

import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

import java.util.ArrayList;

public class MemoryBattleRepository implements BattleRepository {
    ArrayList<Battle> battles;
    private AccountRepository accountRepository;

    public MemoryBattleRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        battles = new ArrayList<>();
        createSampleBattles();
    }

    private void createSampleBattles() {
        try {
            battles.add(new Battle(
                    accountRepository.findById(1),
                    accountRepository.findById(2),
                    0, 0,
                    Battle.STATE.FINISHED
            ));
            battles.add(new Battle(
                    accountRepository.findById(2),
                    accountRepository.findById(1),
                    0, 0,
                    Battle.STATE.LEFT_ATTACKS
            ));
        } catch (EntityNotFoundException e) {
        }
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

    @Override
    public Battle save(Battle battle) {
        int newId = battles.size() + 1;
        battle.setId(newId);
        battles.add(battle);
        return battle;
    }

    @Override
    public Battle findByid(int battleId) throws EntityNotFoundException {
        for (Battle battle: battles)
            if (battle.getId() == battleId)
                return battle;
        throw new EntityNotFoundException();
    }
}
