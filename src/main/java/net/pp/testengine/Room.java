package net.pp.testengine;

import lombok.AllArgsConstructor;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.Random;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 */
@AllArgsConstructor
public class Room implements GameObject{
    private MapManager manager;
    Location position;
    public static final int ROOM_SIZE = 100;
    public boolean isWall;

    @Override
    public void update() {

    }

    @Override
    public void render(TestEngine engine) {
        engine.pushMatrix();
        engine.translate(position.getX()*Room.ROOM_SIZE,0,position.getY()*Room.ROOM_SIZE);
        if (isWall) {
            engine.fill(128, 128, 128);
            engine.box(ROOM_SIZE);
        } else {
            engine.translate(-Room.ROOM_SIZE/2,0,-Room.ROOM_SIZE/2);
            engine.fill(255, 255, 255);
            engine.translate(0,Room.ROOM_SIZE/2,0);
            engine.beginShape(PConstants.QUAD);
            engine.vertex(0,0,0);
            engine.vertex(0,0,ROOM_SIZE);
            engine.vertex(ROOM_SIZE,0,ROOM_SIZE);
            engine.vertex(ROOM_SIZE,0,0);
            engine.vertex(0,-ROOM_SIZE,0);
            engine.vertex(0,-ROOM_SIZE,ROOM_SIZE);
            engine.vertex(ROOM_SIZE,-ROOM_SIZE,ROOM_SIZE);
            engine.vertex(ROOM_SIZE,-ROOM_SIZE,0);
            engine.endShape();
        }
        engine.popMatrix();
    }
}
