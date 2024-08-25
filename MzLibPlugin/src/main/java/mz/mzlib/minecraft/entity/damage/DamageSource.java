package mz.mzlib.minecraft.entity.damage;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.Vector;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.Entity;
import mz.mzlib.minecraft.registry.entry.RegistryEntryV1802;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.entity.damage.DamageSource"))
public interface DamageSource extends WrapperObject
{
    @WrapperCreator
    static DamageSource create(Object wrapped)
    {
        return WrapperObject.create(DamageSource.class, wrapped);
    }

    @VersionRange(begin = 1904)
    @WrapConstructor
    DamageSource staticNewInstanceV1904(RegistryEntryV1802 type, Entity source, Entity attacker, Vector location);
    static DamageSource newInstanceV1904(RegistryEntryV1802 type, Entity source, Entity attacker, Vector location)
    {
        return create(null).staticNewInstanceV1904(type, source, attacker, location);
    }

    default Entity getSource()
    {
        if(MinecraftPlatform.instance.getVersion()<1904)
        {
            if(this.isInstanceOf(DamageSourceEntityV_1904::create))
                return this.castTo(DamageSourceEntityV_1904::create).getSource();
            else
                return Entity.create(null);
        }
        else
            return this.getSourceV1904();
    }
    default Entity getAttacker()
    {
        if(MinecraftPlatform.instance.getVersion()<1904)
        {
            if(this.isInstanceOf(DamageSourceProjectileV1904::create))
                return this.castTo(DamageSourceProjectileV1904::create).getAttacker();
            else
                return Entity.create(null);
        }
        else
            return this.getAttackerV1904();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "type", begin = 1904))
    RegistryEntryV1802 getTypeV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "type", begin = 1904))
    void setTypeV1904(RegistryEntryV1802 value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "source", begin = 1904))
    Entity getSourceV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "source", begin = 1904))
    void setSourceV1904(Entity value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "attacker", begin = 1904))
    Entity getAttackerV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "attacker", begin = 1904))
    void setAttackerV1904(Entity value);

    @WrapMinecraftFieldAccessor(@VersionName(name = "position", begin = 1904))
    Vector getLocationV1904();

    @WrapMinecraftFieldAccessor(@VersionName(name = "position", begin = 1904))
    void setLocationV1904(Vector value);
}
