package net.pp.testengine;

import lombok.Getter;
import lombok.Setter;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

import static processing.core.PApplet.radians;

public class Player implements GameObject {
    public int bullets = 4;

    public Player(Location start) {
        camPos = new PVector(-start.getX(), -start.getY()).mult(Room.ROOM_SIZE);
    }

    public Player(String nameIn, PVector startPos){
        playerName = nameIn;
        camPos = startPos;
        isLocal = false;
    }
    private boolean isLocal = true;
    private boolean isDead;
    @Getter
    int health = 3;
    // variables for Dom's cameraw
    @Getter
    private PVector camRotation = new PVector();
    @Getter
    @Setter
    private PVector camPos = new PVector();  //  (x, y) means (right, forward) in worldspace.
    @Getter
    @Setter
    private float camRot = 0.0f;  // turns the camera anti-clockwise radians as if viewed from above (worldspace). 0 means walking forwards increases camPos.y. 90 means walking forwards increases camPos.x.
    private float moveAmount = 0.5f;
    private float rotSpeed = 300;
    @Getter
    @Setter
    private String playerName = String.valueOf(Math.random()*Integer.MAX_VALUE);

    @Override
    public void update() {

    }
    public void move(TestEngine engine, PVector movement, MapManager manager) {
        PVector lastLoc = camPos.copy();
        Location lastLoca = getLocation();
        movement = movement.copy();
        //TODO: config powerup to liking
        camRot += movement.x / (300 - (hasSpeed ? 100 : 0));
        movement.x = 0;
        PVector mvmt = movement.rotate(-camRot).mult(moveAmount + (hasSpeed ? 0.3f : 0f));
        this.camPos.add(mvmt).add(mvmt);
//        System.out.println((getLocation().getZ()*100) + ((camPos.x-50) % 100) + 100);
        if (manager.isWall(getLocation())) {
            camPos = lastLoc;
        } else if (manager.isStair(getLocation())) {
            // camPos.z = (getLocation().getZ()*100) + (camPos.x % 100) + 100;
            if (manager.isStair(lastLoca) || lastLoca.getX() > getLocation().getX()) {
                camPos.z = (getLocation().getZ() * 100) + ((camPos.x - 50) % 100) + 100;
                return;
            } else {
                camPos = lastLoc;
            }

        } else if (manager.isStair(lastLoca) && ((camPos.z) % 100) > 60) {
            camPos.z = (getLocation().getZ() * 100) + 100;
        } else if (manager.isStair(getLocation().getRelative(Direction.DOWN))) {
            camPos.z = (getLocation().getZ() * 100) - 100;
            camPos.z = (getLocation().getZ() * 100) + ((camPos.x - 50) % 100) + 100;
        } else {
            camPos.sub(mvmt);
        }
        camPos.z = (getLocation().getZ() * 100);
        Collectible c = manager.getRoomMap().get(getLocation()).getCollectible();
        if (c != null) {
            c.onCollect(engine, this);
        }
    }

    public Location getLocation() {
        PVector realPos = PVector.div(camPos, Room.ROOM_SIZE);
        return new Location(-Math.round(realPos.x), -Math.round(realPos.y), (int) realPos.z);
    }
    boolean hasSpeed = false;
    int speedTicks = 0;
    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        if (engine.frameCount % engine.frameRate < 1) {
            if (bullets < 4)
                bullets++;
            if (hasSpeed)
                speedTicks++;
        }
        if (speedTicks==15) {
            hasSpeed = false;
            speedTicks = 0;
        }
//        float deltaX = engine.mouseX-engine.width/2;
//        deltaX = PApplet.map(deltaX,0,engine.width,0,rotSpeed);
//        camRot += -deltaX;  // calculate camera rotation. moving mouse to the right we expect clockwise rotation.
        if (isLocal) {
            // set up the camera (by doing reverse transformations)
            engine.resetMatrix();
            engine.perspective(radians(60), (float) engine.width / (float) engine.height, 1.0f, 10000.0f);   // note: you were seeing z-clipping before.
            engine.rotateY(-camRot);  // reversed as it rotates world objects counter-clockwise
            engine.translate(camPos.x, camPos.z-30, camPos.y);
            camRotation = new PVector();
            camRotation = engine.getMatrix().mult(camRotation,null);
        }
    }
    public void drawOtherPlayer(TestEngine engine, Rectangle blueBounds) {
        if (isLocal) return;
        if (blueBounds.width > 0){
            PVector test = camPos.copy();
            engine.pushMatrix();
            engine.translate(-test.x,-test.z+Room.ROOM_SIZE/2,-test.y);
            engine.rotateX(PConstants.HALF_PI);
            engine.rotateZ(-camRot-PConstants.HALF_PI);  // reversed as it rotates world objects counter-clockwise
            engine.scale(5);
            Models.SCORPION.model.drawModel(blueBounds,engine);
            engine.popMatrix();
        }
    }
    public PVector getRelative(Location l) {
        return new PVector(
                this.getLocation().getX() - l.getX(),
                this.getLocation().getY() - l.getY());
    }

    public boolean collides(TestEngine engine, Projectile projectile) {
        if(projectile.getPosition().dist(new PVector(-camPos.x,-camPos.z+Room.ROOM_SIZE/2,-camPos.y)) < 25){
            engine.musicManager.getBangSound().stop();
            engine.musicManager.getBangSound().play();
        } else {
            engine.musicManager.getPewSound().stop();
            engine.musicManager.getPewSound().play();
        }
        return projectile.getPosition().dist(new PVector(-camPos.x,-camPos.z+Room.ROOM_SIZE/2,-camPos.y)) < 25;
    }
    boolean hit = false;
    public void hit(TestEngine engine) {
        if (!hit) {
            health--;
        }
        if (health <= 0) {
            isDead = true;
            engine.musicManager.getScreechSound().play();
        }
        this.hit = true;
    }

    public void applyPowerup(int type) {
        switch (type) {
            case 1:
                bullets+=4;
                break;
            case 2:
                health = 3;
                break;
            case 3:
                hasSpeed = true;
                break;
        }
    }

    public boolean isDead() {
        return isDead;
    }
}
