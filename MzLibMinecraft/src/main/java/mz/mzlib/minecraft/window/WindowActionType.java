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
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12263", end=1400), @VersionName(name="field_7789", begin=1400)})
    WindowActionType staticPlace();
    
    static WindowActionType place()
    {
        return create(null).staticPlace();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12264", end=1400), @VersionName(name="field_7790", begin=1400)})
    WindowActionType staticTake();
    
    static WindowActionType take()
    {
        return create(null).staticTake();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12265", end=1400), @VersionName(name="field_7791", begin=1400)})
    WindowActionType staticSwap();
    
    static WindowActionType swap()
    {
        return create(null).staticSwap();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12266", end=1400), @VersionName(name="field_7793", begin=1400)})
    WindowActionType staticTakeAll();
    
    static WindowActionType takeAll()
    {
        return create(null).staticTakeAll();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12267", end=1400), @VersionName(name="field_7794", begin=1400)})
    WindowActionType staticQuickMove();
    
    static WindowActionType quickMove()
    {
        return create(null).staticQuickMove();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12268", end=1400), @VersionName(name="field_7795", begin=1400)})
    WindowActionType staticThrowOut();
    
    static WindowActionType throwOut()
    {
        return create(null).staticThrowOut();
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_12269", end=1400), @VersionName(name="field_7796", begin=1400)})
    WindowActionType staticCopy();
    
    static WindowActionType copy()
    {
        return create(null).staticCopy();
    }
}
