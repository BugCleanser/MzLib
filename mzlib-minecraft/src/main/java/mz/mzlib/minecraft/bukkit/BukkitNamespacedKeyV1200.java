package mz.mzlib.minecraft.bukkit;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@VersionRange(begin = 1200)
@WrapClassForName("org.bukkit.NamespacedKey")
public interface BukkitNamespacedKeyV1200 extends WrapperObject
{
    WrapperFactory<BukkitNamespacedKeyV1200> FACTORY = WrapperFactory.of(BukkitNamespacedKeyV1200.class);
}
