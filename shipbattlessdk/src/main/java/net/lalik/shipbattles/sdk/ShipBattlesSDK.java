package net.lalik.shipbattles.sdk;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.service.AccountService;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;

public class ShipBattlesSDK {
    private static ShipBattlesSDK instance = null;

    private AccountRepository accountRepository;
    private AccountService accountService;

    private ShipBattlesSDK() {
        accountRepository = new MemoryAccountRepository();
        accountService = new AccountService(accountRepository);
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
}
