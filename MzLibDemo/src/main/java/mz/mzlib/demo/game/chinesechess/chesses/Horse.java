package mz.mzlib.demo.game.chinesechess.chesses;

import mz.mzlib.demo.game.chinesechess.ChessGame;
import mz.mzlib.demo.game.chinesechess.other.Position2D;
import mz.mzlib.demo.game.chinesechess.rules.AroundJumpRule;
import mz.mzlib.demo.game.chinesechess.rules.ChessRule;

import java.util.Arrays;
import java.util.List;

public class Horse extends BaseChess2D {
    protected Position2D position = null;
    protected ChessGame game = null;
    protected List<ChessRule> chessRules = Arrays.asList(new AroundJumpRule(1,2));

    public Horse(Position2D position){
        super(position);
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
    public Position2D getPosition() {
        return position;
    }

    @Override
    public Position2D getPreposition() {
        return preposition;
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
