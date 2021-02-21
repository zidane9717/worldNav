package atypon.worldnav.game.playerSystem;

import atypon.worldnav.game.items.Item;
import atypon.worldnav.game.rooms.Room;
import atypon.worldnav.game.settings.Map;
import atypon.worldnav.mvc.controller.GameController;
import atypon.worldnav.mvc.controller.GameManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PlayersManager {

    public HashMap<String, Player> players = new HashMap<>();
    private final ArrayList<String> bookedRooms = new ArrayList();
    HashMap<Integer, FightMode> liveFightModes = new HashMap<>();
    Map map;
    String number;


    public PlayersManager(Map map, String number) {
        this.number = number;
        this.map = map;
    }

    public void addPlayer(String name, Map map) {
        Player player = new Player(name, number);
        generateCoordinates(player);
        players.put(name, player);
        map.playersLocations.get(String.valueOf(player.nav.getY()) + player.nav.getX()).add(player);
    }

    private void generateCoordinates(Player player) {
        while (true) {
            int x = ThreadLocalRandom.current().nextInt(0, 8 + 1);
            int y = ThreadLocalRandom.current().nextInt(0, 5 + 1);

            if ((y == 0 && x == 0) || (y == 0 && x == 8) || (y == 5 && x == 0) || (y == 5 && x == 8)) {
                continue;
            }

            if (!bookedRooms.contains(String.valueOf(y + x))) {
                player.nav.setX(x);
                player.nav.setY(y);
                bookedRooms.add(String.valueOf(y) + x);
                break;
            }
        }
    }

    public String processCommand(String name, String command) throws Exception {
        Player player = players.get(name);
        String[] words = command.toLowerCase().split(" ");

        // if command -> look, check, open, trade. while no lights by flashlight or room lights switch then print its dark.
        if (command.equals("look") || command.equals("check") ||
                command.equals("open") || command.equals("trade") ||
                (words.length > 1 && words[0].equals("use") && !words[1].equals("flashlight"))) {
            if (!player.checkRoomLightning()) {
                return "dark use flashlight or switchlights on";
            }
        }

        switch (command.toLowerCase()) {
            case "look":
                return player.look();
            case "check":
                return player.check();
            case "open":
                return player.open();
            case "switchlights":
                return player.switchLights();
            case "forward":
                return player.nav.move("forward");
            case "backward":
                return player.nav.move("backward");
            case "playerstatus":
                return player.playerStatus();
            case "left":
                return player.nav.orientate("left");
            case "right":
                return player.nav.orientate("right");
            case "trade":
                return player.tradeMode();
            case "list":
                return player.list();
            default:
                if ((words.length == 3 && words[0].equals("use") && words[2].equals("key"))
                        || (words.length == 2 && words[0].equals("use") && words[1].equals("flashlight"))) {
                    return player.use(words[1]);
                } else if (words.length == 2 && words[0].equals("buy")) {
                    return player.validatePayment(words[1]);
                } else if (words.length == 3 && words[0].equals("sell")) {
                    return player.validateSelling(words[1]);
                }
                break;
        }
        return "Invalid command";
    }

    void checkAnotherPlayer(Player player, String yx) throws Exception {
        if (map.playersLocations.get(yx).size() > 1) {
            map.roomAvailability(yx, 1);
            FightMode fightMode = new FightMode(map.playersLocations.get(yx).get(0), map.playersLocations.get(yx).get(1), number);
            liveFightModes.put(fightMode.id, fightMode);
            fightMode.startFight();
        }
    }

    public void processFight(int id, String name, String attackMove) {
        liveFightModes.get(id).proceed(name, attackMove);
    }

    public boolean checkPlayerName(String name) {
        return players.containsKey(name);
    }

    public void disconnect(String name) {
        Player player = players.get(name);
        int x = player.nav.getX();
        int y = player.nav.getY();
        Room room = players.get(name).currentRoom(y, x);
        ArrayList<Item> items = new ArrayList<>(player.inventory.dropItems());
        if (items.size() > 0) {
            room.setLootOnFloor(items);
        }
        goldDistribution(player);
        terminate(player);
    }

    void goldDistribution(Player playerLosser) {
        GameManager manager = GameManager.getInstance();
        GameController controller = GameController.getInstance();
        PlayersManager managerPlayers = manager.getGame(number).getPlayersManager();

        if (managerPlayers.players.size() <= 1) {
            //Finish Game
        } else {
            double gold = playerLosser.gold.doubleValue() / (managerPlayers.players.size() - 1);
            for (Player player : managerPlayers.players.values()) {
                player.gold = BigDecimal.valueOf(player.gold.doubleValue() + gold);
            }
            try {
                controller.sendToClients(number, "ANOUNCEMENT: EVERYONE GAINED " + gold + " GOLD");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void terminate(Player playerLoser) {
        GameManager manager = GameManager.getInstance();
        manager.getGame(number).getPlayersManager().players.remove(playerLoser.getName(), playerLoser);
        manager.getGame(number).getMap().playersLocations.get(String.valueOf(playerLoser.nav.getY()) + String.valueOf(playerLoser.nav.getX())).remove(playerLoser);
        manager.getGame(number).getMap().roomAvailability(String.valueOf(playerLoser.nav.getY()) + String.valueOf(playerLoser.nav.getX()), 0);
    }
}
