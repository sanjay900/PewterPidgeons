package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum Direction {
    NORTH(0,1),SOUTH(0,-1),EAST(1,0),WEST(-1,0);
    private int x,y;
}
