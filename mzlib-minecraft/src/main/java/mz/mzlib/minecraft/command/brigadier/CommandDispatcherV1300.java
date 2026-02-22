package mz.mzlib.minecraft.command.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapFieldAccessor;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@VersionRange(begin = 1300)
@WrapClassForName("com.mojang.brigadier.CommandDispatcher")
public interface CommandDispatcherV1300 extends WrapperObject
{
    WrapperFactory<CommandDispatcherV1300> FACTORY = WrapperFactory.of(CommandDispatcherV1300.class);

    @Override
    CommandDispatcher<?> getWrapped();

    @WrapFieldAccessor("root")
    CommandNodeV1300 getRoot();
}
