package atypon.worldnav.game.settings;

import atypon.worldnav.game.playerSystem.Player;
import atypon.worldnav.game.rooms.Room;
import atypon.worldnav.game.rooms.RoomEngineer;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {

    private Room[][] graph;
    public HashMap<String, ArrayList<Player>> playersLocations;
    private String number;

    public Map(String number) {
        this.number = number;
        playersLocations = new HashMap<>();
        initiateRoomsAndMap();
    }

    public Room roomAt(int y, int x) {
        return graph[y][x];
    }

    private void initiateRoomsAndMap() {

        RoomEngineer roomEngineer = new RoomEngineer();

        graph = new Room[6][9];

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 9; x++) {

                if ((y == 0 && x == 0) || (y == 0 && x == 8) || (y == 5 && x == 0) || (y == 5 && x == 8)) {
                    continue;
                } else if (y == 0 && x % 2 == 1) {
                    roomEngineer.makeDecorRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 0 && x % 2 == 0) {
                    roomEngineer.makeDecorRoom(2);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((x == 0 && y % 2 == 1)) {
                    roomEngineer.makeDecorRoom(3);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (x == 0 && y % 2 == 0) {
                    roomEngineer.makeDecorRoom(4);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (x == 8 && y % 2 == 0) {
                    roomEngineer.makeDecorRoom(5);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (x == 8 && y % 2 == 1) {
                    roomEngineer.makeDecorRoom(6);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((y == 2 && x == 2) || (y == 2 && x == 6)) {
                    roomEngineer.makeChestRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 3 && x == 4) {
                    roomEngineer.makeChestRoom(2);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((y == 3 && x == 2) || (y == 3 && x == 6)) {
                    roomEngineer.makeSellerRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 2 && x == 4) {
                    roomEngineer.makeSellerRoom(2);
                    graph[y][x] = roomEngineer.getRoom();
                } else if ((y == 5 && x == 1) || (y == 5 && x == 7)) {
                    roomEngineer.makeDecorRoom(1);
                    graph[y][x] = roomEngineer.getRoom();
                } else if (y == 5) {
                    roomEngineer.makeWinnerRoom();
                    graph[y][x] = roomEngineer.getRoom();
                } else {
                    roomEngineer.makeLobbyRoom();
                    graph[y][x] = roomEngineer.getRoom();
                }
                playersLocations.put(String.valueOf(y) + String.valueOf(x), new ArrayList<Player>());
            }
        }
    }

    public void roomAvailability(String yx, int state) {
        int x = Integer.parseInt(String.valueOf(yx.charAt(1)));
        int y = Integer.parseInt(String.valueOf(yx.charAt(0)));
        Room room = graph[y][x];
        room.setFightState(state);
    }

}
