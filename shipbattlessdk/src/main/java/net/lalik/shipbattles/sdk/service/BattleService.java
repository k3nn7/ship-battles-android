package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;
import net.lalik.shipbattles.sdk.repository.BattlefieldRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

public class BattleService {
    private BattleRepository battleRepository;
    private AccountRepository accountRepository;
    private BattlefieldRepository battlefieldRepository;
    private BattlefieldService battlefieldService;

    public BattleService(
            BattleRepository battleRepository,
            AccountRepository accountRepository,
            BattlefieldRepository battlefieldRepository,
            BattlefieldService battlefieldService
    ) {
        this.battleRepository = battleRepository;
        this.accountRepository = accountRepository;
        this.battlefieldRepository = battlefieldRepository;
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
                Battle.STATE.DEPLOY
        );
        battle = battleRepository.save(battle);

        battle.setLeftBattlefieldId(battlefieldService.prepareBattlefield(battle).getId());
        battle.setRightBattlefieldId(battlefieldService.prepareBattlefield(battle).getId());

        return battleRepository.save(battle);
    }

    public void handleBattlefieldChange(Battlefield battlefield) throws EntityNotFoundException {
        Battle battle = battleRepository.findByid(battlefield.getBattleId());
        Battlefield secondBattlefield;
        if (battle.getLeftBattlefieldId() == battlefield.getId()) {
            secondBattlefield = battlefieldRepository.findById(battle.getRightBattlefieldId());
        } else {
            secondBattlefield = battlefieldRepository.findById(battle.getLeftBattlefieldId());
        }
        battle.setState(getNewBattleState(battle, battlefield, secondBattlefield));
        battleRepository.save(battle);
    }

    private Battle.STATE getNewBattleState(
            Battle battle,
            Battlefield firstBattlefield,
            Battlefield secondBattlefield
    ) {
        switch (battle.getState()) {
            case DEPLOY:
                if (firstBattlefield.isDeployed() && secondBattlefield.isDeployed()) {
                    return Battle.STATE.FIRE_EXCHANGE;
                }
        }
        return battle.getState();
    }
}
