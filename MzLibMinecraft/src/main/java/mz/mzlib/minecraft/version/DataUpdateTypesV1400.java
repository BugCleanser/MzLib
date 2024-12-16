package mz.mzlib.minecraft.version;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.datafixers.DSLV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.datafixer.TypeReferences", begin=1400))
public interface DataUpdateTypesV1400 extends WrapperObject, DSLV1400.TypeReference
{
    @WrapperCreator
    static DataUpdateTypesV1400 create(Object wrapped)
    {
        return WrapperObject.create(DataUpdateTypesV1400.class, wrapped);
    }
    
    @WrapMinecraftFieldAccessor(@VersionName(name="ITEM_STACK"))
    DSLV1400.TypeReference staticItemStack();
    static DSLV1400.TypeReference itemStack()
    {
        return create(null).staticItemStack();
    }
}
