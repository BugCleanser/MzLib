package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.util.collection.IndexedIterableV1400;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.proxy.SetProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Set;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.util.registry.MutableRegistry", end = 1300),
        @VersionName(name = "net.minecraft.util.registry.Registry", begin = 1300, end = 1903),
        @VersionName(name = "net.minecraft.registry.Registry", begin = 1903)
    }
)
public interface Registry<T> extends WrapperObject
{
    WrapperFactory<Registry<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(Registry.class));

    @WrapMinecraftMethod(@VersionName(name = "getId", begin = 1300))
    Identifier getIdV1300(WrapperObject value);

    default Set<Identifier> getIdsV_1300()
    {
        return new SetProxy<>(this.getIds0V_1300(), FunctionInvertible.wrapper(Identifier.FACTORY));
    }

    WrapperObject get(Identifier id);

    @VersionRange(end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "get"))
    Object get0V_1300(Object id);

    @SpecificImpl("get")
    @VersionRange(end = 1300)
    default WrapperObject getV_1300(Identifier id)
    {
        return WrapperObject.FACTORY.create(this.get0V_1300(id.getWrapped()));
    }
    @SpecificImpl("get")
    @VersionRange(begin = 1300)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "getByIdentifier", end = 1400),
            @VersionName(name = "get", begin = 1400)
        }
    )
    WrapperObject getV1300(Identifier id);

    WrapperObject get(int rawId);
    @SpecificImpl("get")
    @VersionRange(end = 1400)
    default WrapperObject getV_1400(int rawId)
    {
        return this.castTo(RegistrySimple.FACTORY).get(rawId);
    }
    @SpecificImpl("get")
    @VersionRange(begin = 1400)
    default WrapperObject getV1400(int rawId)
    {
        return this.v1400().get(rawId);
    }

    default V1400<T> v1400()
    {
        return RuntimeUtil.cast(this.as(V1400.FACTORY));
    }


    @VersionRange(end = 1300)
    @WrapMinecraftMethod({ @VersionName(name = "keySet"), @VersionName(name = "getKeySet") })
    Set<Object> getIds0V_1300();

    class Wrapper<T extends WrapperObject>
    {
        Registry<?> base;
        WrapperFactory<T> type;
        public Wrapper(Registry<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
    }

    @WrapSameClass(Registry.class)
    interface V1400<T> extends Registry<T>, IndexedIterableV1400<T>
    {
        WrapperFactory<V1400<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(V1400.class));

        @Override
        default WrapperObject get(int rawId)
        {
            return this.as(IndexedIterableV1400.FACTORY).get(rawId);
        }
    }
}
