package net.pp.testengine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import processing.core.PVector;

@AllArgsConstructor
@Getter
@ToString

public class Location {
    private final int x,y,z;

    public Location(PVector position) {
        this.x = (int) (position.x/100);
        this.y = (int) (position.y/100);
        this.z = (int) (position.z/100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return x == location.x && y == location.y && z == location.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
    public Location getRelative(Direction dir) {
        return new Location(x+dir.getX(),y+dir.getY(),z+dir.getZ());
    }

    public float distance(Player player) {
        return new PVector(x,y,z).dist(new PVector(player.getLocation().x,player.getLocation().y,player.getLocation().z));
    }

    public PVector toVector() {
        return new PVector(x*Room.ROOM_SIZE,y*Room.ROOM_SIZE,z*Room.ROOM_SIZE);
    }

    public float distance(Location position) {
        return new PVector(x,y,z).dist(new PVector(position.x,position.y,position.z));
    }
}
