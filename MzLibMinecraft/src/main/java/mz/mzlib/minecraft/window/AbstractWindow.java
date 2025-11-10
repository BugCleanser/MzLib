package mz.mzlib.minecraft.window;

import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

/**
 * @see WindowAbstract
 */
@Deprecated
@Compound
public interface AbstractWindow extends WindowAbstract
{
    WrapperFactory<AbstractWindow> FACTORY = WrapperFactory.of(AbstractWindow.class);
    @Deprecated
    @WrapperCreator
    static WindowAbstract create(Object wrapped)
    {
        return WrapperObject.create(AbstractWindow.class, wrapped);
    }
}
