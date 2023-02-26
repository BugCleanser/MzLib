package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.minecraft.wrapper.VersionalWrappedFieldAccessor;
import mz.lib.minecraft.wrapper.VersionalWrappedMethod;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

@VersionalWrappedClass({@VersionalName(value="nms.EntityLiving",maxVer=17),@VersionalName(value="net.minecraft.world.entity.EntityLiving",minVer=17)})
public interface NmsEntityLiving extends NmsEntity
{
	@VersionalWrappedFieldAccessor(@VersionalName(value="at",maxVer=13))
	NmsDataWatcherObject staticGetUsingItemDataWatcherObjectV_13();
	static NmsDataWatcherObject getUsingItemDataWatcherObjectV_13()
	{
		return WrappedObject.getStatic(NmsEntityLiving.class).staticGetUsingItemDataWatcherObjectV_13();
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName("forceDrops"))
	boolean isForceDrops();
	
	@VersionalWrappedFieldAccessor(@VersionalName("drops"))
	ArrayList<ItemStack> getDrops();
	
	@VersionalWrappedMethod({@VersionalName(value="getRandom",maxVer=19),@VersionalName(value="@0",minVer=17,maxVer=19)})
	Random getRandomV_19();
	@VersionalWrappedMethod(@VersionalName(value={"getRandom","@0"},minVer=19))
	NmsRandomSourceV19 getRandomV19();
}
