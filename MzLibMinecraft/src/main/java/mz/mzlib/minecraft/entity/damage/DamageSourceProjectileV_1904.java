package mz.mzlib.minecraft.entity.damage;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.damage.ProjectileDamageSource", end=1904))
public interface DamageSourceProjectileV_1904 extends WrapperObject
{
    WrapperFactory<DamageSourceProjectileV_1904> FACTORY = WrapperFactory.find(DamageSourceProjectileV_1904.class);
    @Deprecated
    @WrapperCreator
    static DamageSourceProjectileV_1904 create(Object wrapped)
    {
        return WrapperObject.create(DamageSourceProjectileV_1904.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="attacker"))
    Entity getAttacker();
    @WrapMinecraftFieldAccessor(@VersionName(name="attacker"))
    void setAttacker(Entity value);
}
