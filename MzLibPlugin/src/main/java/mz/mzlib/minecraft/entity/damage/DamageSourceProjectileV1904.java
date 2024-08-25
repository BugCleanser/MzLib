package mz.mzlib.minecraft.entity.damage;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.damage.ProjectileDamageSource", end=1904))
public interface DamageSourceProjectileV1904 extends DamageSourceEntityV_1904
{
    @WrapperCreator
    static DamageSourceProjectileV1904 create(Object wrapped)
    {
        return WrapperObject.create(DamageSourceProjectileV1904.class, wrapped);
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="attacker"))
    Entity getAttacker();
    @WrapMinecraftFieldAccessor(@VersionName(name="attacker"))
    void setAttacker(Entity value);
}
