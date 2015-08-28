package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;

public class BattleService {
    private BattleRepository battleRepository;
    private AccountRepository accountRepository;

    public BattleService(BattleRepository battleRepository, AccountRepository accountRepository) {
        this.battleRepository = battleRepository;
        this.accountRepository = accountRepository;
    }

    public Battle[] getActiveBattlesForAccountId(int accountId) {
        return battleRepository.getActiveBattlesForAccountId(accountId);
    }

    public Battle attackRandomOpponent(Account attacker) {
        Account opponent = accountRepository.findRandomWithIdOtherThan(attacker.getId());
        Battle battle = new Battle(
                attacker,
                opponent,
                0, 0,
                Battle.STATE.DEPLOY
        );
        return battleRepository.save(battle);
    }
}
