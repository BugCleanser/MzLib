package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.component.DataComponentTypes", begin = 2005))
public interface ComponentKeysV2005 extends WrapperObject
{
    WrapperFactory<ComponentKeysV2005> FACTORY = WrapperFactory.of(ComponentKeysV2005.class);
    }

