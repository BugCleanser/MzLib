package mz.mzlib.minecraft.inventory;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

//TODO
@WrapMinecraftClass(@VersionName(name="net.minecraft.inventory.Inventory"))
public interface Inventory extends WrapperObject
{
    @WrapperCreator
    static Inventory create(Object wrapped)
    {
        return WrapperObject.create(Inventory.class, wrapped);
    }
}
