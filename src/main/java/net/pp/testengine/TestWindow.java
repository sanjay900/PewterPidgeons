package net.pp.testengine;

import ecs100.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestWindow {
    private Point initialClick;
    public TestWindow(TestEngine engine) {
        UI.initialise();
        //Hacks to destroy the canvas so we can undecorate it.
        UI.getFrame().dispose();
        UI.getFrame().setUndecorated(true);
        //Now that this is done, show it again
        UI.getFrame().setVisible(true);
        //Fiddle with the opacity now that it is undecorated
        UI.getFrame().setOpacity(0.5f);
        UI.getGraphics().setColor(new Color(0,51,255));
        UI.getGraphics().setBackground(new Color(0,51,255));
        UI.getGraphics().fillRect(0,0,1000,1000);
        UI.getFrame().setAlwaysOnTop(true);
        KeyAdapter ada = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                engine.key = e.getKeyChar();
                engine.keyPressed();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                engine.key = e.getKeyChar();
                engine.keyReleased();
            }
        };
        ((JComponent)UI.theUI.canvas).addKeyListener(ada);
        UI.getFrame().addKeyListener(ada);
        UI.getFrame().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        UI.getFrame().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = UI.getFrame().getLocation().x;
                int thisY = UI.getFrame().getLocation().y;
                // Determine how much the mouse moved since the initial click
                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                UI.getFrame().setLocation(X, Y);
            }
        });
    }
}
