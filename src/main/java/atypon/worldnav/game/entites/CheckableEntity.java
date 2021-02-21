package atypon.worldnav.game.entites;

import atypon.worldnav.game.playerSystem.Player;

public interface CheckableEntity{
    String check(Player player);
    String getName();
    int getState();
    void setState(int state);
}
