package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import processing.core.PImage;

import java.awt.*;

/**
 * Created by Klimpen on 22/04/2017.
 */
@AllArgsConstructor
public class Sticker implements GameObject {
    @Getter
    private int lifetime;
    private PImage img;
    private int x, y;

    @Override
    public void update() {

    }

    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        engine.tint(255, 255-lifetime*5);
        engine.image(img, x-img.width/2, y-img.height/2);
        lifetime++;
        engine.noTint();
    }
}
