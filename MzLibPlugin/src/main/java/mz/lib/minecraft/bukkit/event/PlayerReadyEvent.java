package mz.lib.minecraft.bukkit.event;

import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.nothing.NothingBukkit;
import mz.lib.minecraft.bukkit.nothing.NothingBukkitInject;
import mz.lib.minecraft.bukkit.nothing.NothingRegistrar;
import mz.lib.minecraft.bukkit.wrappednms.NmsPacketPlayInSettings;
import mz.lib.minecraft.bukkit.wrappednms.NmsPlayerConnection;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.nothing.Nothing;
import mz.lib.nothing.NothingLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class PlayerReadyEvent extends PlayerEvent
{
	public PlayerReadyEvent(Player player)
	{
		super(player);
	}
	
	public static class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance,NothingRegistrar.instance);
		}
		
		public Set<Player> readyPlayers=new CopyOnWriteArraySet<>();
		
		@Override
		public void onEnable()
		{
			readyPlayers.addAll(Bukkit.getOnlinePlayers());
			reg(NothingPlayerConnection.class);
		}
		
		@EventHandler
		public void onPlayerQuit(PlayerQuitEvent event)
		{
			readyPlayers.remove(event.getPlayer());
		}
		
		@WrappedBukkitClass({@VersionName(value="nms.PlayerConnection",maxVer=17),@VersionName(value="net.minecraft.server.network.PlayerConnection",minVer=17)})
		public interface NothingPlayerConnection extends NmsPlayerConnection, NothingBukkit
		{
			@NothingBukkitInject(name = @VersionName("a"), args = {NmsPacketPlayInSettings.class}, location = NothingLocation.RETURN)
			default Optional<Void> receiveAfter()
			{
				Player player=(Player) this.getPlayer().getBukkitEntity();
				if(instance.readyPlayers.add(player))
				{
					Bukkit.getPluginManager().callEvent(new PlayerReadyEvent(player));
				}
				return Nothing.doContinue();
			}
		}
	}
	
	public static HandlerList handlers=new HandlerList();
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
	public HandlerList getHandlers()
	{
		return getHandlerList();
	}
}
