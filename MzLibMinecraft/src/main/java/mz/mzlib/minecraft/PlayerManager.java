package mz.mzlib.minecraft;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.ListWrapper;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.WrapperCreator;

import java.util.List;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.PlayerManager"))
public interface PlayerManager extends WrapperObject
{
    @SuppressWarnings("deprecation")
    @WrapperCreator
    static PlayerManager create(Object wrapped)
    {
        return WrapperObject.create(PlayerManager.class, wrapped);
    }

    @WrapMinecraftMethod({@VersionName(name="getPlayers",end=1400),@VersionName(name="getPlayerList",begin = 1400)})
    List<?> getPlayers0();
    default List<EntityPlayer> getPlayers()
    {
        return new ListWrapper<>(this.getPlayers0(),EntityPlayer::create);
    }
}
