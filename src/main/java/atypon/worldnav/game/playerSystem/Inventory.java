package atypon.worldnav.game.playerSystem;

import atypon.worldnav.game.items.Item;

import java.util.Collection;
import java.util.HashMap;

public class Inventory {

    private final HashMap<String, Item> inventory = new HashMap<>();

    public void addItem(Item item){
        inventory.put(item.getName(),item);
    }

    public void removeItem(String name){
        inventory.remove(name);
    }

    public boolean checkItem(String name){
        return inventory.containsKey(name);
    }

    public Item getItem(String name){
        return inventory.get(name);
    }

    public String showItems(){
        return inventory.keySet().toString();
    }

    public Collection<Item> dropItems(){
        return inventory.values();
    }

}
