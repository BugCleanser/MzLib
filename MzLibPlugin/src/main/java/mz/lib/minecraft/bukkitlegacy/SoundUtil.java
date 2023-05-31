package mz.lib.minecraft.bukkitlegacy;

import mz.lib.minecraft.*;
import mz.mzlib.*;
import org.bukkit.Sound;

public class SoundUtil
{
	public static Sound BLOCK_NOTE_BLOCK_HAT=forFlattening("BLOCK_NOTE_HAT","BLOCK_NOTE_BLOCK_HAT");
	
	public static Sound forFlattening(String id,String idV13)
	{
		if(Server.instance.v13)
			return Enum.valueOf(Sound.class,idV13);
		else
			return Enum.valueOf(Sound.class,id);
	}
}
