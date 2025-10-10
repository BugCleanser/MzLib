package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Map;

@VersionRange(end=1300)
@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.EntityType"))
public interface EntityTypesV_1300 extends WrapperObject
{
    WrapperFactory<EntityTypesV_1300> FACTORY = WrapperFactory.of(EntityTypesV_1300.class);
    @Deprecated
    @WrapperCreator
    static EntityTypesV_1300 create(Object wrapped)
    {
        return WrapperObject.create(EntityTypesV_1300.class, wrapped);
    }
    
    static EntityType getByNameV_1100(String name)
    {
        return EntityType.create(create(null).staticMapName2ClassV_1100().get(name));
    }
    
    @VersionRange(end=1100)
    @WrapMinecraftFieldAccessor(@VersionName(name="NAME_CLASS_MAP"))
    Map<String, Class<?>> staticMapName2ClassV_1100();
    
    static Registry registryV1100()
    {
        return create(null).staticRegistryV1100();
    }
    
    @VersionRange(begin=1100)
    @WrapMinecraftFieldAccessor(@VersionName(name="REGISTRY"))
    Registry staticRegistryV1100();
}
