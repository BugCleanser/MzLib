package mz.mzlib.minecraft.window.sync;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2105)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.screen.sync.TrackedSlot"))
public interface TrackedSlotV2105 extends WrapperObject
{
    WrapperFactory<TrackedSlotV2105> FACTORY = WrapperFactory.of(TrackedSlotV2105.class);

    @WrapMinecraftMethod(@VersionName(name = "isInSync"))
    boolean isInSync(ItemStack actualStack);

    @VersionRange(begin = 2105)
    @WrapMinecraftMethod(@VersionName(name = "setReceivedHash"))
    void setReceivedHashV2105(ItemStackHashV2105 value);

    @VersionRange(begin = 2105)
    @WrapMinecraftInnerClass(outer = TrackedSlotV2105.class, name = @VersionName(name = "Impl"))
    interface Impl extends TrackedSlotV2105
    {
        WrapperFactory<Impl> FACTORY = WrapperFactory.of(Impl.class);

        @WrapMinecraftFieldAccessor(@VersionName(name = "hasher"))
        ComponentChangesHashV2105.ComponentHasher getHasher();
    }
}
