package net.pp.testengine;

import com.jogamp.newt.opengl.GLWindow;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.awt.*;

public class TestEngine extends PApplet {
    MapManager manager = new MapManager(30,30);
    Player player;
    KeyInput input = new KeyInput();
    public static void main(String[] args) {
        ProcessingRunner.run(new TestEngine());
    }
    public void settings() {
        size(800, 600, P3D);
    }
    public void setup() {
        player = new Player(manager.getStartLoc());
        noCursor();
        GLWindow window = (GLWindow) surface.getNative();
        window.confinePointer(true);
    }
    public void draw() {
        player.move(input.getMotion());
        GLWindow window = (GLWindow) surface.getNative();
        window.warpPointer(width/2,height/2);
        clear();
        player.render(this);
        manager.render(this);
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
