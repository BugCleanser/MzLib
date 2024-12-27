package mz.mzlib.demo.game.chinesechess.chesses;

import mz.mzlib.demo.game.chinesechess.Position2D;

public interface Chess2D extends Chess {
    @Override
    Position2D getPosition();

    Position2D getPreposition();

    void setPreposition(Position2D position2D);

    void setPosition(Position2D position2D);
}
