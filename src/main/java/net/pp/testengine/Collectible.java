package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public abstract class Collectible implements GameObject {
    @Getter
    Location position;
    abstract void onCollect(TestEngine engine, Player pl);
}
