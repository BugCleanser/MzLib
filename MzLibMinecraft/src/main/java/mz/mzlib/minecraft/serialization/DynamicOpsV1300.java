package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=1300)
@WrapMinecraftClass({@VersionName(name="com.mojang.datafixers.types.DynamicOps", end=1600), @VersionName(name="com.mojang.serialization.DynamicOps", begin=1600)})
public interface DynamicOpsV1300<T> extends WrapperObject
{
    WrapperFactory<DynamicOpsV1300<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(DynamicOpsV1300.class));
    
    class Wrapper<T extends WrapperObject>
    {
        DynamicOpsV1300<?> base;
        WrapperFactory<T> type;
        public Wrapper(DynamicOpsV1300<?> base,  WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        
        public DynamicOpsV1300<?> getBase()
        {
            return this.base;
        }
        public WrapperFactory<T> getType()
        {
            return this.type;
        }
    }
}
