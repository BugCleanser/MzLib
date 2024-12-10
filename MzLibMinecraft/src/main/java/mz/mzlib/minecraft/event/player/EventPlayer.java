package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.entity.EventEntity;

public abstract class EventPlayer extends EventEntity
{
    public EventPlayer(EntityPlayer player)
    {
        super(player);
    }

    @Override
    public EntityPlayer getEntity()
    {
        return (EntityPlayer) super.getEntity();
    }
}
