package net.pp.testengine;

import ecs100.Sound;

import java.io.File;
import java.io.IOException;

/**
 * Created by Thomas on 23/04/2017.
 */
public class MusicManager {
    private Sound mainTrack;
    private Sound fightTrack;
    //private Sound track3;
    //private Sound track4;

    public MusicManager() {
        try {
            mainTrack = new Sound(new File("bgTrack3.wav"));
            fightTrack = new Sound(new File("bgTrack1.wav"));
            /*track3 = new Sound(new File("bgTrack2.wav"));
            track4 = new Sound(new File("bgTrack4.wav"));*/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Sound getMainTrack() {
        return mainTrack;
    }

    public Sound getFightTrack() {
        return fightTrack;
    }
}
