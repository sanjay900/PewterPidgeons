package net.pp.testengine;

import lombok.Getter;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 *
 * Creates a room[][] roomArray
 * render calls are piped through here
 */
public class MapManager implements GameObject{
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
        int startX = (int)(Math.random()*xSize);
        int startY = (int)(Math.random()*ySize);
    }

    @Override
    public void update() {
        for(int i=0; i<xSize; i++){
            for(int j=0; j<ySize; j++){
                roomArray[i][j].update();
            }
        }
    }

    public void render(TestEngine engine){
        for(int i=0; i<xSize; i++){
            for(int j=0; j<ySize; j++){
                roomArray[i][j].render(engine);
            }
        }
    }


}
