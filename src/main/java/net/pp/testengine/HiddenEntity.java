package net.pp.testengine;

import com.jogamp.nativewindow.util.Rectangle;
import com.jogamp.newt.opengl.GLWindow;
import ecs100.UI;
import lombok.AllArgsConstructor;
import processing.core.PConstants;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PVector;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


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
    public void render(TestEngine engine) {
        engine.pushMatrix();
        engine.translate(x,0,y);
        GLWindow window = (GLWindow) engine.getSurface().getNative();
        Rectangle glbb = window.getBounds();
        JComponent comp = UI.theUI.canvas;
        Point pt = comp.getLocationOnScreen();
        java.awt.Rectangle awtbb = new java.awt.Rectangle(pt.x,pt.y,comp.getWidth(),comp.getHeight());
        int x5 = (int) Math.max(glbb.getX(), awtbb.getX());
        int y5 = (int) Math.max(glbb.getY(), awtbb.getY());
        int x6 = (int) Math.min(glbb.getX()+glbb.getWidth(), awtbb.getX()+awtbb.getWidth());
        int y6 = (int) Math.min(glbb.getY()+glbb.getHeight(), awtbb.getY()+awtbb.getHeight());
        x5 -= glbb.getX();
        y5 -= glbb.getY();
        x6 -= glbb.getX();
        y6 -= glbb.getY();
        if (x5 < x6 &&  y5 < y6) {
            int minX = Integer.MAX_VALUE,minY = Integer.MAX_VALUE,maxX = -Integer.MAX_VALUE, maxY = -Integer.MAX_VALUE;
            for (PVector vector : getVertices()) {
                int x = (int) engine.screenX(vector.x-size/2,vector.y-size/2,vector.z-size/2);
                int y = (int) engine.screenY(vector.x-size/2,vector.y-size/2,vector.z-size/2);
                minX = Math.min(x,minX);
                maxX = Math.max(x,maxX);
                minY = Math.min(y,minY);
                maxY = Math.max(y,maxY);

            }
            java.awt.Rectangle bounds = new java.awt.Rectangle(x5,y5,x6-x5,y6-y5);
            java.awt.Rectangle drawnShape = new java.awt.Rectangle(minX,minY,maxX-minX,maxY-minY);
            if (bounds.contains(drawnShape)) {
                engine.box(size);
            }
        }
        engine.popMatrix();
    }
}
