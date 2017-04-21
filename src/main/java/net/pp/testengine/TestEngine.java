package net.pp.testengine;

import net.tangentmc.processing.ProcessingRunner;
import processing.core.PApplet;
import processing.core.PVector;

public class TestEngine extends PApplet {
    public static void main(String[] args) {
        ProcessingRunner.run(new TestEngine());
    }
    public void settings() {
        size(800, 600, P3D);
    }
    public void setup() {

    }
    public void draw() {

    }
    PVector movement = new PVector(0,0,0);
    public void keyPressed() {
        switch (key) {
            case 'w': movement.y -= 10; break;
            case 's': movement.y += 10; break;
            case 'a': movement.x -= 10; break;
            case 'd': movement.x += 10; break;
        }
    }
    public void keyReleased() {
        switch (key) {
            case 'w': movement.y += 10; break;
            case 's': movement.y -= 10; break;
            case 'a': movement.x += 10; break;
            case 'd': movement.x -= 10; break;
        }
    }
}
