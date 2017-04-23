package net.pp.testengine;

import lombok.Getter;

public abstract class Collectible implements GameObject {
    @Getter
    Location position;
}
