package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.registry.SimpleRegistry;
import mz.mzlib.minecraft.registry.entry.RegistryEntryV1802;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;

@WrapMinecraftClass(@VersionName(name="net.minecraft.item.Item"))
public interface Item extends ItemConvertible
{
    default Identifier getId()
    {
        if(MinecraftPlatform.instance.getVersion()<1300)
            return this.getRegistryV_1300().getId(this, Identifier::create);
        else
            return null; // TODO
    }

    @WrapMinecraftFieldAccessor(@VersionName(name="REGISTRY", end=1300))
    SimpleRegistry getRegistryV_1300();

    @WrapMinecraftMethod(@VersionName(name="getRegistryEntry", begin=1802))
    RegistryEntryV1802 getRegistryEntryV1802();
}
