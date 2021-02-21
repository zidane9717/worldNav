package atypon.worldnav.game.entites;


import atypon.worldnav.game.playerSystem.Player;

public class Door implements Entity,CheckableEntity {

    private final String name;
    private int state;

    Door(String name, int state) {
        this.name=name;
        this.state=state;
    }

    @Override
    public String look() {
        return "the " + name + " door";
    }

    @Override
    public String check(Player player) {
        if (state == 0) {
            return "Door is locked, " + name + " key is needed";
        }
        return "The " + name+ " door is open. if you can't move through it , there is a fight inside the room, try again later.";
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
