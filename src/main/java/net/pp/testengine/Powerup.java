package net.pp.testengine;

import processing.core.PConstants;

import java.awt.*;

public class Powerup implements GameObject {
    private final Location position;
    private boolean taken = false;

    public Powerup(Location position) {
        this.position = position;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        if (taken) return;
        engine.pushMatrix();
        engine.translate(0,Room.ROOM_SIZE/2,0);
        engine.rotateX(PConstants.HALF_PI);
        engine.scale(2);
        Models.MINE.model.drawModel(null,engine);
        engine.popMatrix();
    }
}
