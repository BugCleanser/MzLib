package mz.mzlib.minecraft.entity.damage;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.registry.entry.RegistryEntryV1802;
import mz.mzlib.minecraft.util.math.Vec3d;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.*;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.damage.DamageSource"))
public interface DamageSource extends WrapperObject
{
    WrapperFactory<DamageSource> FACTORY = WrapperFactory.of(DamageSource.class);
    @Deprecated
    @WrapperCreator
    static DamageSource create(Object wrapped)
    {
        return WrapperObject.create(DamageSource.class, wrapped);
    }

    @VersionRange(begin = 1904)
    @WrapConstructor
    DamageSource static$newInstanceV1904(RegistryEntryV1802 type, Entity source, Entity attacker, Vec3d location);
    static DamageSource newInstanceV1904(RegistryEntryV1802 type, Entity source, Entity attacker, Vec3d location)
    {
        return FACTORY.getStatic().static$newInstanceV1904(type, source, attacker, location);
    }

    Entity getSource();
    @SpecificImpl("getSource")
    @VersionRange(end=1904)
    default Entity getSourceV_1904()
    {
        if(this.isInstanceOf(DamageSourceEntityV_1904.FACTORY))
            return this.castTo(DamageSourceEntityV_1904.FACTORY).getSource();
        else
            return Entity.FACTORY.create(null); // TODO Option
    }
    @SpecificImpl("getSource")
    @WrapMinecraftFieldAccessor(@VersionName(name = "source", begin = 1904))
    Entity getSourceV1904();

    Entity getAttacker();
    @SpecificImpl("getAttacker")
    @VersionRange(end=1904)
    default Entity getAttackerV_1904()
    {
        if(this.isInstanceOf(DamageSourceProjectileV_1904.FACTORY))
            return this.castTo(DamageSourceProjectileV_1904.FACTORY).getAttacker();
        else
            return Entity.FACTORY.create(null);
    }
    @SpecificImpl("getAttacker")
    @WrapMinecraftFieldAccessor(@VersionName(name = "attacker", begin = 1904))
    Entity getAttackerV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "type", begin = 1904))
    RegistryEntryV1802 getTypeV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "type", begin = 1904))
    void setTypeV1904(RegistryEntryV1802 value);


    @WrapMinecraftFieldAccessor(@VersionName(name = "source", begin = 1904))
    void setSourceV1904(Entity value);


    @WrapMinecraftFieldAccessor(@VersionName(name = "attacker", begin = 1904))
    void setAttackerV1904(Entity value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "position", begin = 1904))
    Vec3d getLocationV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "position", begin = 1904))
    void setLocationV1904(Vec3d value);
}
