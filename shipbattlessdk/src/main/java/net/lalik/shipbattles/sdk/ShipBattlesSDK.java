package net.lalik.shipbattles.sdk;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattleRepository;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.AccountService;
import net.lalik.shipbattles.sdk.service.BattleService;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;

public class ShipBattlesSDK {
    private static ShipBattlesSDK instance = null;

    private AccountRepository accountRepository;
    private AccountService accountService;
    private BattleRepository battleRepository;
    private BattleService battleService;

    private ShipBattlesSDK() {
        accountRepository = new MemoryAccountRepository();
        accountService = new AccountService(accountRepository);
        battleRepository = new MemoryBattleRepository(accountRepository);
        battleService = new BattleService(battleRepository);
    }

    static public ShipBattlesSDK getInstance() {
        if (instance == null) {
            instance = new ShipBattlesSDK();
        }
        return instance;
    }

    public Account createRandomAccount() {
        return accountService.createRandomAccount();
    }

    public Account authenticate(String nick, String password) throws InvalidCredentialsException {
        return accountService.authenticate(nick, password);
    }

    public Account authenticate(String token) throws InvalidCredentialsException {
        return accountService.authenticate(token);
    }

    public Account getAccountById(int accountId) throws EntityNotFoundException {
        return accountRepository.findById(accountId);
    }

    public Battle[] getActiveBattlesForAccountId(int accountId) {
        return battleService.getActiveBattlesForAccountId(accountId);
    }
}
