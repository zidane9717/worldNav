package atypon.worldnav.mvc.controller;

import atypon.worldnav.game.playerSystem.Player;
import atypon.worldnav.game.playerSystem.PlayersManager;
import atypon.worldnav.game.settings.Game;
import atypon.worldnav.mvc.InvalidName;
import atypon.worldnav.mvc.ReceivedMessage;
import atypon.worldnav.mvc.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate webSocket;
    static GameController instance;

    GameController() {
        instance = this;
    }

    public static GameController getInstance() {
        return instance;
    }

    public void sendToClients(String number, String message) throws Exception {
        webSocket.convertAndSend("/topic/greetings/" + number, "{\"content\":\"" + message + "\"}");
    }

    public void fightModeBroadCast(String number, Player playerOne, Player playerTwo, String message) {
        webSocket.convertAndSend("/topic/greetings/" + number + "/" + playerOne.getName(), "{\"content\":\"" + message + "\"}");
        webSocket.convertAndSend("/topic/greetings/" + number + "/" + playerTwo.getName(), "{\"content\":\"" + message + "\"}");
    }

    public void broadcastPlayersList(String number) {
        GameManager manager = GameManager.getInstance();
        webSocket.convertAndSend("/topic/greetings/" + number, "{\"content\":\"" + "" + "!!! ==Updated list== !!!" + "\"}");
        for (String name : manager.getGame(number).getPlayersManager().players.keySet()) {
            String message = "player : " + name;
            webSocket.convertAndSend("/topic/greetings/" + number, "{\"content\":\"" + message + "\"}");
        }
    }

    @RequestMapping(value = "/fight", method = RequestMethod.GET)
    @ResponseBody
    public void fight(@RequestParam("number") String number, @RequestParam("name") String name, @RequestParam("id") String id, @RequestParam("content") String attackMove) throws InterruptedException {
        GameManager.getInstance().getGame(number).getPlayersManager().processFight(Integer.parseInt(id), name, attackMove);
    }

    @RequestMapping(value = "/disconnect", method = RequestMethod.GET)
    @ResponseBody
    public void disconnect(@RequestParam("number") String number, @RequestParam("name") String name) throws InterruptedException {
        GameManager manger = GameManager.getInstance();
        PlayersManager playerManger = manger.getGame(number).getPlayersManager();
        playerManger.disconnect(name);
        broadcastPlayersList(number);
    }

    @MessageMapping("/hello/{number}/{username}")
    @SendTo("/topic/greetings/{number}/{username}")
    public SendMessage greeting(ReceivedMessage message) throws Exception {
        String number = message.getNumber();
        String name = message.getName();
        String command = message.getContent();

        GameManager managerGames = GameManager.getInstance();
        if (managerGames.checkGame(number)) {
            if (managerGames.getGame(number).getGameStatus()) {
                String answer = managerGames.getGame(number).getPlayersManager().processCommand(name, command);
                return new SendMessage("Game: " + answer);
            }
            return new SendMessage("Game: waiting for host to start the game..");
        }
        return new SendMessage("Game has finished");
    }

    @RequestMapping(value = "/validateHosting", method = RequestMethod.GET)
    @ResponseBody
    public String validateHost(@RequestParam("number") String number, @RequestParam("name") String name) throws Exception {
        GameManager manager = GameManager.getInstance();
        //Host game
        if (!manager.checkGame(number) && !number.equals("") && !name.equals("")) {
            manager.createGame(number);
            manager.getGame(number).getPlayersManager().addPlayer(name, manager.getGame(number).getMap());
            return "ANNOUNCEMENT: GAME CREATED";
        }
        throw new InvalidName("Host number already used");
    }

    @RequestMapping(value = "/validateJoinning", method = RequestMethod.GET)
    @ResponseBody
    public String validateJoin(@RequestParam("number") String number, @RequestParam("name") String name) throws Exception {
        GameManager manager = GameManager.getInstance();
        //Join game
        if (manager.checkGame(number)) { //Check game exist
            if (manager.getGame(number).getJoinable()) {  //Check game joinable
                if (!manager.getGame(number).getPlayersManager().checkPlayerName(name)) { //Check name not exist
                    manager.getGame(number).getPlayersManager().addPlayer(name, manager.getGame(number).getMap());
                    sendToClients(number, "ANNOUNCEMENT: " + name + " joined the game.");
                    return "ANNOUNCEMENT: " + name + " joined the game.";
                }
                throw new InvalidName("Name used");
            }
            throw new InvalidName("Game closed");
        }
        throw new InvalidName("Game does not exist");
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.GET)
    @ResponseBody
    public String startGame(@RequestParam("number") String number) throws Exception {
        Game game = GameManager.getInstance().getGame(number);
        game.start();
        broadcastPlayersList(number);
        sendToClients(number, "ANNOUNCEMENT: GAME STARTED");
        return "";
    }

}
