package net.pp.testengine;

import net.tangentmc.model.MD2.Animation;
import net.tangentmc.model.MD2.ClippableImporter;
import net.tangentmc.model.MD2.MD2ClippableModel;
import processing.core.PApplet;

import java.io.IOException;

public enum Models {
    SCORPION("scorpion.md2","scorpion.png"),STAIR("stairs.md2","step_texture.png"),MINE("mine.md2","mine.png"),HEALTH("slime.md2","slime.png"),
    POW("powSlime.md2","powSlime.png"),SPEED("idkSlime.md2","idkSlime.png");
    public Animation animation;

    Models(String modelName, String imageName) {
        this.modelName = modelName;
        this.imageName = imageName;
    }
    Models(String modelName, String imageName, boolean outline) {
        this(modelName,imageName);
        this.outline = outline;
    }
    boolean outline = false;
    String modelName;
    MD2ClippableModel model;
    String imageName;
    void load(PApplet applet) {
        try {
            model = new ClippableImporter().importModel(applet.dataFile(modelName),applet.loadImage(imageName),applet);
            if (this == SCORPION) {
                model.setAnimation( animation =new Animation(160,1,0.75f,15),2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
