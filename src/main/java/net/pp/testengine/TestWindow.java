package net.pp.testengine;

import ecs100.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestWindow {
    private Point initialClick;
    private String hostname;
    public TestWindow(TestEngine engine) {
        this.engine = engine;
        UI.initialise();
        UI.setDivider(0);
        UI.setFontSize(20);
        UI.drawString("Welcome to "+TestEngine.GAME_NAME+" Game!",260,20);
        UI.drawString("<-- Click the buttons to the side to join / start a game!",150,40);
        UI.drawString("Then this window can be used to interact with the game.",150,60);
        UI.getFrame().setAlwaysOnTop(true);
        UI.addButton("Start Server",this::startServer);
        UI.addTextField("Hostname",name -> hostname = name);
        UI.addButton("Join Server",this::join);
    }

    private void startServer() {
        new Networking(engine).connect(true,"");
        startGame();
    }


    private void join() {
        new Networking(engine).connect(false,hostname);
        startGame();
    }
    void doHit() {
        for (int i = 0; i < 3; i++) {
            UI.getFrame().setLocation(UI.getFrame().getLocation().x+50, UI.getFrame().getLocation().y+50);
            UI.sleep(100);
            UI.getFrame().setLocation(UI.getFrame().getLocation().x-50, UI.getFrame().getLocation().y-50);
            UI.sleep(100);
        }
    }
    private void startGame() {
        engine.bootProcessing();
        UI.addButton("test",()->{}).getParent().removeAll();
        JMenuBar bar = UI.getFrame().getJMenuBar();
        bar.remove(0);
        //Create a JMenuItem that looks like a title bar since we have to get rid of the existing one.
        JMenuItem title=new JMenuItem("<html><p style='text-align:center;width:310px'>The lens of !false</p></html>");
        title.setBackground(Color.BLACK);
        title.setForeground(Color.WHITE);
        title.setFont(new Font(title.getFont().getName(),Font.PLAIN,20));
        title.addMouseListener(adapter);
        title.addMouseMotionListener(adapter1);
        bar.add(title);
        //Hacks to destroy the canvas so we can undecorate it.
        UI.getFrame().dispose();
        UI.getFrame().setUndecorated(true);
        //Now that this is done, show it again
        UI.getFrame().setVisible(true);
        ((JComponent)UI.theUI.canvas).addKeyListener(ada);
        UI.getFrame().addKeyListener(ada);
        UI.getFrame().addMouseListener(adapter);
        UI.getFrame().addMouseMotionListener(adapter1);
        bar.addMouseListener(adapter);
        bar.addMouseMotionListener(adapter);
        UI.clearGraphics();
        UI.getGraphics().setColor(new Color(0,51,255));
        UI.getGraphics().fillRect(0,0,1000,1000);
        //Fiddle with the opacity now that it is undecorated
        UI.getFrame().setOpacity(0.5f);
        UI.getFrame().setSize(new Dimension(400,400));
        UI.getFrame().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                engine.input.reset();
            }
        });
    }

    private MouseMotionAdapter adapter1 = new MouseMotionAdapter() {
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
    };
    MouseAdapter adapter = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            initialClick = e.getPoint();
        }
    };
    private TestEngine engine;
    private KeyAdapter ada = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!UI.getFrame().isFocused()) return;
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                engine.exit();
                return;
            }
            engine.key = e.getKeyChar();
            engine.keyPressed();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (!UI.getFrame().isFocused()) return;
            engine.key = e.getKeyChar();
            engine.keyReleased();
        }
    };
}
