package net.pp.testengine;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 * Creates a room[][] roomArray
 * render calls are piped through here
 */
public class MapManager {
    Room[][] roomArray = new Room[][]{};

    public MapManager(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
    }
}
