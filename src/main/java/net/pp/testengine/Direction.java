package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum Direction {
    NORTH(0,1,0),SOUTH(0,-1,0),EAST(1,0,0),WEST(-1,0,0),UP(0,0,1),DOWN(0,0,-1);
    private int x,y,z;
}
