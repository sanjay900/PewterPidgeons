package net.pp.testengine;


import com.jogamp.newt.opengl.GLWindow;
import ecs100.UI;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PGraphicsOpenGL;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TestEngine extends PApplet {
    MapManager manager;
    ArrayList<Player> playerList = new ArrayList<>();
    Player player;
    MiniMap miniMap;
    KeyInput input = new KeyInput();
    TestWindow window;
    PGraphics offscrean;
    public static final String GAME_NAME = "%insert_title%";
    public Room selected;
    ArrayList<Sticker> stickerList;

    public static void main(String[] args) {
        ProcessingRunner.run(new TestEngine());
    }
    public TestEngine() {
        window = new TestWindow(this);
    }
    public void settings() {
        size(800, 600, P3D);
        randomSeed(31);
    }

    public void setup() {
        frameRate(144);
        offscrean = createGraphics(width,height, P3D);
        manager = new MapManager(this,30,30,2,offscrean);
        player = new Player(manager.getStartLoc());
        playerList.add(player);
        playerList.add(new Player(new Location(0,0,0)));
        stickerList = new ArrayList<>();
        miniMap = new MiniMap(this, width-100, height-100);
        Arrays.stream(Models.values()).forEach(m -> m.load(this));
        ((GLWindow)getSurface().getNative()).setTitle(GAME_NAME);
    }
    public void draw() {
        System.out.println(frameRate);
        pushMatrix();
        hint(PConstants.ENABLE_DEPTH_TEST);
        Rectangle blueBounds = findBounds();
        player.move(input.getMotion(),manager);
        clear();
        player.render(this,blueBounds);
        manager.render(this,blueBounds);

        popMatrix();
        hint(PConstants.DISABLE_DEPTH_TEST);

        miniMap.render(player);
        stickerList.removeIf(sticker -> sticker.getLifetime()>50);
        stickerList.forEach(s -> s.render(this));
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
    @Override
    public void mouseClicked(){
        manager.offscreenCheck(this);
        stickerList.add(new Sticker(0, loadImage("sticker001.png"), mouseX, mouseY));
    }
}

