package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;

import java.util.Objects;
import java.util.UUID;

// TODO from EntityPlayer
/**
 * A persistent representation of a player.
 * unlike EntityPlayer which can vary when the player is offline or dies on some servers.
 */
public class Player
{
    public UUID uuid;
    public Player(UUID uuid)
    {
        this.uuid = uuid;
    }
    
    public boolean isOnline()
    {
        return false; // TODO
    }
    
    public EntityPlayer getEntity()
    {
        return null; // TODO
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.uuid);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Player))
            return false;
        Player player = (Player)obj;
        return Objects.equals(this.uuid, player.uuid);
    }
}
