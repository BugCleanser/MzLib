package mz.mzlib.minecraft.serialization;

import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name = "com.mojang.serialization.Codec", begin = 1600))
public interface CodecV1600<T> extends WrapperObject, EncoderV1600<T>, DecoderV1600<T>
{
    WrapperFactory<CodecV1600<?>> FACTORY = RuntimeUtil.cast(WrapperFactory.of(CodecV1600.class));
    @Deprecated
    @WrapperCreator
    static CodecV1600<?> create(Object wrapped)
    {
        return WrapperObject.create(CodecV1600.class, wrapped);
    }

    interface IWrapper<T extends WrapperObject> extends EncoderV1600.IWrapper<T>, DecoderV1600.IWrapper<T>
    {
        @Override
        CodecV1600<?> getBase();
    }

    class Wrapper<T extends WrapperObject> implements IWrapper<T>
    {
        CodecV1600<?> base;
        WrapperFactory<T> type;
        public Wrapper(CodecV1600<?> base, WrapperFactory<T> type)
        {
            this.base = base;
            this.type = type;
        }

        @Override
        public CodecV1600<?> getBase()
        {
            return this.base;
        }

        @Override
        public WrapperFactory<T> getType()
        {
            return this.type;
        }
    }
}
