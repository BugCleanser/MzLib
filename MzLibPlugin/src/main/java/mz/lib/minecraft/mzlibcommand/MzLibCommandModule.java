package mz.lib.minecraft.mzlibcommand;

import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.command.*;
import mz.lib.minecraft.mzlibcommand.debug.*;
import mz.lib.minecraft.permission.*;
import mz.lib.minecraft.command.*;
import mz.lib.minecraft.mzlibcommand.debug.*;

public class MzLibCommandModule extends MainCommand
{
	public static MzLibCommandModule instance=new MzLibCommandModule();
	public MzLibCommandModule()
	{
		super(MzLib.instance,new MzLibCommand());
		this.depends.add(PermissionRegistrar.instance);
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		reg(ReloadSubcommand.instance.permission);
		reg(DebugSubcommand.instance.permission);
		reg(GiveSubcommand.instance.permission);
		reg(RecipesSubcommand.instance.permission);
		reg(RespawnSubcommand.instance.permission);
		reg(ResetRecipesSubcommand.instance.permission);
		reg(EnchantSubcommand.instance.permission);
		reg(ItemInfoSubcommand.instance.permission);
		reg(PlayerInfoSubcommand.instance.permission);
		reg(PlayerInfoSubcommand.instance.permissionOther);
		reg(LoadMapSubcommand.instance.permission);
		reg(SkullSubcommand.instance.permission);
	}
}
