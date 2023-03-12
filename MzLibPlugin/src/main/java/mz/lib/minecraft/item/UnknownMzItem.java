package mz.lib.minecraft.item;

import org.bukkit.NamespacedKey;

public interface UnknownMzItem extends MzItem
{
	@Override
	default NamespacedKey getKey()
	{
		if(getItemStack()==null)
			return new NamespacedKey("mzlib","unknown");
		return MzItem.getKey(getItemStack().getRaw());
	}
}
