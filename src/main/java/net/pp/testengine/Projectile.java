package net.pp.testengine;

import processing.core.PVector;

import java.awt.*;

public class Projectile implements GameObject {
    PVector motion;
    PVector position;

    public Projectile(PVector start, PVector end) {
        this.motion = PVector.sub(end,start).normalize().mult(10);
        this.position = start.add(motion.copy().mult(4));
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
        engine.sphere(2);
        engine.popMatrix();
    }
}
