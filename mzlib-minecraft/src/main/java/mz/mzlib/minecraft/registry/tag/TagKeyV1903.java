package mz.mzlib.minecraft.registry.tag;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1903)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.registry.tag.TagKey"))
public interface TagKeyV1903<T> extends WrapperObject
{
    WrapperFactory<TagKeyV1903<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(TagKeyV1903.class));
}
