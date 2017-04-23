package net.pp.testengine;

import processing.core.PConstants;

import java.awt.*;

public class Mine extends Collectible {
    boolean activsted = false;
    public Mine(Location location) {
        super(location);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        if (activsted) return;
        engine.pushMatrix();
        engine.translate(0,Room.ROOM_SIZE/2,0);
        engine.rotateX(PConstants.HALF_PI);
        engine.scale(2);
        Models.MINE.model.drawModel(blueBounds,engine);
        engine.popMatrix();
    }

    @Override
    void onCollect(TestEngine engine,Player pl) {
        if (activsted) return;
        pl.hit(engine);
        activsted = true;
    }
}
