package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Location {
    private final int x,y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
    public Location add(int x, int y) {
        return new Location(this.x+x,this.y+y);
    }
    public Location getRelative(Direction dir) {
        return new Location(x+dir.getX(),y+dir.getY());
    }
}
