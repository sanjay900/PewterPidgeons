package net.pp.testengine;

import processing.core.PConstants;

import java.awt.*;

public class Powerup extends Collectible {
    private boolean taken = false;
    private float rotZ = 0.0f;
    int type;
    public Powerup(Location position, int type) {
        super(position);
        this.type = type;
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
        engine.rotateZ(rotZ);
        switch (type) {
            case 1:
                Models.POW.model.drawModel(blueBounds,engine);
                break;
            case 2:
                Models.HEALTH.model.drawModel(blueBounds,engine);
                break;
            case 3:
                Models.SPEED.model.drawModel(blueBounds,engine);
                break;
        }

        engine.popMatrix();
        rotZ+=0.01;
    }

    @Override
    void onCollect(TestEngine engine, Player pl) {
        if (taken) return;
        pl.applyPowerup(type);
        taken = true;
        engine.manager.collectibles.remove(this);
        engine.musicManager.getPopSound().play();
    }
}
