package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.*;

import java.util.Objects;

@WrapMinecraftClass({@VersionName(name="mz.mzlib.minecraft.entity.data.EntityDataKey$WrappedV_900", remap=false, end=900), @VersionName(name="net.minecraft.entity.data.TrackedData", begin=900)})
public interface EntityDataKey<T> extends WrapperObject
{
    WrapperFactory<EntityDataKey<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(EntityDataKey.class));
    @Deprecated
    @WrapperCreator
    static EntityDataKey<?> create(Object wrapped)
    {
        return WrapperObject.create(EntityDataKey.class, wrapped);
    }
    
    int getIndex();
    
    @SpecificImpl("getIndex")
    @VersionRange(end=900)
    default int getIndexV_900()
    {
        return ((WrappedV_900)this.getWrapped()).index;
    }
    
    @SpecificImpl("getIndex")
    @VersionRange(begin=900)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_13816", end=1400), @VersionName(name="id", begin=1400)})
    int getIndexV900();
    
    default int getTypeIdV_900()
    {
        return ((WrappedV_900)this.getWrapped()).typeId;
    }
    
    /**
     * @param typeId {@link WrappedV_900#typeId}
     */
    static <T> EntityDataKey<T> newInstanceV_900(int index, byte typeId)
    {
        return newInstance(index, RuntimeUtil.cast(EntityDataHandler.FACTORY.create(typeId)));
    }
    
    static <T> EntityDataKey<T> newInstance(int index, EntityDataHandler<T> handler)
    {
        return FACTORY.getStatic().static$newInstance(index, handler);
    }
    
    @WrapConstructor
    <T1> EntityDataKey<T1> static$newInstance(int index, EntityDataHandler<T1> handler);
    
    class WrappedV_900
    {
        public int index;
        /**
         * Byte -> 0
         * Short -> 1
         * Integer -> 2
         * Float -> 3
         * String -> 4
         * ItemStack -> 5
         * BlockPos -> 6
         * EulerAngle -> 7
         */
        public byte typeId;
        public WrappedV_900(int index, byte typeId)
        {
            this.index = index;
            this.typeId = typeId;
        }
        
        @Override
        public boolean equals(Object o)
        {
            if(!(o instanceof WrappedV_900))
                return false;
            WrappedV_900 that = (WrappedV_900)o;
            return index==that.index;
        }
        @Override
        public int hashCode()
        {
            return Objects.hashCode(index);
        }
    }
}
