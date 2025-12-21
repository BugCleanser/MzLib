package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
    {
        @VersionName(name = "boolean", remap = false, end = 900),
        @VersionName(name = "net.minecraft.util.ActionResult", begin = 900)
    })
public interface ActionResult extends WrapperObject
{
    WrapperFactory<ActionResult> FACTORY = WrapperFactory.of(ActionResult.class);

    static ActionResult pass()
    {
        return FACTORY.getStatic().static$pass();
    }
    ActionResult static$pass();
    @SpecificImpl("static$pass")
    @VersionRange(end = 900)
    default ActionResult static$passV_900()
    {
        return FACTORY.create(false);
    }
    @SpecificImpl("static$pass")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "PASS", end = 1400),
        @VersionName(name = "field_5811", begin = 1400)
    })
    ActionResult static$passV900();

    static ActionResult success()
    {
        return FACTORY.getStatic().static$success();
    }
    ActionResult static$success();
    @SpecificImpl("static$success")
    @VersionRange(end = 900)
    default ActionResult static$successV_900()
    {
        return FACTORY.create(true);
    }
    @SpecificImpl("static$success")
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor(
        {
            @VersionName(name = "SUCCESS", end = 1400),
            @VersionName(name = "field_5812", begin = 1400)
        })
    ActionResult static$successV900();

    @VersionRange(begin = 900)
    static ActionResult failV900()
    {
        return FACTORY.getStatic().static$failV900();
    }
    @VersionRange(begin = 900)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "FAIL", end = 1400),
        @VersionName(name = "field_5814", begin = 1400)
    })
    ActionResult static$failV900();

    @VersionRange(begin = 1500)
    static ActionResult consumeV1500()
    {
        return FACTORY.getStatic().static$consumeV1500();
    }
    @VersionRange(begin = 1500)
    @WrapMinecraftFieldAccessor({
        @VersionName(name = "CONSUME", end = 1400),
        @VersionName(name = "field_21466", begin = 1400)
    })
    ActionResult static$consumeV1500();

    static boolean isAccepted()
    {
        return FACTORY.getStatic().static$isAccepted();
    }
    boolean static$isAccepted();
    @SpecificImpl("static$isAccepted")
    @VersionRange(end = 1500)
    default boolean static$isAcceptedV_1500()
    {
        return this.equals(success());
    }
    @SpecificImpl("static$isAccepted")
    @VersionRange(begin = 1500)
    @WrapMinecraftMethod(@VersionName(name = "isAccepted"))
    boolean static$isAcceptedV1500();

    @VersionRange(begin = 2102)
    @WrapMinecraftInnerClass(outer = ActionResult.class, name = @VersionName(name = "Success"))
    interface SuccessV2102 extends ActionResult
    {
        WrapperFactory<SuccessV2102> FACTORY = WrapperFactory.of(SuccessV2102.class);

        @WrapMinecraftMethod(@VersionName(name = "withNewHandStack"))
        SuccessV2102 withNewHandStack(ItemStack value);

        @WrapMinecraftMethod(@VersionName(name = "getNewHandStack"))
        ItemStack getNewHandStack();
    }
}
