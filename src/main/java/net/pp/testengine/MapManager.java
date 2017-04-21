package net.pp.testengine;

import lombok.Getter;
import processing.core.PVector;

import java.util.HashMap;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 * Creates a room[][] roomArray
 * render calls are piped through here
 */
public class MapManager implements GameObject{
    @Getter
    private HashMap<Location,Room> roomArray = new HashMap<>();
    private int xSize;
    private int ySize;

    public MapManager(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        createMap();
    }

    private void createMap(){
        for(int i=0; i<xSize; i++){
            for(int j=0; j<ySize; j++){
                roomArray.put(new Location(i,j),new Room(this,new Location(i,j)));
            }
        }
        int startX = (int)(Math.random()*xSize);
        int startY = (int)(Math.random()*ySize);
    }

    @Override
    public void update() {
        roomArray.values().forEach(Room::update);
    }

    public void render(TestEngine engine){
        roomArray.values().forEach(r -> r.render(engine));
    }
    public Room getRelative(Room origin, Direction dir) {
        Location newLocation = origin.position.getRelative(dir);
        if (newLocation.getX() < 0 || newLocation.getY() < 0 || newLocation.getX()> xSize || newLocation.getY() > ySize) return null;
        return roomArray.get(newLocation);
    }

}
