package net.lalik.shipbattles.offline.entity;

public class Battle {
    private int state;
    private PlayerBattlefield playerBattlefield;

    public Battle(int state) {
        this.state = state;
        this.playerBattlefield = new PlayerBattlefield();
    }

    public int getState() {
        return state;
    }

    public PlayerBattlefield getPlayerBattlefield() {
        return playerBattlefield;
    }
}
