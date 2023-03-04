package mz.lib.minecraft.command.argparser;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import org.bukkit.command.CommandSender;

public abstract class AbsPrimitiveArgParser<T> extends AbsArgParser<T>
{
	public AbsPrimitiveArgParser(Class<T> type)
	{
		super(type);
	}
	
	@Override
	public String getTypeName(CommandSender player,double max,double min)
	{
		return StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.command.default.type."+TypeUtil.toPrimitive(getType()).getName()),new MapEntry<>("%\\{min\\}",Double.toString(min)),new MapEntry<>("%\\{max\\}",Double.toString(max)));
	}
}
