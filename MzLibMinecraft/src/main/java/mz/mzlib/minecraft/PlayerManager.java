package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ClientConnectionDataV2002;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.ListWrapper;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.PlayerManager"))
public interface PlayerManager extends WrapperObject
{
    @WrapperCreator
    static PlayerManager create(Object wrapped)
    {
        return WrapperObject.create(PlayerManager.class, wrapped);
    }
    
    @WrapMinecraftMethod({@VersionName(name="getPlayers", end=1400), @VersionName(name="getPlayerList", begin=1400)})
    List<?> getPlayers0();
    
    default List<EntityPlayer> getPlayers()
    {
        return new ListWrapper<>(this.getPlayers0(), EntityPlayer::create);
    }
    
    @WrapMinecraftMethod(@VersionName(name="getPlayer"))
    EntityPlayer getPlayer(String name);
    
    // TODO: versioning
    @VersionRange(end=2002)
    @WrapMinecraftMethod(@VersionName(name="onPlayerConnect"))
    void addPlayerV_2002(ClientConnection connection, EntityPlayer player);
    
    @VersionRange(begin=2002)
    @WrapMinecraftMethod(@VersionName(name="onPlayerConnect"))
    void addPlayerV2002(ClientConnection connection, EntityPlayer player, ClientConnectionDataV2002 connectionData);
}
