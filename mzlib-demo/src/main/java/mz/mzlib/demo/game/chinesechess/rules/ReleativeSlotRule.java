package mz.mzlib.demo.game.chinesechess.rules;

import mz.mzlib.demo.game.chinesechess.chesses.Chess;
import mz.mzlib.demo.game.chinesechess.other.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReleativeSlotRule implements ChessRule
{
    protected List<int[]> slots = new ArrayList<>();

    public ReleativeSlotRule(int[]... slots)
    {
        this.slots.addAll(Arrays.stream(slots).collect(Collectors.toList()));
    }

    @Override
    public List<Position> generateSelectables(Chess chess)
    {
        return slots.stream().map(a -> chess.getPosition().add(a[0], a[1])).collect(Collectors.toList());
    }
}
