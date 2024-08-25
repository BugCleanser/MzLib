package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.event.entity.EntityEvent;

public abstract class PlayerEvent extends EntityEvent
{
    public PlayerEvent(EntityPlayer player)
    {
        super(player);
    }

    @Override
    public EntityPlayer getEntity()
    {
        return (EntityPlayer) super.getEntity();
    }
}
