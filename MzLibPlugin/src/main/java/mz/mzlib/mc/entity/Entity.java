package mz.mzlib.mc.entity;

import mz.mzlib.mc.VersionName;
import mz.mzlib.mc.delegator.DelegatorMinecraftClass;
import mz.mzlib.util.delegator.Delegator;
import mz.mzlib.util.delegator.DelegatorCreator;

@DelegatorMinecraftClass(@VersionName(name = "net.minecraft.entity.Entity"))
public interface Entity extends Delegator
{
    @SuppressWarnings("deprecation")
    @DelegatorCreator
    static Entity create(Object delegate)
    {
        return Delegator.create(Entity.class, delegate);
    }
}
