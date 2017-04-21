package net.pp.testengine;

import lombok.Getter;

import java.util.HashMap;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 */
public class MapManager implements GameObject{
    @Getter
    private HashMap<Location,Room> roomMap = new HashMap<>();
    private int xSize;
    private int ySize;
    @Getter
    private Location startLoc;

    public MapManager(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        MapGenerator mg = new MapGenerator(this, roomMap, xSize, ySize);
        startLoc = new Location(
                // makes it so we're not on the
                Math.max(1,(int)(Math.random()*xSize)),
                Math.max(1,(int)(Math.random()*ySize)));

        mg.createMap(startLoc);
    }

    @Override
    public void update() {
        roomMap.values().forEach(Room::update);
    }

    public void render(TestEngine engine){
        roomMap.values().forEach(r -> r.render(engine));
    }
    public Room getRelative(Room origin, Direction dir) {
        Location newLocation = origin.position.getRelative(dir);
        if (newLocation.getX() < 0 || newLocation.getY() < 0 || newLocation.getX()> xSize || newLocation.getY() > ySize) return null;
        return roomMap.get(newLocation);
    }

}
