package net.pp.testengine;


import com.jogamp.newt.opengl.GLWindow;
import ecs100.UI;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.opengl.PGraphics3D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class TestEngine extends PApplet {

    MapManager manager;
    ConcurrentHashMap<String, Player> playerMap;
    ConcurrentHashMap<Integer, Projectile> projectileMap;
    Player player = new Player("",new PVector(0,0,0));
    MiniMap miniMap;
    KeyInput input = new KeyInput();
    TestWindow window;
    public static final String GAME_NAME = "%insert_title%";
    ArrayList<Sticker> stickerList;
    ScreenCoord2WorldCoord s = new ScreenCoord2WorldCoord();
    MusicManager musicManager;

    public static void main(String[] args) {
        new TestEngine();
    }
    public TestEngine() {
        playerMap = new ConcurrentHashMap<>();
        projectileMap = new ConcurrentHashMap<>();
        window = new TestWindow(this);

    }
    public void bootProcessing() {
        ProcessingRunner.run(this);
    }
    public void settings() {
        size(800, 600, P3D);
    }
    public void setSeed(long seed) {
        randomSeed(seed);
        readyForSeed = true;
    }
    public void setup() {
        frameRate(60);
        musicManager = new MusicManager(this);
        musicManager.getMainTrack().play();
        hud = new HUD(this);
    }
    boolean seedSet = false;
    boolean readyForSeed = false;
    HUD hud;
    public void draw() {
        if (!readyForSeed) {
            hint(PConstants.DISABLE_DEPTH_TEST);
            return;
        }
        if (!seedSet) {
            Arrays.stream(Models.values()).forEach(m -> m.load(this));
            ((GLWindow)getSurface().getNative()).setTitle(GAME_NAME);
            manager = new MapManager(this,30,30,2);
            player = new Player(manager.getRandomStartLocation());
            playerMap.put(player.getPlayerName(), player);
            stickerList = new ArrayList<>();
            miniMap = new MiniMap(this);
            seedSet = true;
            hud = new HUD(this);
        }
        noStroke();
        pushMatrix();
        hint(PConstants.ENABLE_DEPTH_TEST);
        Rectangle blueBounds = findBounds();
        player.move(input.getMotion(),manager);
        projectileMap.values().forEach(Projectile::update);
        clear();
        player.render(this,blueBounds);
        playerMap.values().forEach(p -> p.drawOtherPlayer(this,blueBounds));
        s.captureViewMatrix((PGraphics3D) this.g);
        manager.render(this,blueBounds);
        new ArrayList<>(projectileMap.values()).forEach(p -> p.render(this,blueBounds));
        popMatrix();
        hint(PConstants.DISABLE_DEPTH_TEST);
        miniMap.render(player);
        hud.render(player);

//        stickerList.removeIf(sticker -> sticker.getLifetime()>50);
//        stickerList.forEach(s -> s.render(this));
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
        //This is why UI makes me want to kill myself every night.
        Rectangle awtbb = new Rectangle(pt.x,pt.y,365,347);
        int x5 = (int) Math.max(glbb.getX(), awtbb.getX());
        int y5 = (int) Math.max(glbb.getY(), awtbb.getY());
        int x6 = (int) Math.min(glbb.getX()+glbb.getWidth(), awtbb.getX()+awtbb.getWidth());
        int y6 = (int) Math.min(glbb.getY()+glbb.getHeight(), awtbb.getY()+awtbb.getHeight());
        x5 -= glbb.getX();
        y5 -= glbb.getY();
        x6 -= glbb.getX();
        y6 -= glbb.getY();
        if (x5 > x6 || y5 > y6) return new Rectangle(-Integer.MAX_VALUE,-Integer.MAX_VALUE,0,0);
        return new Rectangle(x5,y5,x6-x5,y6-y5);
    }

    @Override
    public void keyPressed() {
        if (!focused) return;
        input.keyPressed(key);
    }
    @Override
    public void keyReleased() {
        if (!focused) return;
        input.keyReleased(key);
    }
    @Override
    public void mouseClicked(){
        stickerList.add(new Sticker(0, loadImage("sticker001.png"), mouseX, mouseY));
        if (player.bullets <= 0) return;
        s.calculatePickPoints(mouseX,height-mouseY);
        Projectile f = new Projectile(s.ptStartPos.copy(),s.ptEndPos.copy());
        projectileMap.put(f.getId(),f);
        player.bullets--;
    }
}

