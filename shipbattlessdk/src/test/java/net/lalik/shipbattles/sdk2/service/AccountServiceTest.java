package net.lalik.shipbattles.sdk2.service;

import net.lalik.shipbattles.sdk2.client.InMemoryApi;
import net.lalik.shipbattles.sdk2.entity.Account;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest {
    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = new AccountService(new InMemoryApi());
    }

    @Test
    public void createRandomAccount() {
        Account account = accountService.createRandomAccount();
        assertEquals("561439f48d5e0e000c8e7f42", account.getId());
        assertEquals("user1444166132", account.getNick());
        assertEquals("172bc83648184fe9b296321cd1184900", account.getSessionToken());
    }
}
