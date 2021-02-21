package atypon.worldnav.game.entites;

import atypon.worldnav.game.items.Item;
import atypon.worldnav.game.items.Key;
import atypon.worldnav.game.playerSystem.Player;
import atypon.worldnav.game.settings.Gold;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Chest implements Entity,CheckableEntity {

    private List items;
    private String name;
    private int state;

    public Chest(String name, int state, List items) {
        this.name=name;
        this.state=state;
        this.items = items;
    }

    @Override
    public String look() {
        return "the " + name + " chest";
    }

    @Override
    public String check(Player player) {

        if (state == 0) {
            return "Chest closed '" + name + "' key is needed to unlock";
        }

        if (items.isEmpty()) {
            return "Chest is clear";
        }

       String answer="Items are looted: ";
        for (Object chestLoot : items) {
            if (chestLoot instanceof Gold) {
                BigDecimal chestLootVal = ((Gold) chestLoot).getValue();
                player.gold = player.gold.add(chestLootVal, new MathContext(4));
                answer=answer+" [" + ((Gold) chestLoot).getValue() + " gold] ";
            } else if (chestLoot instanceof Item) {
                player.inventory.addItem((Item) chestLoot);
                if(chestLoot instanceof Key){
                    answer=answer+" [The " + ((Item) chestLoot).getName() + " key] ";
                }else{
                    answer=answer+" [The " + ((Item) chestLoot).getName() + " ] ";
                }
            }
        }
        List itemsEmpty = new ArrayList();

        items = itemsEmpty;
        return answer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state= state;
    }
}
