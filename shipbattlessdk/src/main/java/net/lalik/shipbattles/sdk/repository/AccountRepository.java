package net.lalik.shipbattles.sdk.repository;

import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;

public interface AccountRepository {
    Account save(Account account);
    Account findByNickAndPassword(String nick, String password) throws EntityNotFoundException;
    Account findByToken(String token) throws EntityNotFoundException;
}
