package mz.mzlib.mc;

import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.mc.delegator.DelegatorMinecraftMethod;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

import java.util.List;

@DelegatorMinecraftClass(@VersionName(name="net.minecraft.server.PlayerManager"))
public interface PlayerManager extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static PlayerManager create(Object delegate)
    {
        return Delegator.create(PlayerManager.class, delegate);
    }

    @DelegatorMinecraftMethod({@VersionName(name="getPlayers",end=1400),@VersionName(name = "getPlayerList",begin = 1400)})
    List<?> getPlayerList0();
}
