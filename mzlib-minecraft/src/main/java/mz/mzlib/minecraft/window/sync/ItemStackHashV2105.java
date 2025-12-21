package mz.mzlib.minecraft.window.sync;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2105)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.screen.sync.ItemStackHash"))
public interface ItemStackHashV2105 extends WrapperObject
{
    WrapperFactory<ItemStackHashV2105> FACTORY = WrapperFactory.of(ItemStackHashV2105.class);

    static ItemStackHashV2105 of(ItemStack stack, ComponentChangesHashV2105.ComponentHasher hasher)
    {
        return FACTORY.getStatic().static$of(stack, hasher);
    }

    @WrapMinecraftMethod(@VersionName(name = "hashEquals"))
    boolean hashEquals(ItemStack stack, ComponentChangesHashV2105.ComponentHasher hasher);


    @WrapMinecraftMethod(@VersionName(name = "fromItemStack"))
    ItemStackHashV2105 static$of(ItemStack stack, ComponentChangesHashV2105.ComponentHasher hasher);
}
