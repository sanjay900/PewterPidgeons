package net.pp.testengine;

import com.jogamp.newt.opengl.GLWindow;
import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

import java.util.Arrays;

public class TestEngine extends PApplet {
    MapManager manager = new MapManager(30,30);
    Player player;
    KeyInput input = new KeyInput();
    TestWindow window;
    HiddenEntity en;
    public static final String GAME_NAME = "%insert_title%";
    public static void main(String[] args) {
        ProcessingRunner.run(new TestEngine());
    }
    public TestEngine() {
        window = new TestWindow(this);
    }
    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        frameRate(144);
        player = new Player(manager.getStartLoc());
        Arrays.stream(Models.values()).forEach(m -> m.load(this));
        en = new HiddenEntity(255,255,25);
        ((GLWindow)getSurface().getNative()).setTitle(GAME_NAME);
    }
    public void draw() {
        player.move(input.getMotion(),manager);
        clear();
        player.render(this);
        manager.render(this);
        pushMatrix();
        translate(200,0,200);
        Models.SCORPION.render(this);
        popMatrix();
        en.render(this);

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

