package mz.lib.minecraft.bukkit.wrappedobc;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.wrappednms.NmsItemStack;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitObject;
import mz.lib.wrapper.WrappedFieldAccessor;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@WrappedBukkitClass(@VersionName("obc.inventory.CraftItemStack"))
public interface ObcItemStack extends WrappedBukkitObject
{
	@WrappedMethod("asCraftMirror")
	ObcItemStack staticAsCraftMirror(NmsItemStack nms);
	static ObcItemStack asCraftMirror(NmsItemStack nms)
	{
		return WrappedObject.getStatic(ObcItemStack.class).staticAsCraftMirror(nms);
	}
	static ObcItemStack ensure(ItemStack item)
	{
		if(item==null)
			item=new ItemStack(Material.AIR);
		if(WrappedObject.getRawClass(ObcItemStack.class).isAssignableFrom(item.getClass()))
			return WrappedObject.wrap(ObcItemStack.class,item);
		else
			return asCraftCopy(item);
	}
	
	@WrappedFieldAccessor("handle")
	NmsItemStack getHandle();
	@WrappedFieldAccessor("handle")
	ObcItemStack setHandle(NmsItemStack handle);
	
	static int getCount(ItemStack is)
	{
		if(is==null||is.getType()==Material.AIR)
			return 0;
		return is.getAmount();
	}
	static boolean isAir(ItemStack is)
	{
		return getCount(is)<1;
	}
	static ObcItemStack asCraftCopy(ItemStack item)
	{
		return WrappedObject.getStatic(ObcItemStack.class).staticAsCraftCopy(item);
	}
	static ItemStack asBukkitCopy(NmsItemStack nms)
	{
		return WrappedObject.getStatic(ObcItemStack.class).staticAsBukkitCopy(nms);
	}
	static NmsItemStack asNMSCopy(ItemStack is)
	{
		return WrappedObject.getStatic(ObcItemStack.class).staticAsNMSCopy(is);
	}
	
	@Override
	ItemStack getRaw();
	@WrappedMethod("asCraftCopy")
	ObcItemStack staticAsCraftCopy(ItemStack item);
	@WrappedMethod("asBukkitCopy")
	ItemStack staticAsBukkitCopy(NmsItemStack nms);
	@WrappedMethod("asNMSCopy")
	NmsItemStack staticAsNMSCopy(ItemStack item);
}
