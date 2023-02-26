package mz.lib.minecraft.bukkit.nms;

import mz.lib.wrapper.WrappedArray;
import mz.lib.wrapper.WrappedArrayClass;
import mz.lib.wrapper.WrappedObject;

@WrappedArrayClass(NmsMapIcon.class)
public interface NmsMapIconArray extends WrappedArray<NmsMapIcon>
{
	static NmsMapIconArray newInstance(int length)
	{
		return (NmsMapIconArray)WrappedObject.getStatic(NmsMapIconArray.class).staticNewInstance(length);
	}
}
