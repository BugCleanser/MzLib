package mz.mzlib.minecraft.incomprehensible.context;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.world.World;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2102)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.recipe.display.SlotDisplayContexts"))
public interface SlotDisplayContextsV2102 extends WrapperObject
{
    WrapperFactory<SlotDisplayContextsV2102> FACTORY = WrapperFactory.of(SlotDisplayContextsV2102.class);

    static MojangContextV2102 create()
    {
        return create(MinecraftServer.instance.getWorlds().iterator().next());
    }
    static MojangContextV2102 create(World world)
    {
        return FACTORY.getStatic().static$create(world);
    }

    @WrapMinecraftMethod(@VersionName(name = "createParameters"))
    MojangContextV2102 static$create(World world);
}
