package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.wrapper.WrappedArray;
import mz.lib.wrapper.WrappedArrayClass;
import mz.lib.wrapper.WrappedObject;

@WrappedArrayClass(NmsItemStack.class)
public interface NmsItemStackArray extends WrappedArray<NmsItemStack>
{
	static NmsItemStackArray newInstance(int length)
	{
		return (NmsItemStackArray) WrappedObject.getStatic(NmsItemStackArray.class).staticNewInstance(length);
	}
}
