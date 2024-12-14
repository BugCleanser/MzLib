package mz.mzlib.minecraft.ui;

import mz.mzlib.minecraft.entity.player.EntityPlayer;

public interface UI
{
    void open(EntityPlayer player);
    void close(EntityPlayer player);
    default void onPlayerClose(EntityPlayer player)
    {
        UIStack.get(player).clear();
    }
}
