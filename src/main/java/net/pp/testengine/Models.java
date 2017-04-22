package net.pp.testengine;

import net.tangentmc.model.MD2.Animation;
import net.tangentmc.model.MD2.Importer;
import net.tangentmc.model.MD2.MD2Model;
import processing.core.PApplet;
import processing.core.PConstants;

import java.io.IOException;

public enum Models {
    SCORPION("scorpion.md2","scorpion.png"),STAIR("stairs.md2","stairs.png"),MINO("minotaur.md2","minotaur.png");
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
    MD2Model model;
    String imageName;
    void load(PApplet applet) {
        try {
            model = new Importer().importModel(applet.dataFile(modelName),applet.loadImage(imageName),applet);
            if (this == MINO) {
              //  model.setAnimation( new Animation(100,1,0.75f,1),2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void render(TestEngine engine, float height, float scale) {
        if(!outline) engine.noStroke();
        engine.translate(0, -height+Room.ROOM_SIZE/2,0);
        engine.rotateX(PConstants.HALF_PI);
        engine.scale(scale);
        model.drawModel();
        if(!outline) engine.stroke(0);
    }
}
