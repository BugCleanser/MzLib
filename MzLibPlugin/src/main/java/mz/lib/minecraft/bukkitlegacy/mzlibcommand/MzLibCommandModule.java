package mz.lib.minecraft.bukkitlegacy.mzlibcommand;

import mz.lib.minecraft.bukkitlegacy.*;
import mz.lib.minecraft.bukkitlegacy.command.*;
import mz.lib.minecraft.bukkitlegacy.mzlibcommand.debug.*;
import mz.lib.minecraft.bukkitlegacy.permission.*;

public class MzLibCommandModule extends MainCommand
{
	public static MzLibCommandModule instance=new MzLibCommandModule();
	public MzLibCommandModule()
	{
		super(MzLib.instance,new MzLibCommand());
		this.depends.add(PermissionRegistrar.instance);
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
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
