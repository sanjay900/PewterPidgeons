package net.pp.testengine;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Klimpen on 21/04/2017.
 */
@AllArgsConstructor
public class MapGenerator {
    private MapManager manager;
    private HashMap<Location, Room> roomMap;
    private int xSize, ySize;

    public void createMap(Location startLoc){
        // from a room (on init: random)
        // check its adjacent rooms
        // if they havnt been init AND are within bounds
        // a random number of them will be walls
        // otherwise they are notwalls - able to be moved into
        // then recurse
        createRoom(startLoc);
        generateRoom(startLoc);
        Location stair = generateStaircase(startLoc);
        if (stair == null) return;
        createMap(stair.getRelative(Direction.UP));
    }

    private Location generateStaircase(Location startLoc) {
        if (startLoc.getZ() == 2) return null;
        Location loc = startLoc;
        while (loc.equals(startLoc) || roomMap.get(loc).isWall || roomMap.get(loc.getRelative(Direction.EAST)).isWall) {
            loc = new Location((int)(Math.random()*xSize),(int)(Math.random()*ySize),startLoc.getZ());
        }
        roomMap.get(loc).isStair = true;
        return loc;
    }

    private void generateRoom(Location loc){
        ArrayList<Location> adjRoomList = new ArrayList<>();

        checkAdj(adjRoomList, loc.getRelative(Direction.NORTH));
        checkAdj(adjRoomList, loc.getRelative(Direction.EAST));
        checkAdj(adjRoomList, loc.getRelative(Direction.SOUTH));
        checkAdj(adjRoomList, loc.getRelative(Direction.WEST));
        int numWalls = (int)(Math.random()*adjRoomList.size());

        for(int i=0; i<adjRoomList.size(); i++){
            if(i<numWalls){
                createWall(adjRoomList.get(i));
            } else {
                createRoom(adjRoomList.get(i));
            }
        }

        for(int i=0; i<adjRoomList.size(); i++){
            generateRoom(adjRoomList.get(i));
        }
    }

    private void checkAdj(ArrayList<Location> adjRoomList, Location loc){
        if(loc.getX()<xSize-1 && 0<loc.getX()){
            if(loc.getY()<ySize-1 && 0<loc.getY()){
                if(!roomMap.containsKey(loc)){
                    adjRoomList.add(loc);
                    return;
                }
            }
        }
        createWall(loc);
    }

    private void createRoom(Location loc){
        if(!roomMap.containsKey(loc)){
            roomMap.put(loc, new Room(manager, loc, false));
        } else {
            roomMap.get(loc).isWall=false;
        }
    }

    private void createWall(Location loc){
        roomMap.putIfAbsent(loc, new Room(manager, loc, true));
    }
}
