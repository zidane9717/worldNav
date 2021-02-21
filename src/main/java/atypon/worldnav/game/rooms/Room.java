package atypon.worldnav.game.rooms;

import atypon.worldnav.game.entites.Entity;
import atypon.worldnav.game.items.Item;
import atypon.worldnav.game.playerSystem.Player;

import java.util.ArrayList;

public class Room implements RoomPlan{

    public int getFightState() {
        return fightState;
    }

    public void setFightState(int fightState) {
        this.fightState = fightState;
    }

    private int fightState = 0;

    public ArrayList<Item> lootOnFloor;
    private int illumination;
    private String name;
    private Entity northWall;
    private Entity southWall;
    private Entity eastWall;
    private Entity westWall;

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public int getIllumination() {
        return illumination;
    }

    @Override
    public void setRoomName(String name) {
        this.name=name;
    }

    public String getRoomName() {
        return name;
    }

    @Override
    public void setRoomLights(int state) {
        illumination=state;
    }

    @Override
    public void setNorthWall(Entity north) {
        northWall=north;
    }

    private Entity getNorthWall() {
        return northWall;
    }

    @Override
    public void setSouthWall(Entity south) {
        southWall=south;
    }

    private Entity getSouthWall() {
        return southWall;
    }

    @Override
    public void setEastWall(Entity east) {
        eastWall=east;
    }

    private Entity getEastWall() {
        return eastWall;
    }

    @Override
    public void setWestWall(Entity west) {
        westWall=west;
    }

    private Entity getWestWall() {
        return westWall;
    }

    @Override
    public Entity wallAt(String looking) {
        switch (looking) {
            case "north":
                return getNorthWall();
            case "south":
                return getSouthWall();
            case "east":
                return getEastWall();
            case "west":
                return getWestWall();
        }
        return null;
    }

    public void setLootOnFloor(ArrayList<Item> values) {
        lootOnFloor= values;
    }

    public String getLootOnFloor(Player player) {
        String answer = " items looted from the floor : ";
            for(Item item : lootOnFloor){
                player.inventory.addItem(item);
                answer=answer+" "+item.getName();
            }
            lootOnFloor=null;
            return answer;
    }
}
