package mz.mzlib.minecraft.ui;

import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;

public interface UI
{
    void open(AbstractEntityPlayer player);
    void close(AbstractEntityPlayer player);
    default void onPlayerClose(AbstractEntityPlayer player)
    {
        UIStack.get(player).clear();
    }
}
