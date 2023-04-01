package mz.lib.minecraft.bukkit.nms;

import mz.lib.minecraft.*;
import mz.lib.minecraft.bukkitlegacy.entity.*;
import mz.lib.minecraft.wrapper.*;
import mz.lib.*;
import mz.lib.wrapper.*;
import org.bukkit.entity.*;

import java.util.UUID;

@VersionalWrappedClass({@VersionalName(value="nms.PacketPlayOutSpawnEntity",maxVer=17),@VersionalName(value="net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity",minVer=17)})
public interface NmsPacketPlayOutSpawnEntity extends NmsPacket
{
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	int getEntityId();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntity setEntityId(int id);
	
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	UUID getUUID();
	@VersionalWrappedFieldAccessor(@VersionalName("@0"))
	NmsPacketPlayOutSpawnEntity setUUID(UUID id);
	
	default EntityType getEntityType()
	{
		if(Server.instance.version<14)
			return getEntityTypeV_14();
		else
			return EntityTypeUtil.fromNmsV13(getEntityTypeV14());
	}
	
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=14))
	NmsEntityTypes getEntityTypeV14();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@0",minVer=14))
	NmsPacketPlayOutSpawnEntity setEntityTypeV14(NmsEntityTypes type);
	@VersionalWrappedFieldAccessor(@VersionalName(value="@6",maxVer=14))
	int getMainEntityTypeV_14();
	@VersionalWrappedFieldAccessor(@VersionalName(value="@7",maxVer=14))
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
