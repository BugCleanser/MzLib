package mz.mzlib.demo.game.chinesechess.rules;

import mz.mzlib.demo.game.chinesechess.chesses.Chess2D;
import mz.mzlib.demo.games.chinesechess.other.Position2D;

import java.util.List;

@FunctionalInterface
public interface ChessRule2D {
    List<Position2D> generateSelectables(Chess2D chess);
}
