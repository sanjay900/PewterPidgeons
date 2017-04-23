package net.pp.testengine;

import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 */
public class MapManager implements GameObject{
    private TestEngine te;
    @Getter
    private HashMap<Location,Room> roomMap = new HashMap<>();
    HashMap<Integer,ArrayList<Room>> stairMap = new HashMap<>();
    private int xSize;
    private int ySize;
    private int zSize;
    @Getter
    private Location startLoc;

    public MapManager(TestEngine te, int xSize, int ySize, int zSize){
        this.te = te;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        MapGenerator mg = new MapGenerator(te, this, roomMap, xSize, ySize, zSize);
        startLoc = new Location(
                // makes it so we're not on the
                (int)(te.random(1,xSize)),
                (int)(te.random(1,ySize)),
                0);

        mg.createMap(startLoc);
    }
    public float distanceToStair(Room r) {
        float min = Float.MAX_VALUE;
        for(ArrayList<Room> stairArray : stairMap.values()){
            for(Room s : stairArray){
                if (s == null) return 1000;
                float dist = s == null?100:r.position.distance(s.position);
                min = Math.min(min,dist);
            }
        }
        return min;
    }
    @Override
    public void update() {
        roomMap.values().forEach(Room::update);
    }

    public void render(TestEngine engine, Rectangle blueBounds){
        roomMap.values().forEach(r -> {
            r.render(engine, blueBounds);
        });
    }

    public Location getRandomStartLocation(){
        while(true){
            Location random =  new Location(
                    // makes it so we're not on the
                    (int)(Math.random()*(xSize-1)+1),
                    (int)(Math.random()*(ySize-1)+1),
                    0);
            if(!roomMap.get(random).isWall && !roomMap.get(random).isStair){
                return random;
            }
        }
    }

    public boolean isWall(Location location) {
        return roomMap.containsKey(location) && roomMap.get(location).isWall;
    }

    public boolean isStair(Location location) {
        return roomMap.containsKey(location) && roomMap.get(location).isStair ;
    }
    public boolean isMine(Location location, Player toTest) {
        return roomMap.containsKey(location) && roomMap.get(location).getPlacedMine() != null && roomMap.get(location).getPlacedMine() != toTest;
    }
}
