package mz.mzlib.minecraft.entity.damage;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.damage.EntityDamageSource", end = 1904))
public interface DamageSourceEntityV_1904 extends WrapperObject
{
    WrapperFactory<DamageSourceEntityV_1904> FACTORY = WrapperFactory.of(DamageSourceEntityV_1904.class);
    @WrapMinecraftFieldAccessor(@VersionName(name = "source"))
    Entity getSource();
    @WrapMinecraftFieldAccessor(@VersionName(name = "source"))
    void setSource(Entity value);
}

