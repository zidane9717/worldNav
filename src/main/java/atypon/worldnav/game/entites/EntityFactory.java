package atypon.worldnav.game.entites;

import atypon.worldnav.game.items.Item;

import java.util.HashMap;
import java.util.List;


public class EntityFactory {

    public static HashMap<String, Door> doors = new HashMap<>();

    public Entity makeEntity(String name, int state) {

        if (doors.containsKey(name)) {
            return doors.get(name);
        }

        Door door = new Door(name, state);
        doors.put(name, door);
        return door;

    }

    public Entity makeEntity(String name, int state, List items) {
        return new Chest(name, state, items);
    }

    public Entity makeEntity(Decor.DecorType name, Item item) {
        return new Decor(name,item);
    }

    public Entity makeEntity(List<Item> items) {
        return new Seller(items);
    }

    public Entity makeEntity() {
        return new Wall();
    }
}
