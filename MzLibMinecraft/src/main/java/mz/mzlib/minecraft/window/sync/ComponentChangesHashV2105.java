package mz.mzlib.minecraft.window.sync;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2105)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.screen.sync.ComponentChangesHash"))
public interface ComponentChangesHashV2105 extends WrapperObject
{
    WrapperFactory<ComponentChangesHashV2105> FACTORY = WrapperFactory.of(ComponentChangesHashV2105.class);

    @VersionRange(begin = 2105)
    @WrapMinecraftInnerClass(outer = ComponentChangesHashV2105.class, name = @VersionName(name = "ComponentHasher"))
    interface ComponentHasher extends WrapperObject
    {
        WrapperFactory<ComponentHasher> FACTORY = WrapperFactory.of(ComponentHasher.class);
    }
}
