package mz.mzlib.demo.game.chinesechess;

import java.util.Objects;

public class Position2D implements Position{
    protected int x;
    protected int y;

    public Position2D(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Position2D add(int x,int y){
        this.x=this.x+x;
        this.y=this.y+y;
        return this;
    }

    @Override
    public Position2D clone() {
        try {
            Position2D clone = (Position2D) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position2D that = (Position2D) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
