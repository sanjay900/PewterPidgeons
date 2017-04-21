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
    private Room[][] roomArray;
    private int xSize;
    private int ySize;

    public MapManager(int xSize, int ySize){
        this.xSize = xSize;
        this.ySize = ySize;
        roomArray = new Room[xSize][ySize];
        createMap();
    }

    private void createMap(){
        for(int i=0; i<xSize; i++){
            for(int j=0; j<ySize; j++){
                roomArray[i][j] = new Room();
            }
        }
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
        for(int x=0; x<xSize; x++){
            for(int y=0; y<ySize; y++){
                engine.pushMatrix();
                engine.translate(x*Room.ROOM_SIZE,y*Room.ROOM_SIZE);
                roomArray[x][y].render(engine);
                engine.popMatrix();
            }
        }
    }


}
