package mz.lib.minecraft.bukkit.nms;

import mz.lib.TypeUtil;
import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.itemstack.ItemStackBuilder;
import mz.lib.minecraft.nothing.VersionalNothingInject;
import mz.lib.minecraft.nothing.VersionalNothing;
import mz.lib.minecraft.bukkit.obc.ObcChatMessage;
import mz.lib.minecraft.bukkit.obc.ObcEntity;
import mz.lib.minecraft.bukkit.obc.ObcItemStack;
import mz.lib.minecraft.wrapper.*;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedMethod;
import mz.lib.wrapper.WrappedObject;
import mz.mzlib.*;
import mz.mzlib.wrapper.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;

import java.util.Optional;

@VersionalWrappedClass({@VersionalName(value="nms.Entity",maxVer=17),@VersionalName(value="net.minecraft.world.entity.Entity",minVer=17)})
public interface NmsEntity extends NmsICommandListener, VersionalNothing
{
	static NmsEntity newInstanceV13(NmsEntityTypes type,NmsWorld world)
	{
		return WrappedObject.wrap(NmsEntity.class,null).staticNewInstanceV13(type,world);
	}
	static NmsEntity newInstanceV_13(NmsWorld world)
	{
		return WrappedObject.wrap(NmsEntity.class,null).staticNewInstanceV_13(world);
	}
	static NmsDataWatcherObject getCustomNameType()
	{
		return WrappedObject.wrap(NmsEntity.class,null).staticGetCustomNameType();
	}
	static NmsDataWatcherObject getCustomNameTypeV_13()
	{
		return TypeUtil.cast(getCustomNameType());
	}
	static NmsDataWatcherObject getCustomNameTypeV13()
	{
		return TypeUtil.cast(getCustomNameType());
	}
	static <T> String getCustomName(NmsDataWatcherItem item)
	{
		if(Server.instance.v13)
		{
			Object o=TypeUtil.<Optional<?>,Object>cast(item.getValue());
			if(o==null)
				return null;
			return ObcChatMessage.fromComponentV13(WrappedObject.wrap(NmsIChatBaseComponent.class,o));
		}
		else
			return TypeUtil.cast(item.getValue());
	}
	static NmsDataWatcherItem newCustomName(String name)
	{
		if(Server.instance.v13)
			return NmsDataWatcherItem.newInstance(getCustomNameTypeV13(),Optional.of(ObcChatMessage.fromStringOrNullV13(name).getRaw()));
		else
			return NmsDataWatcherItem.newInstance(getCustomNameTypeV_13(),name);
	}
	static NmsDataWatcherObject getCustomNameVisibleType()
	{
		return WrappedObject.wrap(NmsEntity.class,null).staticGetCustomNameVisibleType();
	}
	@VersionalWrappedConstructor(minVer=13)
	NmsEntity staticNewInstanceV13(NmsEntityTypes type,NmsWorld world);
	@VersionalWrappedConstructor(maxVer=13)
	NmsEntity staticNewInstanceV_13(NmsWorld world);
	@WrappedMethod("getBukkitEntity")
	Entity getBukkitEntity();
	@VersionalWrappedMethod(@VersionalName({"load","g","f"}))
	void load(NmsNBTTagCompound nbt);
	@WrappedMethod({"save","f"})
	NmsNBTTagCompound save(NmsNBTTagCompound nbt);
	@VersionalWrappedMethod({@VersionalName(value="getEntityType",minVer=13),@VersionalName(value="ad",minVer=18)})
	NmsEntityTypes getEntityTypeV13();
	@VersionalWrappedMethod({@VersionalName(maxVer=17, value="h"),@VersionalName(minVer=17, value="f")})
	double squaredDistanceTo(NmsEntity target);
//	@WrappedBukkitFieldGetter({@VersionName("random"),@VersionName(minVer=17, maxVer=18, value="Q"),@VersionName(minVer=18, value="R")})
//	Random getRandom();
	
	default String getTranslateKey()
	{
		if(Server.instance.v13)
			return getEntityTypeV13().getTranslateKeyV13();
		else
			return NmsEntityTypes.getTranslateKeyV_13(this);
	}
	@VersionalNothingInject(name=@VersionalName(value="a"),args={NmsItemStack.class,float.class},location=NothingLocation.FRONT)
	default Optional<NmsEntityItem> drop(@LocalVar(1) NmsItemStack item,@LocalVar(2) float height)
	{
		if(item==null||item.getRaw()==null||ItemStackBuilder.isAir(ObcItemStack.asBukkitCopy(item)))
			return Nothing.doReturn(WrappedObject.wrap(NmsEntityItem.class,null));
		if(this.is(NmsEntityLiving.class)&&!this.cast(NmsEntityLiving.class).isForceDrops())
		{
			this.cast(NmsEntityLiving.class).getDrops().add(ObcItemStack.asBukkitCopy(item));
			return Nothing.doReturn(WrappedObject.wrap(NmsEntityItem.class,null));
		}
		Item r=this.getBukkitEntity().getWorld().dropItem(this.getBukkitEntity().getLocation().add(0,height,0),ObcItemStack.asBukkitCopy(item));
		r.setPickupDelay(10);
		EntityDropItemEvent event=new EntityDropItemEvent(this.getBukkitEntity(),r);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled())
		{
			r.remove();
			return Nothing.doReturn(WrappedObject.wrap(NmsEntityItem.class,null));
		}
		return Nothing.doReturn(WrappedObject.wrap(ObcEntity.class,r).getHandle().cast(NmsEntityItem.class));
	}
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=13, value="aB"),@VersionalName(value="aE",minVer=13,maxVer=14),@VersionalName(minVer=14, maxVer=17, value={"aq","az"}),@VersionalName(minVer=17, maxVer=18, value="aJ"),@VersionalName(minVer=18,maxVer=18.2f, value="aL"),@VersionalName(minVer=18.2f, value="aM")})
	NmsDataWatcherObject staticGetCustomNameType();
	@VersionalWrappedFieldAccessor({@VersionalName(maxVer=13, value="aC"),@VersionalName(value="aF",minVer=13,maxVer=14),@VersionalName(minVer=14, maxVer=17, value={"ar","aA"}),@VersionalName(minVer=17, maxVer=18, value="aK"),@VersionalName(minVer=18,maxVer=18.2f, value="aM"),@VersionalName(minVer=18.2f, value="aN")})
	NmsDataWatcherObject staticGetCustomNameVisibleType();
}
