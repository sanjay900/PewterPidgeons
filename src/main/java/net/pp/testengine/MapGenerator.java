package net.pp.testengine;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Klimpen on 21/04/2017.
 */
@AllArgsConstructor
public class MapGenerator {
    private TestEngine te;
    private MapManager manager;
    private HashMap<Location, Room> roomMap;
    private int xSize, ySize, zSize;

    public void createMap(Location startLoc){
        // from a room (on init: random)
        // check its adjacent rooms
        // if they havnt been init AND are within bounds
        // a random number of them will be walls
        // otherwise they are notwalls - able to be moved into
        // then recurse
        createRoom(startLoc);
        generateRoom(startLoc);
        Location stair = null;
        for(int i=0; i<3; i++) {
            stair = generateStaircase(startLoc);
        }
        if (stair == null) return;
        createMap(stair.getRelative(Direction.UP));
    }

    private Location generateStaircase(Location startLoc) {
        if (startLoc.getZ() == zSize) return null;
        Location loc = null;
        while (loc == null || roomMap.get(loc).isSolid() || roomMap.get(loc.getRelative(Direction.EAST)).isSolid()) {
            loc = new Location((int)(te.random(1,xSize)),(int)(te.random(1,ySize)),startLoc.getZ());
        }
        roomMap.get(loc).isStair = true;
        manager.stairMap.putIfAbsent(loc.getZ(),new ArrayList<>());
        manager.stairMap.get(loc.getZ()).add(roomMap.get(loc));
        return loc;
    }

    private void generateRoom(Location loc){
        ArrayList<Location> adjRoomList = new ArrayList<>();

        checkAdj(adjRoomList, loc.getRelative(Direction.NORTH));
        checkAdj(adjRoomList, loc.getRelative(Direction.EAST));
        checkAdj(adjRoomList, loc.getRelative(Direction.SOUTH));
        checkAdj(adjRoomList, loc.getRelative(Direction.WEST));
        int numWalls = (int)(te.random(adjRoomList.size()));

        for(int i=0; i<adjRoomList.size(); i++){
            if(i<numWalls){
                if(!roomMap.containsKey(adjRoomList.get(i).getRelative(Direction.DOWN))||(!roomMap.get(adjRoomList.get(i).getRelative(Direction.DOWN)).isStair)) {
                        createWall(adjRoomList.get(i));
                }
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
