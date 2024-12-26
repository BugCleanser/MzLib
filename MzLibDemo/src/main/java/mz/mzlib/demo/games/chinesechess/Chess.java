package mz.mzlib.demo.games.chinesechess;

import java.util.List;

public interface Chess {
    Position getPosition();
    List<Position> getSelectables();
    ChessRule getChessRule();
}
