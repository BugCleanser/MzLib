package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.nothing.*;
import mz.lib.wrapper.*;
import mz.lib.minecraft.VersionalName;
import mz.lib.minecraft.bukkitlegacy.event.FishingHookClearEvent;
import mz.lib.minecraft.bukkitlegacy.event.FishingHookClearEvent.FishingHookClearReason;
import mz.lib.minecraft.bukkitlegacy.nothing.*;
import mz.lib.minecraft.bukkit.obc.ObcEntity;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.nothing.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.HumanEntity;

import java.util.*;

@VersionalWrappedClass({@VersionalName(value="nms.EntityFishingHook",maxVer=17),@VersionalName(value="net.minecraft.world.entity.projectile.EntityFishingHook",minVer=17)})
public interface NmsEntityFishingHook extends NmsIProjectile, VersionalNothing
{
	@VersionalNothingInject(name = @VersionalName(maxVer = 13, value = "p"), args = {}, location = NothingLocation.FRONT)
	default Optional<Boolean> removeIfInvalidOverwrite()
	{
		return removeIfInvalidOverwrite(WrappedObject.wrap(ObcEntity.class,((FishHook) this.getBukkitEntity()).getShooter()).getHandle().cast(NmsEntityHuman.class));
	}
	@VersionalNothingInject(name = @VersionalName(minVer = 13, value = "a"), args = {NmsEntityHuman.class}, location = NothingLocation.FRONT)
	default Optional<Boolean> removeIfInvalidOverwrite(@LocalVar(1) NmsEntityHuman player)
	{
		HumanEntity p=(HumanEntity) player.getBukkitEntity();
		if(p.isDead()||p.getHealth()<=0)
		{
			@SuppressWarnings("deprecation") FishingHookClearEvent event=new FishingHookClearEvent((FishHook) this.getBukkitEntity(),FishingHookClearReason.NOPLAYER);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled())
			{
				this.getBukkitEntity().remove();
				event.done();
				return Optional.of(true);
			}
			else
				event.done();
		}
		else
		{
			if((p.getInventory().getItemInMainHand()==null||p.getInventory().getItemInMainHand().getType()!=Material.FISHING_ROD)&&(p.getInventory().getItemInOffHand()==null||p.getInventory().getItemInOffHand().getType()!=Material.FISHING_ROD))
			{
				FishingHookClearEvent event=new FishingHookClearEvent((FishHook) this.getBukkitEntity(),FishingHookClearReason.NOROD);
				Bukkit.getPluginManager().callEvent(event);
				if(!event.isCancelled())
				{
					this.getBukkitEntity().remove();
					event.done();
					return Optional.of(true);
				}
				else
					event.done();
			}
			if(this.squaredDistanceTo(player)>1024)
			{
				FishingHookClearEvent event=new FishingHookClearEvent((FishHook) this.getBukkitEntity(),FishingHookClearReason.TOOFAR);
				Bukkit.getPluginManager().callEvent(event);
				if(!event.isCancelled())
				{
					this.getBukkitEntity().remove();
					event.done();
					return Optional.of(true);
				}
				else
					event.done();
			}
		}
		return Optional.of(false);
	}
}
