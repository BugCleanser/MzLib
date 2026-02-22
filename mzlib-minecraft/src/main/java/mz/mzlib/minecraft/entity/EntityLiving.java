package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.LivingEntity"))
public interface EntityLiving extends Entity
{
    WrapperFactory<EntityLiving> FACTORY = WrapperFactory.of(EntityLiving.class);
    }

