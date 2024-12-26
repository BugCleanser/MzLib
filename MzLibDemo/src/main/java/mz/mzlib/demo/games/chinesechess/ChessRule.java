package mz.mzlib.demo.games.chinesechess;

import java.util.List;

@FunctionalInterface
public interface ChessRule {
    List<Position> generateSelectables(Chess chess);
}
