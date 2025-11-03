package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
        {
                @VersionName(name="boolean", end=900),
                @VersionName(name="net.minecraft.util.ActionResult", begin=900)
        })
public interface ActionResult extends WrapperObject
{
    WrapperFactory<ActionResult> FACTORY = WrapperFactory.of(ActionResult.class);
    
    static ActionResult pass()
    {
        return FACTORY.getStatic().staticPass();
    }
    ActionResult staticPass();
    @SpecificImpl("staticPass")
    @VersionRange(end=900)
    default ActionResult staticPassV_900()
    {
        return FACTORY.create(false);
    }
    @SpecificImpl("staticPass")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor({@VersionName(name="PASS", end=1400), @VersionName(name="field_5811", begin=1400)})
    ActionResult staticPassV900();
    
    static ActionResult success()
    {
        return FACTORY.getStatic().staticSuccess();
    }
    ActionResult staticSuccess();
    @SpecificImpl("staticSuccess")
    @VersionRange(end=900)
    default ActionResult staticSuccessV_900()
    {
        return FACTORY.create(true);
    }
    @SpecificImpl("staticSuccess")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor(
            {
                    @VersionName(name="SUCCESS", end=1400),
                    @VersionName(name="field_5812", begin=1400, end=2102),
                    @VersionName(name="field_52422", begin=2102) // yarn: SUCCESS_SERVER
            })
    ActionResult staticSuccessV900();
    
    @VersionRange(begin=900)
    static ActionResult failV900()
    {
        return FACTORY.getStatic().staticFailV900();
    }
    @VersionRange
    @WrapMinecraftFieldAccessor({@VersionName(name="FAIL", end=1400), @VersionName(name="field_5814", begin=1400)})
    ActionResult staticFailV900();
    
    @VersionRange(begin=1500)
    static ActionResult consumeV1500()
    {
        return FACTORY.getStatic().staticConsumeV1500();
    }
    @VersionRange(begin=1500)
    @WrapMinecraftFieldAccessor({@VersionName(name="CONSUME", end=1400), @VersionName(name="field_21466", begin=1400)})
    ActionResult staticConsumeV1500();
    
    static boolean isAccepted()
    {
        return FACTORY.getStatic().staticIsAccepted();
    }
    boolean staticIsAccepted();
    @SpecificImpl("staticIsAccepted")
    @VersionRange(end=1500)
    default boolean staticIsAcceptedV_1500()
    {
        return this.equals(success());
    }
    @SpecificImpl("staticIsAccepted")
    @VersionRange(begin=1500)
    @WrapMinecraftMethod(@VersionName(name="isAccepted"))
    boolean staticIsAcceptedV1500();
    
    // TODO
}
