package mz.lib.minecraft.event.entity.player;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.VersionalName;
import mz.lib.module.MzModule;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.bukkit.nms.NmsPacketPlayInSettings;
import mz.lib.minecraft.bukkit.nms.NmsPlayerConnection;
import mz.lib.minecraft.nothing.*;
import mz.lib.minecraft.wrapper.VersionalWrappedClass;
import mz.lib.nothing.*;
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
	
	public static class Module extends MzModule
	{
		public static Module instance=new Module();
		
		public Set<Player> readyPlayers=new CopyOnWriteArraySet<>();
		
		@Override
		public void onLoad()
		{
			depend(NothingRegistrar.instance);
			readyPlayers.addAll(Bukkit.getOnlinePlayers());
			reg(NothingPlayerConnection.class);
		}
		
		@EventHandler
		public void onPlayerQuit(PlayerQuitEvent event)
		{
			readyPlayers.remove(event.getPlayer());
		}
		
		@VersionalWrappedClass({@VersionalName(value="nms.PlayerConnection",maxVer=17),@VersionalName(value="net.minecraft.server.network.PlayerConnection",minVer=17)})
		public interface NothingPlayerConnection extends NmsPlayerConnection, VersionalNothing
		{
			@VersionalNothingInject(name = @VersionalName("a"), args = {NmsPacketPlayInSettings.class}, location = NothingLocation.RETURN)
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
