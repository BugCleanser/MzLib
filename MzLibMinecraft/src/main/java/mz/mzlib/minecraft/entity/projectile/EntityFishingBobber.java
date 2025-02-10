package mz.mzlib.minecraft.entity.projectile;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.projectile.FishingBobberEntity"))
public interface EntityFishingBobber extends WrapperObject, Entity
{
    @WrapperCreator
    static EntityFishingBobber create(Object wrapped)
    {
        return WrapperObject.create(EntityFishingBobber.class, wrapped);
    }
}
