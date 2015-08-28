package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;

public class BattleService {
    private BattleRepository battleRepository;
    private AccountRepository accountRepository;
    private BattlefieldService battlefieldService;

    public BattleService(
            BattleRepository battleRepository,
            AccountRepository accountRepository,
            BattlefieldService battlefieldService
    ) {
        this.battleRepository = battleRepository;
        this.accountRepository = accountRepository;
        this.battlefieldService = battlefieldService;
    }

    public Battle[] getActiveBattlesForAccountId(int accountId) {
        return battleRepository.getActiveBattlesForAccountId(accountId);
    }

    public Battle attackRandomOpponent(Account attacker) {
        Account opponent = accountRepository.findRandomWithIdOtherThan(attacker.getId());
        Battle battle = new Battle(
                attacker,
                opponent,
                battlefieldService.prepareBattlefield().getId(),
                battlefieldService.prepareBattlefield().getId(),
                Battle.STATE.DEPLOY
        );

        return battleRepository.save(battle);
    }
}
