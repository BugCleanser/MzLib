package mz.mzlib.minecraft.entity.damage;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.damage.ProjectileDamageSource", end = 1904))
public interface DamageSourceProjectileV_1904 extends WrapperObject
{
    WrapperFactory<DamageSourceProjectileV_1904> FACTORY = WrapperFactory.of(DamageSourceProjectileV_1904.class);
    @WrapMinecraftFieldAccessor(@VersionName(name = "attacker"))
    Entity getAttacker();
    @WrapMinecraftFieldAccessor(@VersionName(name = "attacker"))
    void setAttacker(Entity value);
}

