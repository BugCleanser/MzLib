package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.inventory.Inventory"))
public interface Inventory extends WrapperObject
{
    @WrapperCreator
    static Inventory create(Object wrapped)
    {
        return WrapperObject.create(Inventory.class, wrapped);
    }
    
    @WrapMinecraftMethod({@VersionName(name="getInvSize", end=1600), @VersionName(name="size", begin=1600)})
    int size();
    
    @WrapMinecraftMethod({@VersionName(name="getInvStack", end=1600), @VersionName(name="getStack", begin=1600)})
    ItemStack getItemStack(int index);
    
    @WrapMinecraftMethod({@VersionName(name="setInvStack", end=1600), @VersionName(name="setStack", begin=1600)})
    void setItemStack(int index, ItemStack itemStack);
    
    @WrapMinecraftMethod(@VersionName(name="clear"))
    void clear();
}
