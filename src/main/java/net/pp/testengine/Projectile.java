package net.pp.testengine;

import processing.core.PVector;

import java.awt.*;

public class Projectile implements GameObject {
    PVector motion;
    PVector position;

    public Projectile(PVector start, PVector end) {
        this.position = start;
        this.motion = PVector.sub(end,start).normalize().mult(10);
    }

    @Override
    public void update() {
        position.add(motion);
    }

    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        engine.pushMatrix();
        engine.translate(position.x,position.y,position.z);
        engine.fill(255);
        engine.box(25);
        engine.popMatrix();
    }
}
