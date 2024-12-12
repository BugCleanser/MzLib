package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryPlayer;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.compound.PropAccessor;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@Compound
public interface WindowFactorySimple extends WrapperObject, WindowFactory
{
    @WrapperCreator
    static WindowFactorySimple create(Object wrapped)
    {
        return WrapperObject.create(WindowFactorySimple.class, wrapped);
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
    WindowFactorySimple staticNewInstance();
    static WindowFactorySimple newInstance(String windowIdV_1400, Text displayName, BiFunction<Integer, InventoryPlayer, Window> windowCreator)
    {
        WindowFactorySimple result = create(null).staticNewInstance();
        result.setWindowIdV_1400(windowIdV_1400);
        result.setDisplayName(displayName);
        result.setWindowCreator(windowCreator);
        return result;
    }
    
    static WindowFactorySimple generic9x(Text title, Inventory inventory, int rows, Consumer<? super WindowGeneric9x> initializer)
    {
        return newInstance(WindowGeneric9x.windowIdV_1400, title, (syncId, inventoryPlayer)->
        {
            WindowGeneric9x result = WindowGeneric9x.newInstance(syncId, inventoryPlayer, inventory, rows);
            initializer.accept(result);
            return result;
        });
    }
    
    @CompoundOverride("getDisplayNameV_1400")
    @VersionRange(end=1400)
    default Text getDisplayNameV_1400()
    {
        return this.getDisplayName();
    }
    
    @CompoundOverride("hasDisplayNameV_1400")
    @VersionRange(end=1400)
    default boolean hasDisplayNameV_1400()
    {
        return this.getDisplayName().isPresent();
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
