package mz.mzlib.demo.game.chinesechess.rules;

import mz.mzlib.demo.game.chinesechess.Position;

import java.util.*;
import java.util.stream.Collectors;

public class ReleativeSlotRule implements ChessRule {
    protected List<int[]> slots = new ArrayList<>();

    public ReleativeSlotRule(int[]... slots){
        this.slots.addAll(Arrays.stream(slots).collect(Collectors.toList()));
    }

    @Override
    public List<Position> generateSelectables(Chess chess) {
        return slots.stream().map(a->chess.getPosition().add(a[0],a[1])).collect(Collectors.toList());
    }
}
