package mz.mzlib.minecraft.entity;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.RegistriesV1300;
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
        return getRegistry().get(id).castTo(EntityType::create);
    }
    
    default Identifier getIdV1300()
    {
        return getRegistry().getIdV1300(this);
    }
    
    static Registry getRegistry()
    {
        return create(null).staticGetRegistry();
    }
    
    Registry staticGetRegistry();
    
    @VersionRange(end=1300)
    @SpecificImpl("staticRegistry")
    default Registry staticRegistryV_1300()
    {
        // TODO
        throw new UnsupportedOperationException();
    }
    
    @VersionRange(begin=1300)
    @SpecificImpl("staticRegistry")
    default Registry staticRegistryV1300()
    {
        return RegistriesV1300.entityType();
    }
}
