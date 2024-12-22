package mz.mzlib.minecraft.entity.player;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.util.Hand"))
public interface EnumHand extends WrapperObject
{
    @WrapperCreator
    static EnumHand create(Object wrapped)
    {
        return WrapperObject.create(EnumHand.class, wrapped);
    }
    
    static EnumHand mainHand()
    {
        return create(null).staticMainHand();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_14436", end=1400), @VersionName(name="field_5808", begin=1400)})
    EnumHand staticMainHand();
    
    static EnumHand offHand()
    {
        return create(null).staticOffHand();
    }
    @WrapMinecraftFieldAccessor({@VersionName(name="field_14437", end=1400), @VersionName(name="field_5810", begin=1400)})
    EnumHand staticOffHand();
}
