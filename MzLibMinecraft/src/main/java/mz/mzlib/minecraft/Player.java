package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.util.Option;

import java.util.Objects;
import java.util.UUID;

/**
 * A persistent representation of a player.
 * unlike EntityPlayer which can vary when the player is offline or dies on some servers.
 */
public class Player
{
    public UUID uuid;
    protected Player(UUID uuid)
    {
        this.uuid = uuid;
    }

    public static Player of(UUID uuid)
    {
        return new Player(uuid);
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public Option<EntityPlayer> getEntity()
    {
        return MinecraftServer.instance.getPlayerManager().getPlayer(this.uuid);
    }

    public boolean isOnline()
    {
        return this.getEntity().isSome();
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
        Player player = (Player) obj;
        return Objects.equals(this.uuid, player.uuid);
    }
}
