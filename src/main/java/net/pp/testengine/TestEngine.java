package net.pp.testengine;

import com.jogamp.newt.opengl.GLWindow;
import ecs100.UI;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PMatrix;

import javax.swing.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class TestEngine extends PApplet {
    MapManager manager;
    ArrayList<Player> playerList = new ArrayList<>();
    Player player;
    MiniMap miniMap;
    KeyInput input = new KeyInput();
    TestWindow window;
    public static final String GAME_NAME = "%insert_title%";

    public static void main(String[] args) {
        ProcessingRunner.run(new TestEngine());
    }
    public TestEngine() {
        window = new TestWindow(this);
    }
    public void settings() {
        size(800, 600, P3D);
        randomSeed(31);
        manager = new MapManager(this,20,20,2);
    }

    public void setup() {
        frameRate(144);
        player = new Player(manager.getStartLoc());
        playerList.add(player);
        playerList.add(new Player(new Location(0,0,0)));
        miniMap = new MiniMap(this, width-100, height-100);
        Arrays.stream(Models.values()).forEach(m -> m.load(this));
        ((GLWindow)getSurface().getNative()).setTitle(GAME_NAME);
    }
    public void draw() {
        pushMatrix();
        hint(PConstants.ENABLE_DEPTH_TEST);
        Rectangle blueBounds = findBounds();
        player.move(input.getMotion(),manager);
        clear();
        player.render(this,blueBounds);
        manager.render(this,blueBounds);
        pushMatrix();
        translate(200,0,200);
        Models.MINO.render(this,3,10);
        popMatrix();
        popMatrix();
        hint(PConstants.DISABLE_DEPTH_TEST);

        miniMap.render(player);
        //PVector cam = new PVector(-player.getCamPos().x,player.getCamPos().z,-player.getCamPos().y);
        //PVector mouse  = matrix.mult(new PVector(mouseX-width/2,mouseY-height/2),null);
        //mouse.y = cam.y+PApplet.map(mouseY,0,height,0,50);
        //translate(mouse.x, mouse.y-100,mouse.z);
        //fill(255);
        //box(100);

        //System.out.println("mouse: " + mouse);
        //System.out.println("camera: " + cam);
    }

    /**
     * Figure out the intersection between the game window and the blue window.
     * Returns a Rectangle with the bounds.
     * @return a size, or null if no intersection
     */
    private Rectangle findBounds() {
        GLWindow window = (GLWindow) getSurface().getNative();
        com.jogamp.nativewindow.util.Rectangle glbb = window.getBounds();
        JComponent comp = UI.theUI.canvas;
        Point pt = comp.getLocationOnScreen();
        Rectangle awtbb = new Rectangle(pt.x,pt.y,comp.getWidth(),comp.getHeight());
        int x5 = (int) Math.max(glbb.getX(), awtbb.getX());
        int y5 = (int) Math.max(glbb.getY(), awtbb.getY());
        int x6 = (int) Math.min(glbb.getX()+glbb.getWidth(), awtbb.getX()+awtbb.getWidth());
        int y6 = (int) Math.min(glbb.getY()+glbb.getHeight(), awtbb.getY()+awtbb.getHeight());
        x5 -= glbb.getX();
        y5 -= glbb.getY();
        x6 -= glbb.getX();
        y6 -= glbb.getY();
        if (x5 > x6 || y5 > y6) return null;
        return new Rectangle(x5,y5,x6-x5,y6-y5);
    }

    @Override
    public void keyPressed() {
        input.keyPressed(key);
    }
    @Override
    public void keyReleased() {
        input.keyReleased(key);
    }
}

