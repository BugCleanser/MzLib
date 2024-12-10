package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.util.registry.Registry", end=1903),@VersionName(name = "net.minecraft.registry.Registry", begin = 1903)})
public interface Registry extends WrapperObject
{
    @WrapperCreator
    static Registry create(Object wrapped)
    {
        return WrapperObject.create(Registry.class, wrapped);
    }

    @WrapMinecraftMethod(@VersionName(name="getId",begin=1300))
    Identifier getIdV1300(WrapperObject value);

    SimpleRegistry staticItemV1300_1903();
    static SimpleRegistry itemV1300_1903()
    {
        return create(null).staticItemV1300_1903();
    }

    @SpecificImpl("staticItemV1300_1903")
    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM", begin=1300, end=1400))
    SimpleRegistry staticItemV1300_1400();

    @SpecificImpl("staticItemV1300_1903")
    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM", begin=1400, end=1903))
    DefaultedRegistryV1400_1903 staticItemV1400_1903();
}
