package mz.mzlib.minecraft.window;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.container.ContainerType",begin=1400, end=1600), @VersionName(name="net.minecraft.screen.ScreenHandlerType", begin=1600)})
public interface WindowTypeV1400 extends WrapperObject
{
    @WrapperCreator
    static WindowTypeV1400 create(Object wrapped)
    {
        return WrapperObject.create(WindowTypeV1400.class, wrapped);
    }
    
    @WrapFieldAccessor("#0")
    WindowTypeV1400 staticGeneric9x1();
    static WindowTypeV1400 generic9x1()
    {
        return create(null).staticGeneric9x1();
    }
    
    @WrapFieldAccessor("#1")
    WindowTypeV1400 staticGeneric9x2();
    static WindowTypeV1400 generic9x2()
    {
        return create(null).staticGeneric9x2();
    }
    
    @WrapFieldAccessor("#2")
    WindowTypeV1400 staticGeneric9x3();
    static WindowTypeV1400 generic9x3()
    {
        return create(null).staticGeneric9x3();
    }
    
    @WrapFieldAccessor("#3")
    WindowTypeV1400 staticGeneric9x4();
    static WindowTypeV1400 generic9x4()
    {
        return create(null).staticGeneric9x4();
    }
    
    @WrapFieldAccessor("#4")
    WindowTypeV1400 staticGeneric9x5();
    static WindowTypeV1400 generic9x5()
    {
        return create(null).staticGeneric9x5();
    }
    
    @WrapFieldAccessor("#5")
    WindowTypeV1400 staticGeneric9x6();
    static WindowTypeV1400 generic9x6()
    {
        return create(null).staticGeneric9x6();
    }
    
    // TODO
    @WrapFieldAccessor("#12")
    WindowTypeV1400 staticCrafting();
    static WindowTypeV1400 crafting()
    {
        return create(null).staticCrafting();
    }
}
