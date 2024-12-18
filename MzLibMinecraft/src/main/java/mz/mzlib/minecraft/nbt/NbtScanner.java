package mz.mzlib.minecraft.nbt;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass({@VersionName(name="net.minecraft.nbt.StringNbtReader")})
public interface NbtScanner extends WrapperObject {

    @WrapperCreator
    static NbtScanner create(Object wrapped)
    {
        return WrapperObject.create(NbtScanner.class, wrapped);
    }

    static NbtCompound parseCompound(String context){
        return create(null).staticParseCompound(context);
    }

    @WrapMinecraftMethod(@VersionName(name="parse"))
    NbtCompound staticParseCompound(String context);
}


