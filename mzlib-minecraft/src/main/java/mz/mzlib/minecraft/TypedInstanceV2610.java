package mz.mzlib.minecraft;

import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapMethod;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 2610)
@WrapClassForName("net.minecraft.core.TypedInstance")
public interface TypedInstanceV2610<T> extends WrapperObject
{
    WrapperFactory<TypedInstanceV2610<?>> FACTORY = WrapperFactory.of(RuntimeUtil.castClass(TypedInstanceV2610.class));

    @WrapMethod("is")
    boolean isType(T type);

    class Wrapper<T extends WrapperObject>
    {
        TypedInstanceV2610<?> base;
        WrapperFactory<T> type;
        public Wrapper(TypedInstanceV2610<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }
        public static <T extends WrapperObject> Wrapper<T> of(TypedInstanceV2610<?> base, WrapperFactory<T> type)
        {
            return new Wrapper<>(base, type);
        }

        public TypedInstanceV2610<?> getBase()
        {
            return this.base;
        }
        public WrapperFactory<T> getType()
        {
            return this.type;
        }

        public boolean isType(T type)
        {
            return this.getBase().isType(RuntimeUtil.cast(type.getWrapped()));
        }
    }
}
