package mz.mzlib.demo.games.chinesechess.rules;

import mz.mzlib.demo.games.chinesechess.Position;

import java.util.List;

@FunctionalInterface
public interface ChessRule {
    List<Position> generateSelectables(Chess chess);
}
