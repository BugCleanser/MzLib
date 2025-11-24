package mz.mzlib.minecraft.incomprehensible;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.resource.FeatureSetV1903;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1600)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.world.SaveProperties"))
public interface SavePropertiesV1600 extends WrapperObject
{
    WrapperFactory<SavePropertiesV1600> FACTORY = WrapperFactory.of(SavePropertiesV1600.class);

    @VersionRange(begin = 1903)
    @WrapMinecraftMethod(@VersionName(name = "getEnabledFeatures"))
    FeatureSetV1903 getEnabledFeaturesV1903();
}
