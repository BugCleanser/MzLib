package mz.mzlib.demo.games.chinesechess;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public class Horse implements Chess {
    protected Position2D position = null;
    protected ChessGame game = null;
    protected ChessRule chessRule = new AroundJumpRule(1,2);

    public Horse(Position2D position){
        this.position = position;
    }

    public boolean summon(ChessGame game){
        game.layChess(this);
        //TODO
        throw new UnsupportedOperationException();
    }


    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public List<Position> getSelectables() {
        return chessRule.generateSelectables(this);
    }

    @Override
    public ChessRule getChessRule() {
        return chessRule;
    }
}
