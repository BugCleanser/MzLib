package mz.mzlib.minecraft;

import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.incomprehensible.PlayerConfigEntryV2109;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ClientConnectionDataV2002;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.InvertibleFunction;
import mz.mzlib.util.Option;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
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
    @VersionRange(end=2109)
    boolean isOpV_2109(GameProfile playerProfile);
    @WrapMinecraftMethod(@VersionName(name="isOperator"))
    @VersionRange(begin=2109)
    boolean isOpV2109(PlayerConfigEntryV2109 key);
    
    boolean isOp(EntityPlayer player);
    @SpecificImpl("isOp")
    @VersionRange(end=2109)
    default boolean isOpV_2109(EntityPlayer player)
    {
        return this.isOpV_2109(player.getGameProfile());
    }
    @SpecificImpl("isOp")
    @VersionRange(begin=2109)
    default boolean isOpV2109(EntityPlayer player)
    {
        return this.isOpV2109(player.getPlayerConfigEntryV2109());
    }
    
    @WrapMinecraftMethod(@VersionName(name="getPlayer"))
    EntityPlayer getPlayer0(UUID uuid);
    
    default Option<EntityPlayer> getPlayer(UUID uuid)
    {
        return Option.fromWrapper(this.getPlayer0(uuid));
    }
}
