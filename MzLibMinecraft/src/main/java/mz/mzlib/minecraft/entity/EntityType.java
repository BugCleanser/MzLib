package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.EntityType")) // TODO
public interface EntityType extends WrapperObject
{
    @WrapperCreator
    static EntityType create(Object wrapped)
    {
        return WrapperObject.create(EntityType.class, wrapped);
    }

    static EntityType fromId(String id)
    {
        return fromId(Identifier.newInstance(id));
    }
    static EntityType fromId(Identifier id)
    {
        return registry().get(id).castTo(EntityType::create);
    }
    
    default Identifier getIdV1300()
    {
        return registry().getIdV1300(this);
    }
    
    static Registry registry()
    {
        return create(null).staticRegistry();
    }
    Registry staticRegistry();
    @VersionRange(end=1903)
    @SpecificImpl("staticRegistry")
    default Registry staticRegistryV_1903()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    @VersionRange(begin=1903)
    @SpecificImpl("staticRegistry")
    default Registry staticRegistryV1903()
    {
        return RegistriesV1903.entityType();
    }
}
