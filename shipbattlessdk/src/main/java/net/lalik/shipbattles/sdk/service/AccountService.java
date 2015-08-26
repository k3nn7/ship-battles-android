package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createRandomAccount() {
        long timestamp = System.currentTimeMillis() / 1000L;
        String nick = String.format("user%d", timestamp);
        return accountRepository.save(new Account(nick));
    }

    public Account authenticate(String nick, String password) throws InvalidCredentialsException {
        try {
            return accountRepository.findByNickAndPassword(nick, password);
        } catch (EntityNotFoundException e) {
            throw new InvalidCredentialsException();
        }
    }

    public Account authenticate(String token) throws InvalidCredentialsException {
        try {
            return accountRepository.findByToken(token);
        } catch (EntityNotFoundException e) {
            throw new InvalidCredentialsException();
        }
    }
}
