package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperFactory;

@VersionRange(begin = 2610)
@WrapClassForName("net.minecraft.world.item.ItemStackTemplate")
public interface ItemStackTemplateV2610 extends ItemInstanceV2610
{
    WrapperFactory<ItemStackTemplateV2610> FACTORY = WrapperFactory.of(ItemStackTemplateV2610.class);

    static ItemStackTemplateV2610 of(ItemStack itemStack)
    {
        return FACTORY.getStatic().static$of(itemStack);
    }


    @WrapMethod("fromNonEmptyStack")
    ItemStackTemplateV2610 static$of(ItemStack itemStack);
}
