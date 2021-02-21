package atypon.worldnav.game.playerSystem;

import atypon.worldnav.game.entites.Door;
import atypon.worldnav.game.entites.Entity;
import atypon.worldnav.game.rooms.Room;
import atypon.worldnav.game.settings.Map;
import atypon.worldnav.mvc.controller.GameManager;

public class Navigation {

    private Player player;
    private int x;
    private int y;
    private String looking = "east";
    private String number;

    Navigation(Player player, String number) {
        this.number = number;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getLooking() {
        return looking;
    }

    String move(String direction) throws Exception {

        String wayToGo = looking;

        if (direction.equals("backward")) {
            wayToGo = viceDirection();
        }

        Entity entity = player.currentRoom(player.nav.getY(), player.nav.getX()).wallAt(wayToGo);

        if (entity instanceof Door) { //Check if it is a door
            Door door = (Door) entity;
            if (door.getState() == 1) { // Check if it is open
                if (checkFight(wayToGo)) {
                    doMove(wayToGo);
                    Room room =  player.currentRoom(player.nav.getY(), player.nav.getX());
                    if(room.lootOnFloor != null){
                     return "Entered the " + room.getRoomName() + " room ," + room.getLootOnFloor(player);
                    }
                    return "Entered the " + room.getRoomName() + " room";
                } else {
                    return "there is a fight in the room, try again later";
                }
            } else {
                return "door closed";
            }
        }
        return "no place to move";
    }

    private boolean checkFight(String direction) {
        int x = this.x;
        int y = this.y;

        switch (direction) {
            case "east":
                x++;
                break;
            case "west":
                x--;
                break;
            case "north":
                y--;
                break;
            case "south":
                y++;
                break;
        }

        if(y>5){
            GameManager managerGames = GameManager.getInstance();
            managerGames.wonTheGame(player,number);
            return false;
        }

        if (player.currentRoom(y, x).getFightState() == 1) {
            return false;
        }
        return true;
    }

    private void doMove(String direction) throws Exception {
        GameManager managerGames = GameManager.getInstance();
        Map map = managerGames.getGame(number).getMap();
        String yx = String.valueOf(player.nav.getY()) + String.valueOf(player.nav.getX());
        map.playersLocations.get(yx).remove(player);
        switch (direction) {
            case "east":
                x++;
                break;
            case "west":
                x--;
                break;
            case "north":
                y--;
                break;
            case "south":
                y++;
                break;
        }
        map.playersLocations.get(String.valueOf(player.nav.getY()) + String.valueOf(player.nav.getX())).add(player);
        managerGames.getGame(number).getPlayersManager().checkAnotherPlayer(player, String.valueOf(player.nav.getY()) + String.valueOf(player.nav.getX()));
    }

    String orientate(String direction) {
        if (direction.equals("left")) {
            switch (looking) {
                case "north":
                    looking = "west";
                    break;
                case "west":
                    looking = "south";
                    break;
                case "south":
                    looking = "east";
                    break;
                case "east":
                    looking = "north";
                    break;
            }
        } else if (direction.equals("right")) {
            switch (looking) {
                case "north":
                    looking = "east";
                    break;
                case "east":
                    looking = "south";
                    break;
                case "south":
                    looking = "west";
                    break;
                case "west":
                    looking = "north";
                    break;
            }
        }
        return "You are looking " + looking + " now";
    }

    private String viceDirection() {
        switch (looking) {
            case "east":
                return "west";
            case "west":
                return "east";
            case "north":
                return "south";
            case "south":
                return "north";
        }
        return null;
    }

}
