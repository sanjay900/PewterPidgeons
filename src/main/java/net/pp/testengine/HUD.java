package net.pp.testengine;

import lombok.AllArgsConstructor;
import processing.core.PImage;

@AllArgsConstructor
public class HUD {
    TestEngine engine;
    PImage[] hearts = new PImage[4];
    PImage mine2d;
    public HUD(TestEngine engine) {
        this.engine = engine;
        hearts[0] = engine.loadImage("0-hearts.png");
        hearts[1] = engine.loadImage("1-heart.png");
        hearts[2] = engine.loadImage("2-hearts.png");
        hearts[3] = engine.loadImage("3-hearts.png");
        mine2d = engine.loadImage("mine.2d.png");
    }
    public void render(Player p) {
        engine.fill(0);
        engine.image(hearts[p.health],20,0);
        engine.textSize(40);
        engine.text(p.bullets+"/8",20,engine.height-20);
        engine.image(mine2d,60,engine.height-170,150,150);

        engine.text("x"+p.mines,180,engine.height-20);
    }
}
