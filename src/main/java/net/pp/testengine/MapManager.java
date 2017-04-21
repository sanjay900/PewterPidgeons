package net.pp.testengine;

import lombok.Getter;

import java.util.HashMap;

import java.util.ArrayList;

import static processing.core.PApplet.print;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 * Creates a room[][] roomMap
 * render calls are piped through here
 */
public class MapManager implements GameObject{
    @Getter
    private HashMap<Location,Room> roomMap = new HashMap<>();
    private int xSize;
    private int ySize;

    public MapManager(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        createMap();
    }

    private void createMap(){
        // from a room (on init: random)
        // check its adjacent rooms
        // if they havnt been init AND are within bounds
        // a random number of them will be walls
        // otherwise they are notwalls - able to be moved into
        // then recurse
        int startX = (int)(Math.random()*xSize);
        int startY = (int)(Math.random()*ySize);
        System.out.println("about to gen room");
        generateRoom(new Location(startX, startY));
    }

    private void generateRoom(Location loc){
        ArrayList<Location> adjRoomList = new ArrayList<Location>();

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
            roomMap.put(loc,new Room(this, loc, false));
        } else {
            roomMap.get(loc).isWall=false;
        }
    }

    private void createWall(Location loc){
        roomMap.putIfAbsent(loc, new Room(this, loc, true));
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
