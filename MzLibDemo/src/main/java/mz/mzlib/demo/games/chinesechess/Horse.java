package mz.mzlib.demo.games.chinesechess;

import mz.mzlib.demo.games.chinesechess.rules.AroundJumpRule;
import mz.mzlib.demo.games.chinesechess.rules.Chess;
import mz.mzlib.demo.games.chinesechess.rules.ChessRule;

import java.util.Arrays;
import java.util.List;

public class Horse implements Chess {
    protected Position2D position = null;
    protected ChessGame game = null;
    protected List<ChessRule> chessRules = Arrays.asList(new AroundJumpRule(1,2));

    public Horse(Position2D position){
        this.position = position;
    }

    public boolean summon(ChessGame game){
        game.layChess(this);
        //TODO
        throw new UnsupportedOperationException();
    }

    public boolean move(int x,int y){
        //TODO
        throw new UnsupportedOperationException();
    }

    public boolean move(){
        //TODO
        throw new UnsupportedOperationException();
    }


    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public List<ChessRule> getChessRule() {
        return chessRules;
    }

    @Override
    public ChessGame getGame() {
        return game;
    }
}
