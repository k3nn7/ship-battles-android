package net.lalik.shipbattles.fakeclient.repository;

import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

import java.util.ArrayList;

public class MemoryAccountRepository implements AccountRepository {
    ArrayList<Account> accounts;

    public MemoryAccountRepository() {
        accounts = new ArrayList<>();

        accounts.add(new Account(
                1,
                "testuser",
                "dev123",
                "foobar"
        ));
    }

    @Override
    public Account save(Account account) {
        int newId = accounts.size() + 1;
        Account persistedAccount = new Account(
                newId,
                account.getNick(),
                account.getPassword(),
                account.getAuthToken()
        );
        accounts.add(persistedAccount);
        return persistedAccount;
    }

    @Override
    public Account findByNickAndPassword(String nick, String password) throws EntityNotFoundException {
        for (Account account : accounts) {
            if (nick.equals(account.getNick()) && account.isPasswordValid(password)) {
                return account;
            }
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Account findByToken(String token) throws EntityNotFoundException {
        for (Account account : accounts) {
            if (token.equals(account.getAuthToken())) {
                return account;
            }
        }
        throw new EntityNotFoundException();
    }
}
