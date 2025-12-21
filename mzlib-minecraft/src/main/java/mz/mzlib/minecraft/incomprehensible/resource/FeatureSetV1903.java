package mz.mzlib.minecraft.incomprehensible.resource;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1903)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.resource.featuretoggle.FeatureSet"))
public interface FeatureSetV1903 extends WrapperObject
{
    WrapperFactory<FeatureSetV1903> FACTORY = WrapperFactory.of(FeatureSetV1903.class);
}
