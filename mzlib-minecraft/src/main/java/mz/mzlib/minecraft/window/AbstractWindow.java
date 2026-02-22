package mz.mzlib.minecraft.window;

import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.wrapper.WrapperFactory;

/**
 * @see WindowAbstract
 */
@Deprecated
@Compound
public interface AbstractWindow extends WindowAbstract
{
    WrapperFactory<AbstractWindow> FACTORY = WrapperFactory.of(AbstractWindow.class);
}
