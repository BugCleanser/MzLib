package mz.mzlib.minecraft.entity.data;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftInnerClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.function.Function;

// TODO
@WrapMinecraftClass(@VersionName(name="net.minecraft.entity.data.DataTracker"))
public interface EntityDataTracker extends WrapperObject
{
    @WrapperCreator
    static EntityDataTracker create(Object wrapped)
    {
        return WrapperObject.create(EntityDataTracker.class, wrapped);
    }
    
    @WrapMinecraftInnerClass(outer=EntityDataTracker.class, name=@VersionName(name="Entry"))
    interface Entry extends WrapperObject
    {
        @WrapperCreator
        static Entry create(Object wrapped)
        {
            return WrapperObject.create(Entry.class, wrapped);
        }
        
        static Entry newInstance(EntityDataType type, WrapperObject value)
        {
            return newInstance0(type, value.getWrapped());
        }
        static Entry newInstance0(EntityDataType type, Object value)
        {
            return Entry.create(null).staticNewInstance0(type, value);
        }
        @WrapConstructor
        Entry staticNewInstance0(EntityDataType type, Object value);
        
        @WrapMinecraftMethod(@VersionName(name="toSerialized"))
        EntityData getData();
    }
    
    @WrapMinecraftInnerClass(outer=EntityDataTracker.class, name=@VersionName(name="SerializedEntry"))
    interface EntityData extends WrapperObject
    {
        @WrapperCreator
        static EntityData create(Object wrapped)
        {
            return WrapperObject.create(EntityData.class, wrapped);
        }
        
        @WrapMinecraftFieldAccessor(@VersionName(name="id"))
        int getIndex();
        
        @WrapMinecraftFieldAccessor(@VersionName(name="handler"))
        EntityDataHandler getHandler();
        
        default EntityDataType getType()
        {
            return EntityDataType.newInstance(this.getIndex(), this.getHandler());
        }
        
        @WrapMinecraftFieldAccessor(@VersionName(name="value"))
        Object getValue0();
        default <T extends WrapperObject> T getValue(Function<Object, T> wrapperCreator)
        {
            return wrapperCreator.apply(getValue0());
        }
    }
}
