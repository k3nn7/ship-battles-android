package net.lalik.shipbattles.offline.entity;

public class Battle {
    private int state;
    private PlayerBattlefield playerBattlefield;
    private OpponentBattlefield opponentBattlefield;

    public Battle(int state) {
        this.state = state;
        this.playerBattlefield = new PlayerBattlefield();
        this.opponentBattlefield = new OpponentBattlefield();
    }

    public int getState() {
        return state;
    }

    public OpponentBattlefield getOpponentBattlefield() {
        return opponentBattlefield;
    }

    public PlayerBattlefield getPlayerBattlefield() {
        return playerBattlefield;
    }
}
