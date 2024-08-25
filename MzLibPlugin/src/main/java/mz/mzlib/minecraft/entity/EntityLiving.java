package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.LivingEntity"))
public interface EntityLiving extends Entity
{
}
