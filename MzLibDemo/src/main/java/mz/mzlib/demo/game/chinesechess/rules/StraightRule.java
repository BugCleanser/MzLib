package mz.mzlib.demo.game.chinesechess.rules;

import mz.mzlib.demo.game.chinesechess.chesses.Chess2D;
import mz.mzlib.demo.game.chinesechess.other.Position2D;

import java.util.ArrayList;
import java.util.List;

public class StraightRule implements ChessRule2D {
    protected int[] slots = new int[2];

    public StraightRule(int x,int y){
        this.slots[0] = x;
        this.slots[1] = y;
    }

    @Override
    public List<Position2D> generateSelectables(Chess2D chess) {
        List<Position2D> position2Ds = new ArrayList<>();
        for(int var1=-slots[0];var1<slots[0];var1++){
            position2Ds.add(chess.getPosition().clone().add(var1,0));
        }
        for(int var1=-slots[1];var1<slots[1];var1++){
            position2Ds.add(chess.getPosition().clone().add(0,var1));
        }
        return position2Ds;
    }
}
