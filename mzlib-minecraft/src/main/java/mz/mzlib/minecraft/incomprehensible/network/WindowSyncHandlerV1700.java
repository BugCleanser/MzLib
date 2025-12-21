package mz.mzlib.minecraft.incomprehensible.network;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.window.sync.TrackedSlotV2105;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1700)
@WrapMinecraftClass(@VersionName(name = "net.minecraft.screen.ScreenHandlerSyncHandler"))
public interface WindowSyncHandlerV1700 extends WrapperObject
{
    WrapperFactory<WindowSyncHandlerV1700> FACTORY = WrapperFactory.of(WindowSyncHandlerV1700.class);

    @VersionRange(begin = 2105)
    @WrapMinecraftMethod(@VersionName(name = "createTrackedSlot"))
    TrackedSlotV2105 createTrackedSlotV2105();

    /**
     * An anonymous class in player class
     */
    @VersionRange(begin = 1700)
    @WrapMinecraftInnerClass(outer = EntityPlayer.class, name = @VersionName(name = "1"))
    interface Impl extends WindowSyncHandlerV1700
    {
        WrapperFactory<Impl> FACTORY = WrapperFactory.of(Impl.class);

        /**
         * this$0
         */
        @WrapFieldAccessor("@0")
        EntityPlayer getPlayer();
    }
}
