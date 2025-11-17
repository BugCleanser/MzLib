package mz.mzlib.minecraft.incomprehensible;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.incomprehensible.resource.FeatureSet;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.world.SaveProperties"))
public interface SaveProperties extends WrapperObject
{
    WrapperFactory<SaveProperties> FACTORY = WrapperFactory.of(SaveProperties.class);

    @WrapMinecraftMethod(@VersionName(name = "getEnabledFeatures"))
    FeatureSet getEnabledFeatures();
}
