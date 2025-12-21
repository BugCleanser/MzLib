package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.tester.TesterContext;

public class TesterContextPlayer extends TesterContext
{
    EntityPlayer player;
    public TesterContextPlayer(int level, EntityPlayer player)
    {
        super(level);
        this.player = player;
    }

    public EntityPlayer getPlayer()
    {
        return this.player;
    }
}
