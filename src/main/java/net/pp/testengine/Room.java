package net.pp.testengine;

import java.util.Random;

/**
 * Created by Klimpen on 21/04/2017.
 * TODO everything
 */
public class Room implements GameObject{
    public static final int ROOM_SIZE = 100;
    @Override
    public void update() {

    }

    @Override
    public void render(TestEngine engine) {
        Random random = new Random();
        engine.fill(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        engine.box(ROOM_SIZE);
    }
}
