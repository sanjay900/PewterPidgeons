package net.pp.testengine;

import lombok.Getter;
import processing.core.PVector;

import java.awt.*;
@Getter
public class Projectile implements GameObject {
    int id;
    PVector motion;
    PVector position;
    boolean isRemote = false;
    boolean dead = false;

    public Projectile(PVector start, PVector end) {
        this.motion = PVector.sub(end,start).normalize().mult(15);
        this.position = start.add(motion.copy().mult(4));
        id = (int) (Math.random()*Integer.MAX_VALUE);
    }

    public Projectile(int id, PVector motion, PVector position) {
        this.motion = motion;
        this.position = position;
        this.id = id;
        isRemote = true;
    }
    @Override
    public void update() {
        if (dead) return;
        position.add(motion);
    }

    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        if (dead) return;
        if (engine.manager.isWall(new Location(position))) {
            dead = true;
            return;
        }
        for (Player player : engine.playerMap.values()) {
            if (player.collides(this)) {
                player.hit();
                dead = true;
                return;
            }
        }
        engine.pushMatrix();
        engine.translate(position.x,position.y,position.z);
        engine.fill(255);
        engine.sphere(2);
        engine.popMatrix();
    }

    public void updateWith(PVector mot, PVector pos) {
        this.motion = mot;
        this.position = pos;
    }
}
