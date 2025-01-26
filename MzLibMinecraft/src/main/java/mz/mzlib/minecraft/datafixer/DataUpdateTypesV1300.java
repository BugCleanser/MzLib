package mz.mzlib.minecraft.datafixer;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="net.minecraft.class_3402", end=1400), @VersionName(name="net.minecraft.datafixer.TypeReferences", begin=1400, end=1500), @VersionName(name="net.minecraft.datafixers.TypeReferences", begin=1500, end=1501), @VersionName(name="net.minecraft.datafixer.TypeReferences", begin=1501)})
public interface DataUpdateTypesV1300 extends WrapperObject, DSLV1300.TypeReference
{
    @WrapperCreator
    static DataUpdateTypesV1300 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypesV1300.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor({@VersionName(name="field_16592", end=1400), @VersionName(name="ITEM_STACK", begin=1400)})
    DSLV1300.TypeReference staticItemStack();
    static DSLV1300.TypeReference itemStack()
    {
        return create(null).staticItemStack();
    }
}
