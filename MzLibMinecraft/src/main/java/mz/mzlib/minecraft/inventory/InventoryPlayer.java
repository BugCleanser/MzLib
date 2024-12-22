package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.player.PlayerInventory"))
public interface InventoryPlayer extends WrapperObject, Inventory
{
    @WrapperCreator
    static InventoryPlayer create(Object wrapped)
    {
        return WrapperObject.create(InventoryPlayer.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="player"))
    AbstractEntityPlayer getPlayer();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="selectedSlot"))
    int getHandIndex();
}
