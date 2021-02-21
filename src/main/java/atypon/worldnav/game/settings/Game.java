package atypon.worldnav.game.settings;


import atypon.worldnav.game.playerSystem.PlayersManager;

public class Game {

    Map map;
    static Time time;
    Thread timer;
    private boolean joinable;
    private boolean gameStatus;
    private PlayersManager manager;
    String number;

    public Game(String number) {
        this.number = number;
        gameStatus = false;
        joinable = true;
        map = new Map(number);
        manager = new PlayersManager(map, number);
    }

    public PlayersManager getPlayersManager() {
        return manager;
    }

    public boolean getGameStatus() {
        return gameStatus;
    }

    private void setGameStatus() {
        gameStatus = true;
    }

    public boolean getJoinable() {
        return joinable;
    }

    private void disableJoinable() {
        joinable = false;
    }

    private void startTimer(String number) {
        time = new Time(number);
        timer = new Thread(time);
        timer.start();
    }

    public String getTime() {
        return time.time();
    }

    public Map getMap() {
        return this.map;
    }

    public void start() {
        disableJoinable();
        setGameStatus();
        startTimer(number);
    }
}