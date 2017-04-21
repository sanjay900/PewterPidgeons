package net.pp.testengine;

import net.tangentmc.model.MD2.Importer;
import net.tangentmc.model.MD2.MD2Model;
import processing.core.PApplet;
import processing.core.PConstants;

import java.io.IOException;

public enum Models {
    SCORPION("scorpion.md2","scorpion.png");
    Models(String modelName, String imageName) {
        this.modelName = modelName;
        this.imageName = imageName;
    }
    String modelName;
    MD2Model model;
    String imageName;
    void load(PApplet applet) {
        try {
            model = new Importer().importModel(applet.dataFile(modelName),applet.loadImage(imageName),applet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(TestEngine engine) {
        engine.noStroke();
        engine.translate(0, -10+Room.ROOM_SIZE/2,0);
        engine.rotateX(PConstants.HALF_PI);
        engine.scale(3);
        model.drawModel();
        engine.stroke(0);
    }
}
