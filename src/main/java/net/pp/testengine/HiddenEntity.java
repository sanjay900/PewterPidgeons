package net.pp.testengine;

import lombok.AllArgsConstructor;
import processing.core.PVector;

import java.awt.*;


@AllArgsConstructor
public class HiddenEntity implements GameObject {
    float x,y;
    int size ;
    @Override
    public void update() {

    }
    private PVector[] getVertices() {
        return new PVector[]{
                new PVector(0,0,0),
                new PVector(0,0,size),
                new PVector(0,size,size),
                new PVector(size,size,size),
                new PVector(size,size,0),
                new PVector(size,0,0),
                new PVector(size,0,size),
                new PVector(0,size,0),
        };
    }
    @Override
    public void render(TestEngine engine, Rectangle blueBounds) {
        engine.pushMatrix();
        engine.translate(x,0,y);

        if (blueBounds != null) {
            int minX = Integer.MAX_VALUE,minY = Integer.MAX_VALUE,maxX = -Integer.MAX_VALUE, maxY = -Integer.MAX_VALUE;
            for (PVector vector : getVertices()) {
                int x = (int) engine.screenX(vector.x-size/2,vector.y-size/2,vector.z-size/2);
                int y = (int) engine.screenY(vector.x-size/2,vector.y-size/2,vector.z-size/2);
                minX = Math.min(x,minX);
                maxX = Math.max(x,maxX);
                minY = Math.min(y,minY);
                maxY = Math.max(y,maxY);

            }
            Rectangle drawnShape = new Rectangle(minX,minY,maxX-minX,maxY-minY);
            if (blueBounds.contains(drawnShape)) {
                engine.box(size);
            }
        }
        engine.popMatrix();
    }
}
