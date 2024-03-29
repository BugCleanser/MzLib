package mz.lib.minecraft.bukkit.wrappednms;

import mz.lib.minecraft.bukkit.VersionName;
import mz.lib.minecraft.bukkit.entity.EntityTypeUtil;
import mz.lib.minecraft.bukkit.wrapper.BukkitWrapper;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitClass;
import mz.lib.minecraft.bukkit.wrapper.WrappedBukkitFieldAccessor;
import org.bukkit.entity.EntityType;

import java.util.UUID;

@WrappedBukkitClass({@VersionName(value="nms.PacketPlayOutSpawnEntity",maxVer=17),@VersionName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity",minVer=17)})
public interface NmsPacketPlayOutSpawnEntity extends NmsPacket
{
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	int getEntityId();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntity setEntityId(int id);
	
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	UUID getUUID();
	@WrappedBukkitFieldAccessor(@VersionName("@0"))
	NmsPacketPlayOutSpawnEntity setUUID(UUID id);
	
	default EntityType getEntityType()
	{
		if(BukkitWrapper.version<14)
			return getEntityTypeV_14();
		else
			return EntityTypeUtil.fromNmsV13(getEntityTypeV14());
	}
	
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=14))
	NmsEntityTypes getEntityTypeV14();
	@WrappedBukkitFieldAccessor(@VersionName(value="@0",minVer=14))
	NmsPacketPlayOutSpawnEntity setEntityTypeV14(NmsEntityTypes type);
	@WrappedBukkitFieldAccessor(@VersionName(value="@6",maxVer=14))
	int getMainEntityTypeV_14();
	@WrappedBukkitFieldAccessor(@VersionName(value="@7",maxVer=14))
	int getSecondEntityTypeV_14();
	default EntityType getEntityTypeV_14()
	{
		switch(getMainEntityTypeV_14())
		{
			case 1:
				return EntityType.BOAT;
			case 2:
				return EntityType.DROPPED_ITEM;
			case 3:
				return EntityType.AREA_EFFECT_CLOUD;
			case 10:
				switch(getSecondEntityTypeV_14())
				{
					case 0:
						return EntityType.MINECART;
					case 1:
						return EntityType.MINECART_CHEST;
					case 2:
						return EntityType.MINECART_FURNACE;
					case 3:
						return EntityType.MINECART_TNT;
					case 4:
						return EntityType.MINECART_MOB_SPAWNER;
					case 5:
						return EntityType.MINECART_HOPPER;
					case 6:
						return EntityType.MINECART_COMMAND;
					default:
						return null;
				}
			case 50:
				return EntityType.PRIMED_TNT;
			case 51:
				return EntityType.ENDER_CRYSTAL;
			case 60:
				return EntityType.ARROW;
			case 61:
				return EntityType.SNOWBALL;
			case 62:
				return EntityType.EGG;
			case 63:
				return EntityType.FIREBALL;
			case 64:
				return EntityType.SMALL_FIREBALL;
			case 65:
				return EntityType.ENDER_PEARL;
			case 66:
				return EntityType.WITHER_SKULL;
			case 67:
				return EntityType.SHULKER_BULLET;
			case 68:
				return EntityType.LLAMA_SPIT;
			case 70:
				return EntityType.FALLING_BLOCK;
			case 71:
				return EntityType.ITEM_FRAME;
			case 72:
				return EntityType.ENDER_SIGNAL;
			case 73:
				return EntityType.LINGERING_POTION;
			case 75:
				return EntityType.THROWN_EXP_BOTTLE;
			case 76:
				return EntityType.FIREWORK;
			case 77:
				return EntityType.LEASH_HITCH;
			case 78:
				return EntityType.ARMOR_STAND;
			case 79:
				return EntityType.EVOKER_FANGS;
			case 90:
				return EntityType.FISHING_HOOK;
			case 91:
				return EntityType.SPECTRAL_ARROW;
			case 93:
				return EntityType.DRAGON_FIREBALL;
			case 94:
				return EntityTypeUtil.tridentV13;
			default:
				return null;
		}
	}
}
