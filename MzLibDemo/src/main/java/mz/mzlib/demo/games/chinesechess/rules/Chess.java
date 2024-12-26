package mz.mzlib.demo.games.chinesechess.rules;

import mz.mzlib.demo.games.chinesechess.ChessGame;
import mz.mzlib.demo.games.chinesechess.Position;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Chess {
    Position getPosition();
    default List<Position> getSelectables(){
        if(getGame()==null){
            return null;
        }
        return getChessRule().stream().map(a->a.generateSelectables(this)).flatMap(Collection::stream).collect(Collectors.toList());
    }
    List<ChessRule> getChessRule();
    ChessGame getGame();
}
