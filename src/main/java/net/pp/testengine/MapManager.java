package net.pp.testengine;

import lombok.Getter;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 * Creates a room[][] roomArray
 * render calls are piped through here
 */
public class MapManager {
    @Getter
    private Room[][] roomArray = new Room[][]{};
    private int xSize;
    private int ySize;

    public MapManager(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        createMap();
    }

    private void createMap(){

    }

    private void draw(){
        for(int i=0; i<xSize; i++){
            for(int j=0; j<ySize; j++){
                roomArray[i][j].draw();
            }
        }
    }


}
