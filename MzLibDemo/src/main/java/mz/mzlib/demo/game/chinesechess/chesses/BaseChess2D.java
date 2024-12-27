package mz.mzlib.demo.game.chinesechess.chesses;

import mz.mzlib.demo.game.chinesechess.ChessGame2D;
import mz.mzlib.demo.game.chinesechess.other.Position2D;
import mz.mzlib.demo.game.chinesechess.rules.ChessRule;

import java.util.List;

public abstract class BaseChess2D implements Chess2D{
    protected Position2D position = null;
    protected Position2D preposition = null;
    protected ChessGame2D game = null;

    public BaseChess2D(Position2D position){
        this.position = position;
        this.preposition = position;
    }

    public boolean summon(ChessGame2D game){
        game.layChess(this);
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract List<ChessRule> getChessRule();

    public void setPosition(Position2D position) {
        this.position = position;
    }

    public void setPreposition(Position2D preposition) {
        this.preposition = preposition;
    }
}
