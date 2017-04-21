package net.pp.testengine;

import processing.core.PApplet;
import processing.core.PVector;

import static processing.core.PApplet.radians;

public class Player implements GameObject{
    public Player(Location start) {
        camPos = new PVector(-start.getX(),-start.getY()).mult(Room.ROOM_SIZE);
    }
    // variables for Dom's cameraw
    private PVector camPos = new PVector();  //  (x, y) means (right, forward) in worldspace.
    private float camRot = 0.0f;  // turns the camera anti-clockwise radians as if viewed from above (worldspace). 0 means walking forwards increases camPos.y. 90 means walking forwards increases camPos.x.
    private float moveAmount = .2f;
    private float rotSpeed = 1.2f;
    @Override
    public void update() {

    }
    public void move(PVector movement, MapManager manager) {
        PVector lastLoc = camPos.copy();
        movement = movement.copy();
        camRot+=movement.x/1000;
        movement.x = 0;
        PVector mvmt = movement.rotate(-camRot).mult(moveAmount);
        this.camPos.add(mvmt).add(mvmt);
        if (manager.isWall(getLocation())) {
            camPos = lastLoc;
        } else {
            camPos.sub(mvmt);
        }
    }
    public Location getLocation() {
        PVector realPos = PVector.div(camPos,Room.ROOM_SIZE);
        return new Location(-Math.round(realPos.x), -Math.round(realPos.y));
    }
    @Override
    public void render(TestEngine engine) {
//        float deltaX = engine.mouseX-engine.width/2;
//        deltaX = PApplet.map(deltaX,0,engine.width,0,rotSpeed);
//        camRot += -deltaX;  // calculate camera rotation. moving mouse to the right we expect clockwise rotation.

        // set up the camera (by doing reverse transformations)
        engine.resetMatrix();
        engine.perspective(radians(60), (float)engine.width/(float)engine.height, 1.0f, 10000.0f);   // note: you were seeing z-clipping before.
        engine.rotateY(-camRot);  // reversed as it rotates world objects counter-clockwise
        engine.translate(camPos.x, 0, camPos.y);
    }
}
