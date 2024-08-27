package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.registry.DefaultedRegistryV_1300__1400;
import mz.mzlib.minecraft.registry.RegistriesV1903;
import mz.mzlib.minecraft.registry.Registry;
import mz.mzlib.minecraft.registry.SimpleRegistry;
import mz.mzlib.minecraft.registry.entry.RegistryEntryV1802;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.Item"))
public interface Item extends WrapperObject, ItemConvertible
{
    @WrapperCreator
    static Item create(Object wrapped)
    {
        return WrapperObject.create(Item.class, wrapped);
    }

    static Item fromId(Identifier id)
    {
        return null; // TODO
    }

    Identifier getId();
    @SpecificImpl("getId")
    @VersionRange(end=1903)
    default Identifier getIdV_1903()
    {
        return getRegistryV_1903().getId(this);
    }
    @SpecificImpl("getId")
    @VersionRange(begin=1903)
    default Identifier getIdV1903()
    {
        return getRegistryV1903().getIdV1300(this);
    }

    Registry staticGetRegistry();
    static Registry getRegistry()
    {
        return create(null).staticGetRegistry();
    }

    static SimpleRegistry getRegistryV_1903()
    {
        return create(null).staticGetRegistryV_1903();
    }

    @SpecificImpl("staticGetRegistry")
    @VersionRange(end=1903)
    SimpleRegistry staticGetRegistryV_1903();

    @SpecificImpl("staticGetRegistryV_1903")
    @WrapMinecraftFieldAccessor(@VersionName(name="REGISTRY", end=1300))
    SimpleRegistry staticGetRegistryV_1300();

    @SpecificImpl("staticGetRegistryV_1903")
    @VersionRange(begin=1300,end=1903)
    default SimpleRegistry staticGetRegistryV1300_1903()
    {
        return Registry.itemV1300_1903();
    }

    @SpecificImpl("staticGetRegistry")
    @VersionRange(begin=1903)
    default DefaultedRegistryV_1300__1400 staticGetRegistryV1903()
    {
        return RegistriesV1903.item();
    }
    static DefaultedRegistryV_1300__1400 getRegistryV1903()
    {
        return create(null).staticGetRegistryV1903();
    }

    @WrapMinecraftMethod(@VersionName(name="getRegistryEntry", begin=1802))
    RegistryEntryV1802 getRegistryEntryV1802();
}
