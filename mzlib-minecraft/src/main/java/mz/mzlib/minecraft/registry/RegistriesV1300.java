package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.block.entity.BlockEntityTypeV1300;
import mz.mzlib.minecraft.component.ComponentKeyV2005;
import mz.mzlib.minecraft.entity.EntityType;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.window.WindowTypeV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapMinecraftClass({
    @VersionName(name = "net.minecraft.util.registry.Registry", end = 1903),
    @VersionName(name = "net.minecraft.registry.Registries", begin = 1903)
})
public interface RegistriesV1300 extends WrapperObject
{
    WrapperFactory<RegistriesV1300> FACTORY = WrapperFactory.of(RegistriesV1300.class);
    @Deprecated
    @WrapperCreator
    static RegistriesV1300 create(Object wrapped)
    {
        return WrapperObject.create(RegistriesV1300.class, wrapped);
    }

    static Registry<Item> item()
    {
        return FACTORY.getStatic().static$item();
    }

    Registry<Item> static$item();

    @SpecificImpl("static$item")
    @VersionRange(end = 1903)
    @WrapMinecraftFieldAccessor(@VersionName(name = "ITEM"))
    SimpleRegistry<Item> static$itemV_1903();

    @SpecificImpl("static$item")
    @VersionRange(begin = 1903)
    @WrapMinecraftFieldAccessor(@VersionName(name = "ITEM"))
    Registry<Item> static$itemV1903();

    @VersionRange(begin = 2005)
    @WrapMinecraftFieldAccessor(@VersionName(name = "DATA_COMPONENT_TYPE"))
    Registry<ComponentKeyV2005<?>> static$componentKeyV2005();

    static Registry<ComponentKeyV2005<?>> componentKeyV2005()
    {
        return FACTORY.getStatic().static$componentKeyV2005();
    }

    static Registry<EntityType> entityType()
    {
        return FACTORY.getStatic().static$entityType();
    }

    @WrapMinecraftFieldAccessor(@VersionName(name = "ENTITY_TYPE"))
    Registry<EntityType> static$entityType();

    @VersionRange(begin = 1400)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "CONTAINER", end = 1600),
        @VersionName(name = "SCREEN_HANDLER", begin = 1600)
    })
    Registry<WindowTypeV1400> static$windowTypeV1400();

    static Registry<WindowTypeV1400> windowTypeV1400()
    {
        return FACTORY.getStatic().static$windowTypeV1400();
    }

    static Registry<BlockEntityTypeV1300> blockEntityType()
    {
        return FACTORY.getStatic().static$blockEntityType();
    }

    @WrapMinecraftFieldAccessor({
        @VersionName(name = "BLOCK_ENTITY_TYPE", end = 1400),
        @VersionName(name = "field_11137", begin = 1400, end = 1903),
        @VersionName(name = "field_41181", begin = 1903)
    })
    Registry<BlockEntityTypeV1300> static$blockEntityType();
}
