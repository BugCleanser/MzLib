package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.event.FishingHookClearEvent;
import mz.lib.minecraft.bukkit.event.FishingHookClearEvent.FishingHookClearReason;
import mz.lib.minecraft.bukkit.nothing.NothingBukkit;
import mz.lib.minecraft.bukkit.nothing.NothingBukkitInject;
import mz.lib.minecraft.bukkit.wrappedobc.ObcEntity;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.nothing.LocalVar;
import mz.lib.nothing.NothingLocation;
import mz.lib.wrapper.WrappedObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.HumanEntity;

import java.util.Optional;

@WrappedBukkitClass({@VersionName(value="nms.EntityFishingHook",maxVer=17),@VersionName(value="net.minecraft.world.entity.projectile.EntityFishingHook",minVer=17)})
public interface NmsEntityFishingHook extends NmsIProjectile, NothingBukkit
{
	@NothingBukkitInject(name = @VersionName(maxVer = 13, value = "p"), args = {}, location = NothingLocation.FRONT)
	default Optional<Boolean> removeIfInvalidOverwrite()
	{
		return removeIfInvalidOverwrite(WrappedObject.wrap(ObcEntity.class,((FishHook) this.getBukkitEntity()).getShooter()).getHandle().cast(NmsEntityHuman.class));
	}
	@NothingBukkitInject(name = @VersionName(minVer = 13, value = "a"), args = {NmsEntityHuman.class}, location = NothingLocation.FRONT)
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
