package mz.mzlib.minecraft.window;

import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

@Compound
public interface AbstractWindowCustom extends Window
{
    @WrapperCreator
    static AbstractWindowCustom create(Object wrapped)
    {
        return WrapperObject.create(AbstractWindowCustom.class, wrapped);
    }
}
