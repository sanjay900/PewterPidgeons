package net.pp.testengine;

import lombok.Getter;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

public class KeyInput {
    @Getter
    private PVector motion = new PVector();
    private Set<Character> pressed = new HashSet<>();
    public void keyPressed(char key) {
        if (pressed.contains(key)) return;
        pressed.add(Character.toLowerCase(key));
        switch (Character.toLowerCase(key)) {
            case 'w':
                motion.y+=10;
                break;
            case 's':
                motion.y-=10;
                break;
            case 'a':
                motion.x+=10;
                break;
            case 'd':
                motion.x-=10;
                break;
        }
    }

    public void keyReleased(char key) {
        pressed.remove(Character.toLowerCase(key));
        switch (Character.toLowerCase(key)) {
            case 'w':
                motion.y-=10;
                break;
            case 's':
                motion.y+=10;
                break;
            case 'a':
                motion.x-=10;
                break;
            case 'd':
                motion.x+=10;
                break;

        }
    }

    public void reset() {
        motion.x = 0;
        motion.y = 0;
    }
}
