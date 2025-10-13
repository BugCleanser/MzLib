package mz.mzlib.minecraft.serialization;

import com.google.gson.JsonElement;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="com.mojang.datafixers.types.JsonOps", end=1600), @VersionName(name="com.mojang.serialization.JsonOps", begin=1600)})
public interface JsonOpsV1300 extends WrapperObject, DynamicOpsV1300<JsonElement>
{
    WrapperFactory<JsonOpsV1300> FACTORY = WrapperFactory.of(JsonOpsV1300.class);
    
    static JsonOpsV1300 instance()
    {
        return FACTORY.getStatic().staticInstance();
    }
    @WrapMinecraftFieldAccessor(@VersionName(name="INSTANCE"))
    JsonOpsV1300 staticInstance();
}
