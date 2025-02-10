package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=900)
@WrapMinecraftClass(@VersionName(name="net.minecraft.util.Hand"))
public interface EnumHandV900 extends WrapperObject
{
    @WrapperCreator
    static EnumHandV900 create(Object wrapped)
    {
        return WrapperObject.create(EnumHandV900.class, wrapped);
    }
    
    static EnumHandV900 mainHand()
    {
        return create(null).staticMainHand();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_14436", end=1400), @VersionName(name="field_5808", begin=1400)})
    EnumHandV900 staticMainHand();
    
    static EnumHandV900 offHand()
    {
        return create(null).staticOffHand();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_14437", end=1400), @VersionName(name="field_5810", begin=1400)})
    EnumHandV900 staticOffHand();
}
