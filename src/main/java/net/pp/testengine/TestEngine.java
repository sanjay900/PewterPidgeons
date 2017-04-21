package net.pp.testengine;

import com.jogamp.newt.opengl.GLWindow;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

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
        frameRate(144); // for added cinematic
        player = new Player(manager.getStartLoc());
        noCursor();
        GLWindow window = (GLWindow) surface.getNative();
        window.confinePointer(true);
    }
    public void draw() {
        player.move(input.getMotion(),manager);
        GLWindow window = (GLWindow) surface.getNative();
        if (window.hasFocus())
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

