package mz.mzlib.minecraft.incomprehensible;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.ActionResult;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin=900, end=2102)
@WrapMinecraftClass(@VersionName(name="net.minecraft.util.TypedActionResult"))
public interface TypedActionResultV900_2102<T> extends WrapperObject
{
    WrapperFactory<TypedActionResultV900_2102<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(TypedActionResultV900_2102.class));
    
    @WrapMinecraftMethod(@VersionName(name="getActionResult"))
    ActionResult getActionResult();
    
    @WrapMinecraftMethod(@VersionName(name="getObject"))
    T getObject();
    
    class Wrapper<T extends WrapperObject>
    {
        TypedActionResultV900_2102<?> base;
        WrapperFactory<T> type;
        public Wrapper(TypedActionResultV900_2102<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        
        public TypedActionResultV900_2102<?> getBase()
        {
            return this.base;
        }
        public WrapperFactory<T> getType()
        {
            return this.type;
        }
        
        public ActionResult getActionResult()
        {
            return this.getBase().getActionResult();
        }
        public T getObject()
        {
            return this.getType().create(this.getBase().getObject());
        }
    }
}
