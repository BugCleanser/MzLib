package mz.mzlib.minecraft.component;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Option;
import mz.mzlib.util.proxy.IteratorProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Iterator;

@WrapMinecraftClass(@VersionName(name = "net.minecraft.component.ComponentMap", begin = 2005))
public interface ComponentMapV2005 extends WrapperObject, Iterable<ComponentMapV2005.Entry>
{
    WrapperFactory<ComponentMapV2005> FACTORY = WrapperFactory.of(ComponentMapV2005.class);
    @Deprecated
    @WrapperCreator
    static ComponentMapV2005 create(Object wrapped)
    {
        return WrapperObject.create(ComponentMapV2005.class, wrapped);
    }

    @Override
    Iterable<Object> getWrapped();

    <T> T get0(ComponentKeyV2005<T> key);
    @SpecificImpl("get0")
    @VersionRange(end = 2105)
    @WrapMinecraftMethod(@VersionName(name = "get"))
    <T> T get0V_2105(ComponentKeyV2005<T> key);
    @SpecificImpl("get0")
    @VersionRange(begin = 2105)
    default <T> T get0V2105(ComponentKeyV2005<T> key)
    {
        return this.castTo(ComponentsAccessV2105.FACTORY).get0(key);
    }

    default <T extends WrapperObject> Option<T> get(ComponentKeyV2005.Wrapper<T> key)
    {
        return Option.fromNullable(this.get0(key.getBase())).map(key.getType()::create);
    }

    @Deprecated
    default <T extends WrapperObject> Option<T> get(ComponentKeyV2005<?> key, WrapperFactory<T> type)
    {
        return this.get(new ComponentKeyV2005.Wrapper<>(key, type));
    }

    default <T extends WrapperObject> Option<T> copy(ComponentKeyV2005.Wrapper<T> key)
    {
        return this.get(key).map(key::copy);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    default Iterator<Entry> iterator()
    {
        return new IteratorProxy<>(this.getWrapped().iterator(), Entry.FACTORY::create);
    }

    @WrapMinecraftClass(@VersionName(name = "net.minecraft.component.Component", begin = 2005))
    interface Entry extends WrapperObject
    {
        WrapperFactory<Entry> FACTORY = WrapperFactory.of(Entry.class);
        @Deprecated
        @WrapperCreator
        static Entry create(Object wrapped)
        {
            return WrapperObject.create(Entry.class, wrapped);
        }

        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2443"))
        ComponentKeyV2005<?> getType();
        @WrapMinecraftFieldAccessor(@VersionName(name = "comp_2444"))
        Object getValue();
    }
}
