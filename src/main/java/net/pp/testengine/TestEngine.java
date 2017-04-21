package net.pp.testengine;

import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;

public class TestEngine extends PApplet {
    MapManager manager = new MapManager(10,10);
    public static void main(String[] args) {
        ProcessingRunner.run(new TestEngine());
    }
    public void settings() {
        size(800, 600, P3D);
    }
    public void setup() {

    }
    public void draw() {

        manager.render(this);
    }
}
