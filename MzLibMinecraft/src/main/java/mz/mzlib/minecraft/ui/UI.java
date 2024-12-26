package mz.mzlib.minecraft.ui;

import mz.mzlib.minecraft.entity.player.EntityPlayer;

public interface UI
{
    void open(EntityPlayer player);
    default void close(EntityPlayer player)
    {
        player.closeInterface();
    }
    default void onPlayerClose(EntityPlayer player)
    {
        UIStack.get(player).clear();
    }
}
