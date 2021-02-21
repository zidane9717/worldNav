package atypon.worldnav.game.rooms;

import atypon.worldnav.game.entites.Entity;

public class OldRoomBuilder implements RoomBuilder {

    private  Room room;

    public OldRoomBuilder(){
        this.room= new Room();
    }

    @Override
    public void buildLights(int state) {
        room.setRoomLights(state);
    }

    @Override
    public void buildName(String name) {
        room.setRoomName(name);
    }

    @Override
    public void buildNorthWall(Entity entity) {
       room.setNorthWall(entity);
    }

    @Override
    public void buildSouthWall(Entity entity) {
        room.setSouthWall(entity);
    }

    @Override
    public void buildEastWall(Entity entity) {
       room.setEastWall(entity);
    }

    @Override
    public void buildWestWall(Entity entity) {
       room.setWestWall(entity);
    }

    @Override
    public Room getRoom() {
        return this.room;
    }
}
