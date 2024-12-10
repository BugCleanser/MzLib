package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.LivingEntity"))
public interface EntityLiving extends Entity
{
    @WrapperCreator
    static EntityLiving create(Object wrapped)
    {
        return WrapperObject.create(EntityLiving.class, wrapped);
    }
}
