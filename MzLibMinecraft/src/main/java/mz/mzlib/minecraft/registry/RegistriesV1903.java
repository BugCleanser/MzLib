package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.registry.Registries",begin=1903))
public interface RegistriesV1903 extends WrapperObject
{
    @WrapperCreator
    static RegistriesV1903 create(Object wrapped)
    {
        return WrapperObject.create(RegistriesV1903.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM"))
    DefaultedRegistryV_1300__1400 staticItem();
    static DefaultedRegistryV_1300__1400 item()
    {
        return create(null).staticItem();
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="DATA_COMPONENT_TYPE", begin=2005))
    Registry staticComponentKeyV2005();
    static Registry componentKey()
    {
        return create(null).staticComponentKeyV2005();
    }
    
    static Registry entityType()
    {
        return create(null).staticEntityType();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="ENTITY_TYPE"))
    Registry staticEntityType();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="SCREEN_HANDLER"))
    Registry staticWindowType();
    static Registry windowType()
    {
        return create(null).staticWindowType();
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="BLOCK_ENTITY_TYPE"))
    Registry staticBlockEntityType();
    static Registry blockEntityType()
    {
        return create(null).staticBlockEntityType();
    }
}
