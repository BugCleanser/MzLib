package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.Registry", end=1903), @VersionName(name="net.minecraft.registry.Registries", begin=1903)})
public interface RegistriesV1300 extends WrapperObject
{
    @WrapperCreator
    static RegistriesV1300 create(Object wrapped)
    {
        return WrapperObject.create(RegistriesV1300.class, wrapped);
    }
    
    static Registry item()
    {
        return create(null).staticItem();
    }
    Registry staticItem();
    @SpecificImpl("staticItem")
    @VersionRange(end=1400)
    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM"))
    SimpleRegistry staticItemV_1400();
    @SpecificImpl("staticItem")
    @VersionRange(begin=1400)
    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM"))
    DefaultedRegistryV1400_1903 staticItemV1400();
    
    @VersionRange(begin=2005)
    @WrapMinecraftFieldAccessor(@VersionName(name="DATA_COMPONENT_TYPE"))
    Registry staticComponentKeyV2005();
    static Registry componentKeyV2005()
    {
        return create(null).staticComponentKeyV2005();
    }
    
    static Registry entityType()
    {
        return create(null).staticEntityType();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="ENTITY_TYPE"))
    Registry staticEntityType();
    
    @VersionRange(begin=1400)
    @WrapMinecraftFieldAccessor({@VersionName(name="CONTAINER", end=1600), @VersionName(name="SCREEN_HANDLER", begin=1600)})
    Registry staticWindowTypeV1400();
    static Registry windowTypeV1400()
    {
        return create(null).staticWindowTypeV1400();
    }
    
    static Registry blockEntityType()
    {
        return create(null).staticBlockEntityType();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="BLOCK_ENTITY_TYPE"))
    Registry staticBlockEntityType();
}
