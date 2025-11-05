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
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@Compound
public interface WindowFactorySimple extends WindowFactory
{
    WrapperFactory<WindowFactorySimple> FACTORY = WrapperFactory.of(WindowFactorySimple.class);
    @Deprecated
    @WrapperCreator
    static WindowFactorySimple create(Object wrapped)
    {
        return WrapperObject.create(WindowFactorySimple.class, wrapped);
    }
    
    @WrapConstructor
    WindowFactorySimple staticNewInstance();
    
    static WindowFactorySimple newInstance(String windowIdV_1400, Text displayName, BiFunction<Integer, InventoryPlayer, Window> windowCreator)
    {
        WindowFactorySimple result = FACTORY.getStatic().staticNewInstance();
        result.setWindowTypeIdV_1400(windowIdV_1400);
        result.setDisplayName(displayName);
        result.setWindowCreator(windowCreator);
        return result;
    }
    
    @VersionRange(end=1300)
    @CompoundOverride(parent=AbstractWindowFactory.class, method="getTranslationKeyV_1300")
    @Override
    default String getTranslationKeyV_1300()
    {
        return null;
    }
    
    @VersionRange(begin=1300, end=1400)
    @CompoundOverride(parent=AbstractWindowFactory.class, method="getDefaultNameV1300_1400")
    @Override
    default Text getDefaultNameV1300_1400()
    {
        return this.propDisplayName();
    }
    
    @VersionRange(end=1400)
    @CompoundOverride(parent=AbstractWindowFactory.class, method="hasCustomNameV_1400")
    @Override
    default boolean hasCustomNameV_1400()
    {
        return true;
    }
    
    @VersionRange(end=1400)
    @CompoundOverride(parent=AbstractWindowFactory.class, method="getCustomNameV_1400")
    @Override
    default Text getCustomNameV_1400()
    {
        return this.propDisplayName();
    }
    
    @CompoundOverride(parent=WindowFactory.class, method="getWindowTypeIdV_1400")
    @PropAccessor("windowTypeIdV_1400")
    String getWindowTypeIdV_1400();
    
    @PropAccessor("windowTypeIdV_1400")
    void setWindowTypeIdV_1400(String windowId);
    
    @PropAccessor("displayName")
    Text propDisplayName();
    
    @PropAccessor("displayName")
    void setDisplayName(Text displayName);
    
    @PropAccessor("windowCreator")
    BiFunction<Integer, InventoryPlayer, Window> getWindowCreator();
    
    @PropAccessor("windowCreator")
    void setWindowCreator(BiFunction<Integer, InventoryPlayer, Window> windowCreator);
    
    static WindowFactorySimple newInstance(WindowType windowType, Text displayName, BiFunction<Integer, InventoryPlayer, Window> initializer)
    {
        return newInstance(windowType.typeIdV_1400, displayName, initializer);
    }
    
    static WindowFactorySimple chest(Text title, Inventory inventory, int rows, Consumer<? super WindowChest> initializer)
    {
        return newInstance(WindowType.generic9x(rows), title, (syncId, inventoryPlayer)->
        {
            WindowChest result = WindowChest.newInstance(syncId, inventoryPlayer, inventory, rows);
            initializer.accept(result);
            return result;
        });
    }
    
    @CompoundOverride(parent=WindowFactory.class, method="getDisplayNameV1400")
    @VersionRange(begin=1400)
    default Text getDisplayNameV1400()
    {
        return this.propDisplayName();
    }
    
    @CompoundOverride(parent=WindowFactory.class, method="createWindowV_1400")
    @VersionRange(end=1400)
    default Window createWindowV_1400(InventoryPlayer inventoryPlayer, AbstractEntityPlayer player)
    {
        return this.getWindowCreator().apply(-1, inventoryPlayer);
    }
    
    @CompoundOverride(parent=WindowFactory.class, method="createWindowV1400")
    @VersionRange(begin=1400)
    default Window createWindowV1400(int syncId, InventoryPlayer inventoryPlayer, AbstractEntityPlayer player)
    {
        return this.getWindowCreator().apply(syncId, inventoryPlayer);
    }
}
