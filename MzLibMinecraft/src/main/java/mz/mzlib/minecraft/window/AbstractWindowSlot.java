package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.CompoundSuper;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface AbstractWindowSlot extends WindowSlot
{
    WrapperFactory<AbstractWindowSlot> FACTORY = WrapperFactory.of(AbstractWindowSlot.class);
    @Deprecated
    @WrapperCreator
    static AbstractWindowSlot create(Object wrapped)
    {
        return WrapperObject.create(AbstractWindowSlot.class, wrapped);
    }
    
    default void onTake(AbstractEntityPlayer player, ItemStack itemStack)
    {
        this.onTakeSuper(player, itemStack);
    }
    
    void onTakeSuper(AbstractEntityPlayer player, ItemStack itemStack);
    
    @SpecificImpl("onTakeSuper")
    @VersionRange(end=1100)
    @VersionRange(begin=1700)
    @CompoundSuper(parent=WindowSlot.class, method="onTakeV_1100__1700")
    void onTakeSuperV_1100__1700(AbstractEntityPlayer player, ItemStack itemStack);
    
    @VersionRange(end=1700)
    @CompoundSuper(parent=WindowSlot.class, method="onTakeV1100_1700")
    ItemStack onTakeSuper0V1100_1700(AbstractEntityPlayer player, ItemStack itemStack);
    
    @SpecificImpl("onTakeSuper")
    @VersionRange(begin=1100, end=1700)
    default void onTakeSuperV1100_1700(AbstractEntityPlayer player, ItemStack itemStack)
    {
        ItemStack ignored = this.onTakeSuper0V1100_1700(player, itemStack);
    }
    
    @Override
    @VersionRange(end=1100)
    @VersionRange(begin=1700)
    @CompoundOverride(parent=WindowSlot.class, method="onTakeV_1100__1700")
    default void onTakeV_1100__1700(AbstractEntityPlayer player, ItemStack itemStack)
    {
        this.onTake(player, itemStack);
    }
    @Override
    @VersionRange(begin=1100, end=1700)
    @CompoundOverride(parent=WindowSlot.class, method="onTakeV1100_1700")
    default ItemStack onTakeV1100_1700(AbstractEntityPlayer player, ItemStack itemStack)
    {
        this.onTake(player, itemStack);
        return itemStack;
    }
}
