package atypon.worldnav.game.items;

import atypon.worldnav.game.settings.Gold;

public class ItemFactory {

     public Item makeItem(String name,Gold price){
         return new FlashLight(name,price);
     }

     public Item makeItem(String keyName, Key.KeyType keyType, Gold price){
         return new Key(keyName,keyType,price,"key");
     }

}
