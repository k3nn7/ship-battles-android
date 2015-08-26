package net.lalik.shipbattles.sdk.service;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        accountRepository = new MemoryAccountRepository();
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void createRandomAccount() {
        Account account = accountService.createRandomAccount();
        assertNotNull(account);
    }

    @Test
    public void authenticateAccountForValidCredentials() throws Exception {
        Account expectedAccount = accountRepository.save(
                new Account(5, "user", "password", "foobar")
        );

        Account authenticatedAccount = accountService.authenticate("user", "password");
        assertEquals(
                expectedAccount,
                authenticatedAccount
        );
    }

    @Test(expected=InvalidCredentialsException.class)
    public void authenticateAccountForInvalidCredentials() throws Exception {
        accountService.authenticate("foo", "bar");
    }
}
