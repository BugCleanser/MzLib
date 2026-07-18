package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.Impl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
    {
        @VersionName(name = "boolean", remap = false, end = 900),
        @VersionName(name = "net.minecraft.util.Hand", begin = 900)
    })
public interface Hand extends WrapperObject
{
    WrapperFactory<Hand> FACTORY = WrapperFactory.of(Hand.class);

    static Hand mainHand()
    {
        return FACTORY.getStatic().static$mainHand();
    }
    Hand static$mainHand();
    @Impl("static$mainHand")
    @VersionRange(end = 900)
    default Hand static$mainHandV_900()
    {
        return FACTORY.create(true);
    }
    @Impl("static$mainHand")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "field_14436", end = 1400),
        @VersionName(name = "field_5808", begin = 1400)
    })
    Hand static$mainHandV900();

    static Hand offHand()
    {
        return FACTORY.getStatic().static$offHand();
    }
    Hand static$offHand();
    @Impl("static$offHand")
    @VersionRange(end = 900)
    default Hand static$offHandV_900()
    {
        return FACTORY.create(false);
    }
    @Impl("static$offHand")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "field_14437", end = 1400),
        @VersionName(name = "field_5810", begin = 1400)
    })
    Hand static$offHandV900();
}
