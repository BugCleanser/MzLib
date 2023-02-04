package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.DoubleKeyList;
import mz.lib.minecraft.bukkit.MzLib;
import mz.lib.minecraft.bukkit.ProtocolUtil;
import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.module.AbsModule;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayInSettings",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayInSettings",minVer=17)})
public interface NmsPacketPlayInSettings extends NmsPacket
{
	DoubleKeyList<Plugin,Player,Runnable> tasks=new DoubleKeyList<>(new HashMap<>(),new HashMap<>());
	
	default void runOnReceive(Plugin plugin,Player player,Runnable task)
	{
		tasks.add(plugin,player,task);
	}
	
	@WrappedBukkitFieldAccessor({@VersionName("locale"),@VersionName(value="a",maxVer=16),@VersionName(value="b",minVer=17)})
	String getLocale();
	@WrappedBukkitFieldAccessor({@VersionName("locale"),@VersionName(value="a",maxVer=16),@VersionName(value="b",minVer=17)})
	NmsPacketPlayInSettings setLocale(String locale);
	
	class Module extends AbsModule
	{
		public static Module instance=new Module();
		public Module()
		{
			super(MzLib.instance);
			reg(new ProtocolUtil.ReceiveListener<>(EventPriority.MONITOR,NmsPacketPlayInSettings.class,(pl,pa,c)->
			{
				tasks.remove2(pl).forEach(t->
				{
					try
					{
						t.run();
					}
					catch(Throwable e)
					{
						e.printStackTrace();
					}
				});
			}));
		}
		
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		void onPluginDisable(PluginDisableEvent event)
		{
			tasks.remove1(event.getPlugin());
		}
	}
}
