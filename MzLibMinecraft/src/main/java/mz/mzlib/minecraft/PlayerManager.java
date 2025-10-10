package mz.mzlib.minecraft;

import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ClientConnectionDataV2002;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.Option;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.List;
import java.util.UUID;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.PlayerManager"))
public interface PlayerManager extends WrapperObject
{
    WrapperFactory<PlayerManager> FACTORY = WrapperFactory.of(PlayerManager.class);
    @Deprecated
    @WrapperCreator
    static PlayerManager create(Object wrapped)
    {
        return WrapperObject.create(PlayerManager.class, wrapped);
    }
    
    @WrapMinecraftMethod({@VersionName(name="getPlayers", end=1400), @VersionName(name="getPlayerList", begin=1400)})
    List<Object> getPlayers0();
    
    default List<EntityPlayer> getPlayers()
    {
        return new ListProxy<>(this.getPlayers0(), InvertibleFunction.wrapper(EntityPlayer.FACTORY));
    }
    
    @WrapMinecraftMethod(@VersionName(name="getPlayer"))
    EntityPlayer getPlayer(String name);
    
    @VersionRange(end=2002)
    @WrapMinecraftMethod({@VersionName(name="onPlayerConnect", end=900), @VersionName(name="method_12827", begin=900, end=1400), @VersionName(name="onPlayerConnect", begin=1400)})
    void addPlayerV_2002(ClientConnection connection, EntityPlayer player);
    
    @VersionRange(begin=2002)
    @WrapMinecraftMethod(@VersionName(name="onPlayerConnect"))
    void addPlayerV2002(ClientConnection connection, EntityPlayer player, ClientConnectionDataV2002 connectionData);
    
    @WrapMinecraftMethod(@VersionName(name="isOperator"))
    boolean isOp(GameProfile playerProfile);
    
    @WrapMinecraftMethod(@VersionName(name="getPlayer"))
    EntityPlayer getPlayer0(UUID uuid);
    
    default Option<EntityPlayer> getPlayer(UUID uuid)
    {
        return Option.fromWrapper(this.getPlayer0(uuid));
    }
}
