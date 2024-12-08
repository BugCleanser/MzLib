package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.PropAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.BiFunction;

@Compound
public interface WindowFactoryCustom extends WrapperObject, WindowFactory
{
    @WrapperCreator
    static WindowFactoryCustom create(Object wrapped)
    {
        return WrapperObject.create(WindowFactoryCustom.class, wrapped);
    }
    
    @CompoundOverride("getWindowIdV_1400")
    @VersionRange(end=1400)
    @PropAccessor("windowId")
    String getWindowIdV_1400();
    @PropAccessor("windowId")
    void setWindowIdV_1400(String windowId);
    
    @PropAccessor("displayName")
    Text getDisplayName();
    @PropAccessor("displayName")
    void setDisplayName(Text displayName);
    
    @PropAccessor("windowCreator")
    BiFunction<Integer, InventoryPlayer, Window> getWindowCreator();
    @PropAccessor("windowCreator")
    void setWindowCreator(BiFunction<Integer, InventoryPlayer, Window> windowCreator);
    
    @WrapConstructor
    WindowFactoryCustom staticNewInstance();
    static WindowFactoryCustom newInstance(String windowIdV_1400, Text displayName, BiFunction<Integer, InventoryPlayer, Window> windowCreator)
    {
        WindowFactoryCustom result = create(null).staticNewInstance();
        result.setWindowIdV_1400(windowIdV_1400);
        result.setDisplayName(displayName);
        result.setWindowCreator(windowCreator);
        return result;
    }
    
    @CompoundOverride("getDisplayNameV_1400")
    @VersionRange(end=1400)
    default Text getDisplayNameV_1400()
    {
        return this.getDisplayName();
    }
    
    @CompoundOverride("getDisplayNameV1400")
    @VersionRange(begin=1400)
    default Text getDisplayNameV1400()
    {
        return this.getDisplayName();
    }
    
    @CompoundOverride("createWindowV_1400")
    @VersionRange(end=1400)
    default Window createWindowV_1400(InventoryPlayer inventoryPlayer, AbstractEntityPlayer player)
    {
        return this.getWindowCreator().apply(-1, inventoryPlayer);
    }
    
    @CompoundOverride("createWindowV1400")
    @VersionRange(begin=1400)
    default Window createWindowV1400(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player)
    {
        return this.getWindowCreator().apply(syncId, inventoryPlayer);
    }
}
