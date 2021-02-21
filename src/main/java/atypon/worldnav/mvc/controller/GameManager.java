package atypon.worldnav.mvc.controller;

import atypon.worldnav.game.playerSystem.Player;
import atypon.worldnav.game.settings.Game;

import java.util.HashMap;

public class GameManager {

    private static final HashMap<String, Game> games = new HashMap<>();

    private static GameManager instance;

    private GameManager(){}

    public static GameManager getInstance(){
        if(instance == null) {
            return instance = new GameManager();
        }
        return instance;
    }

    public  boolean checkGame(String number){
        return games.containsKey(number);
    }

    public  Game getGame(String number){
        return games.get(number);
    }

    public  void removeGame(String number){
        games.remove(number);
    }

    public void createGame(String number) {
        games.put(number,new Game(number));
    }

    public void wonTheGame(Player player,String number){
        GameController gameController = GameController.getInstance();
        try {
            gameController.sendToClients(number,"CONGRATULATIONS to "+player.getName()+" HAS WON THE GAME");
        } catch (Exception e) {
            e.printStackTrace();
        }
        games.remove(number,games.get(number));
    }
}
