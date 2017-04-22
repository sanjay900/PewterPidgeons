package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.ToString;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 */
@ToString
@AllArgsConstructor
public class Room implements GameObject{
    private MapManager manager;
    Location position;
    public static final int ROOM_SIZE = 100;
    public boolean isWall;
    List<GameObject> entities = new ArrayList<>();
    public boolean isStair;


    public Room(MapManager manager, Location loc, boolean isWall) {
        this.manager = manager;
        this.position = loc;
        this.isWall = isWall;
    }

    public void randomize() {

//        en = new HiddenEntity(300,300,25);
    }
    @Override
    public void update() {
        entities.forEach(GameObject::update);
    }

    @Override
    public void render(TestEngine engine, Rectangle bounds) {
        engine.pushMatrix();
        engine.translate(position.getX()*Room.ROOM_SIZE,(-position.getZ()*(Room.ROOM_SIZE+0.01f)),position.getY()*Room.ROOM_SIZE);
        if (isWall) {
            engine.fill(128, 128, 128);
            engine.box(ROOM_SIZE);
        } else if (isStair) {
            Models.STAIR.render(engine,0,5);
        } else {
            engine.translate(-Room.ROOM_SIZE/2,0,-Room.ROOM_SIZE/2);
            engine.fill(255, 255, 255);
            engine.translate(0,Room.ROOM_SIZE/2,0);
            engine.beginShape(PConstants.QUAD);
            if (!manager.isStair(position.getRelative(Direction.DOWN))) {
                engine.vertex(0,0,0);
                engine.vertex(0,0,ROOM_SIZE);
                engine.vertex(ROOM_SIZE,0,ROOM_SIZE);
                engine.vertex(ROOM_SIZE, 0, 0);
            }
            engine.vertex(0, -ROOM_SIZE, 0);
            engine.vertex(0, -ROOM_SIZE, ROOM_SIZE);
            engine.vertex(ROOM_SIZE, -ROOM_SIZE, ROOM_SIZE);
            engine.vertex(ROOM_SIZE, -ROOM_SIZE, 0);
            engine.endShape();
        }
        engine.popMatrix();
        entities.forEach(o -> o.render(engine, bounds));
    }

    public Color renderOffscreen(TestEngine engine, PGraphics offscreen) {
        if (!isWall && !isStair) return null;
        offscreen.pushMatrix();
        offscreen.translate(position.getX()*Room.ROOM_SIZE,(-position.getZ()*Room.ROOM_SIZE),position.getY()*Room.ROOM_SIZE);
        Color c = new Color((int)engine.random(255),(int)engine.random(255),(int)engine.random(255));
        offscreen.fill(c.getRed(),c.getGreen(), c.getBlue());
        offscreen.box(ROOM_SIZE);
        offscreen.popMatrix();
        return c;
    }
}
