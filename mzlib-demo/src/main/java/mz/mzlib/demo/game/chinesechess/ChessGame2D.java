package mz.mzlib.demo.game.chinesechess;

import mz.mzlib.demo.game.chinesechess.chesses.Chess2D;
import mz.mzlib.demo.game.chinesechess.other.Position2D;

//TODO
public class ChessGame2D extends ChessGame
{
    protected String name;

    public ChessGame2D(String name)
    {
        super(name);
    }

    public void layChess(Chess2D chess)
    {
        chess.setPosition(chess.getPreposition());
        //TODO
    }

    public void pluckChess(Chess2D chess)
    {

    }

    public void moveChess(Chess2D chess, Position2D position)
    {
        pluckChess(chess);
        chess.setPreposition(position);
        layChess(chess);
    }
}
