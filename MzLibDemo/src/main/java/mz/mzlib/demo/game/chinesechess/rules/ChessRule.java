package mz.mzlib.demo.game.chinesechess.rules;

import mz.mzlib.demo.game.chinesechess.chesses.Chess;
import mz.mzlib.demo.game.chinesechess.other.Position;

import java.util.List;

@FunctionalInterface
public interface ChessRule
{
    List<Position> generateSelectables(Chess chess);
}
