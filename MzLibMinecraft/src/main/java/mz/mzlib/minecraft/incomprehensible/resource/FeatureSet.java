package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.resource.featuretoggle.FeatureSet"))
public interface FeatureSet extends WrapperObject
{
    WrapperFactory<FeatureSet> FACTORY = WrapperFactory.of(FeatureSet.class);
}
