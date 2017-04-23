package net.pp.testengine;

import ecs100.Sound;
import java.io.IOException;

/**
 * Created by Thomas on 23/04/2017.
 */
public class MusicManager {
    private Sound mainTrack;
    private Sound fightTrack;
    private Sound bangSound;
    private Sound pewSound;
    private Sound screechSound;
    //private Sound track3;
    //private Sound track4;

    public MusicManager(TestEngine engine) {
        try {
            mainTrack = new Sound(engine.dataFile("bgTrack3.wav"));
            fightTrack = new Sound(engine.dataFile("bgTrack1.wav"));
            /*track3 = new Sound(new File("bgTrack2.wav"));
            track4 = new Sound(new File("bgTrack4.wav"));*/
            bangSound = new Sound((engine.dataFile(("gameSounds/bang.wav"))));
            pewSound = new Sound((engine.dataFile(("gameSounds/pew.wav"))));
            screechSound = new Sound((engine.dataFile(("gameSounds/screech.wav"))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Sound getBangSound() { return bangSound; }

    public Sound getMainTrack() {return mainTrack; }

    public Sound getPewSound() { return pewSound; }

    public Sound getScreechSound() { return screechSound; }

    public Sound getFightTrack() {
        return fightTrack;
    }
}
