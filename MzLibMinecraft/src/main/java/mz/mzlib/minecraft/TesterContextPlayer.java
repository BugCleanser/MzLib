package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.tester.TesterContext;

public class TesterContextPlayer extends TesterContext
{
    public EntityPlayer player;
    public TesterContextPlayer(int level, EntityPlayer player)
    {
        super(level);
        this.player=player;
    }
}
