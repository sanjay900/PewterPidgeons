package net.pp.testengine;

import lombok.AllArgsConstructor;
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
        Random random = new Random();
        engine.fill(0, 0, 255);
        if (isWall)
        engine.box(ROOM_SIZE);
        engine.popMatrix();
    }
}
