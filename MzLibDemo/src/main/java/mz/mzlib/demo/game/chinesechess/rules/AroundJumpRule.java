package mz.mzlib.demo.game.chinesechess.rules;

import mz.mzlib.demo.game.chinesechess.Position;
import mz.mzlib.demo.game.chinesechess.chesses.Chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AroundJumpRule implements ChessRule {
    protected int[] slots = new int[2];

    public AroundJumpRule(int x, int y){
        this.slots[0] = x;
        this.slots[1] = y;
    }

    @Override
    public List<Position> generateSelectables(Chess chess) {
        Set<Position> positions = new HashSet<>();
        for(int var1=-slots.length;var1<slots.length;var1++){
            int var2= var1*(var1/Math.abs(var1));
            for(int var3=-slots.length;var3<slots.length;var3++){
                int var4= var3*(var3/Math.abs(var3));
                positions.add(chess.getPosition().add(var2,var4));
            }
        }
        return new ArrayList<>(positions);
    }
}
