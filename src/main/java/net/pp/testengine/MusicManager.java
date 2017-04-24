package net.pp.testengine;

import ecs100.Sound;
import java.io.IOException;

/**
 * Created by Thomas on 23/04/2017.
 */
public class MusicManager {
    private Sound fightTrack;
    private Sound bangSound;
    private Sound pewSound;
    private Sound screechSound;
    private Sound popSound;

    public MusicManager(TestEngine engine) {
        try {
            fightTrack = new Sound(engine.dataFile("bgTrack1.wav")); // Sampled and modified "Crisis in the North," composed by Senju, Akira
            bangSound = new Sound((engine.dataFile(("gameSounds/bang.wav"))));
            pewSound = new Sound((engine.dataFile(("gameSounds/pew.wav"))));
            screechSound = new Sound((engine.dataFile(("gameSounds/screech.wav"))));
            popSound = new Sound((engine.dataFile(("gameSounds/pop.wav"))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Sound getBangSound() { return bangSound; }

    public Sound getPewSound() { return pewSound; }

    public Sound getScreechSound() { return screechSound; }

    public Sound getFightTrack() {
        return fightTrack;
    }

    public Sound getPopSound() { return popSound; }
}
