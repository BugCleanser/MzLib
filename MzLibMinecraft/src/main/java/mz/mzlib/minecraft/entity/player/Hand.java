package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name="boolean", remap=false, end=900),
                @VersionName(name="net.minecraft.util.Hand", begin=900)
        })
public interface Hand extends WrapperObject
{
    WrapperFactory<Hand> FACTORY = WrapperFactory.of(Hand.class);
    
    static Hand mainHand()
    {
        return FACTORY.getStatic().staticMainHand();
    }
    Hand staticMainHand();
    @SpecificImpl("staticMainHand")
    @VersionRange(end=900)
    default Hand staticMainHandV_900()
    {
        return FACTORY.create(true);
    }
    @SpecificImpl("staticMainHand")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_14436", end=1400), @VersionName(name="field_5808", begin=1400)})
    Hand staticMainHandV900();
    
    static Hand offHand()
    {
        return FACTORY.getStatic().staticOffHand();
    }
    Hand staticOffHand();
    @SpecificImpl("staticOffHand")
    @VersionRange(end=900)
    default Hand staticOffHandV_900()
    {
        return FACTORY.create(false);
    }
    @SpecificImpl("staticOffHand")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_14437", end=1400), @VersionName(name="field_5810", begin=1400)})
    Hand staticOffHandV900();
}
