package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

// TODO: test v_1400
@WrapMinecraftClass({@VersionName(name="net.minecraft.util.ItemAction", end=1400), @VersionName(name="net.minecraft.container.SlotActionType", begin=1400, end=1600), @VersionName(name="net.minecraft.screen.slot.SlotActionType", begin=1600)})
public interface WindowActionType extends WrapperObject
{
    @WrapperCreator
    static WindowActionType create(Object wrapped)
    {
        return WrapperObject.create(WindowActionType.class, wrapped);
    }
    
    /**
     * When finish dragging, There will be multiple actions
     * Drag begins when data%4==0
     * Drag a slot when data%4==1
     * Drag ends when data%4==2
     * Drag with left-button when data/4==0
     * Drag with right-button when data/4==1
     * Drag with middle-button when data/4==2
     */
    static WindowActionType drag()
    {
        return create(null).staticDrag();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12263", end=1400), @VersionName(name="field_7789", begin=1400)})
    WindowActionType staticDrag();
    
    /**
     * Left-click or right-click
     * The action data is either 0 (left-click) or 1 (right-click)
     */
    static WindowActionType click()
    {
        return create(null).staticClick();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12264", end=1400), @VersionName(name="field_7790", begin=1400)})
    WindowActionType staticClick();
    
    /**
     * Use shortcut keys to exchange items (1~9 or F)
     * The action data is the swap slot of player inventory (0~8 or 40)
     */
    static WindowActionType swap()
    {
        return create(null).staticSwap();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12265", end=1400), @VersionName(name="field_7791", begin=1400)})
    WindowActionType staticSwap();
    
    /**
     * Double-click an item
     */
    static WindowActionType pickUpAll()
    {
        return create(null).staticPickUpAll();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12266", end=1400), @VersionName(name="field_7793", begin=1400)})
    WindowActionType staticPickUpAll();
    
    /**
     * Shift-click a slot
     * The action data is either 0 (left-click) or 1 (right-click)
     */
    static WindowActionType shiftClick()
    {
        return create(null).staticShiftClick();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12267", end=1400), @VersionName(name="field_7794", begin=1400)})
    WindowActionType staticShiftClick();
    
    /**
     * Throw item:
     *   Throw one item when data==0
     *   Throw all items when data==1
     * Click outside without item:
     *   index==-999
     *   The action data is either 0 (left-click) or 1 (right-click)
     */
    static WindowActionType drop()
    {
        return create(null).staticDrop();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12268", end=1400), @VersionName(name="field_7795", begin=1400)})
    WindowActionType staticDrop();
    
    /**
     * Middle-click in creative-mode or middle-click with item
     */
    static WindowActionType copy()
    {
        return create(null).staticCopy();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12269", end=1400), @VersionName(name="field_7796", begin=1400)})
    WindowActionType staticCopy();
    
    @Override
    default String toString0()
    {
        if(this.equals(drag()))
            return "DRAG";
        else if(this.equals(click()))
            return "CLICK";
        else if(this.equals(swap()))
            return "SWAP";
        else if(this.equals(pickUpAll()))
            return "PICK_UP_ALL";
        else if(this.equals(shiftClick()))
            return "SHIFT_CLICK";
        else if(this.equals(drop()))
            return "DROP";
        else if(this.equals(copy()))
            return "COPY";
        else
            return "UNKNOWN";
    }
}
