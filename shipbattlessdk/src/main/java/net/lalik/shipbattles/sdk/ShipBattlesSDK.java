package net.lalik.shipbattles.sdk;

import net.lalik.shipbattles.fakeclient.repository.MemoryAccountRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattleRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryBattlefieldRepository;
import net.lalik.shipbattles.fakeclient.repository.MemoryShipClassRepository;
import net.lalik.shipbattles.sdk.entity.Account;
import net.lalik.shipbattles.sdk.entity.Battle;
import net.lalik.shipbattles.sdk.entity.Battlefield;
import net.lalik.shipbattles.sdk.entity.ShipClass;
import net.lalik.shipbattles.sdk.repository.AccountRepository;
import net.lalik.shipbattles.sdk.repository.BattleRepository;
import net.lalik.shipbattles.sdk.repository.BattlefieldRepository;
import net.lalik.shipbattles.sdk.repository.ShipClassRepository;
import net.lalik.shipbattles.sdk.repository.exception.EntityNotFoundException;
import net.lalik.shipbattles.sdk.service.AccountService;
import net.lalik.shipbattles.sdk.service.BattleService;
import net.lalik.shipbattles.sdk.service.BattlefieldService;
import net.lalik.shipbattles.sdk.service.exception.InvalidCredentialsException;
import net.lalik.shipbattles.sdk.values.AttackResult;
import net.lalik.shipbattles.sdk.values.Coordinate;
import net.lalik.shipbattles.sdk.values.Orientation;
import net.lalik.shipbattles.sdk.values.ShipsInventory;

public class ShipBattlesSDK {
    private static ShipBattlesSDK instance = null;

    private AccountRepository accountRepository;
    private BattleRepository battleRepository;
    private ShipClassRepository shipClassRepository;
    private BattlefieldRepository battlefieldRepository;

    private AccountService accountService;
    private BattleService battleService;
    private BattlefieldService battlefieldService;

    private ShipBattlesSDK() {
        accountRepository = new MemoryAccountRepository();
        battleRepository = new MemoryBattleRepository(accountRepository);
        shipClassRepository = new MemoryShipClassRepository();
        battlefieldRepository = new MemoryBattlefieldRepository();

        accountService = new AccountService(accountRepository);
        battlefieldService = new BattlefieldService(battlefieldRepository);
        battleService = new BattleService(
                battleRepository,
                accountRepository,
                battlefieldRepository,
                battlefieldService
        );
        battlefieldService.setBattleService(battleService);
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

    public Battle attackRandomOpponent(Account attacker) {
        return battleService.attackRandomOpponent(attacker);
    }

    public Battle getBattleById(int battleId) throws EntityNotFoundException {
        return battleRepository.findByid(battleId);
    }

    public ShipClass[] getAllShipClasses() {
        return shipClassRepository.findAll();
    }

    public Battlefield getBattlefieldById(int battlefieldId) throws EntityNotFoundException {
        return battlefieldRepository.findById(battlefieldId);
    }

    public void deployShipsToBattlefield(Battlefield battlefield) throws EntityNotFoundException {
        battlefieldService.commitBattlefield(battlefield);

        Battle battle = battleRepository.findByid(battlefield.getBattleId());
        Battlefield secondBattlefield;
        if (battle.getLeftBattlefieldId() == battlefield.getId()) {
            secondBattlefield = battlefieldRepository.findById(battle.getRightBattlefieldId());
        } else {
            secondBattlefield = battlefieldRepository.findById(battle.getLeftBattlefieldId());
        }

        for (ShipsInventory.Item item : secondBattlefield.shipsInInventory())
            for (int i = 0; i < item.getCount(); i++)
                secondBattlefield.deployShip(new Coordinate(1, 1), Orientation.VERTICAL, item.getShipClass());
        battlefieldService.commitBattlefield(secondBattlefield);
    }

    public AttackResult attackBattlefield(Battlefield battlefield, Coordinate coordinate) {
        AttackResult result = battlefield.attack(coordinate);
        battlefieldRepository.save(battlefield);
        return result;
    }
}
