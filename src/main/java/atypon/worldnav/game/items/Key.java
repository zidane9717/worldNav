package atypon.worldnav.game.items;

import atypon.worldnav.game.entites.CheckableEntity;
import atypon.worldnav.game.entites.Entity;
import atypon.worldnav.game.playerSystem.Player;
import atypon.worldnav.game.settings.Gold;


public class Key extends Item {

    public enum KeyType {
        DOOR, CHEST;
    }

    private KeyType keyType;

    Key(String keyName, KeyType type, Gold price, String key) {
        keyType = type;
        setName(keyName);
        setPrice(price);
    }

    @Override
    public String use(Player player) {
        Entity entity = player.currentRoom(player.nav.getY(),player.nav.getX()).wallAt(player.nav.getLooking());
        if (entity instanceof CheckableEntity) {
             CheckableEntity entity1 = (CheckableEntity) entity;
            if (entity1.getName().equals(getName().toLowerCase())) { //if entity name = key name
                if (entity1.getState() == 1) {
                    entity1.setState(0);
                    return entity1.getName() + " " + keyType + " locked";
                }
                entity1.setState(1);
                return entity1.getName() + " " + keyType + " unlocked";
            }
        }
        return "i can only use keys for doors or chests..";
    }

}
