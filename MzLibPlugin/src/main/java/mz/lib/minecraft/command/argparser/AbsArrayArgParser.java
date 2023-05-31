package mz.lib.minecraft.command.argparser;

import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;

public abstract class AbsArrayArgParser<T> extends AbsArgParser<Object>
{
	public AbsArrayArgParser(Class<T> type)
	{
		super(TypeUtil.cast(Array.newInstance(type,0).getClass()));
	}
	
	public IArgParser<T> getComponentParser()
	{
		return TypeUtil.cast(getDefault(this.getType().getComponentType()));
	}
	
	public T[] parse0(CommandSender sender,String arg) throws Throwable
	{
		IArgParser<T> componentParser=getComponentParser();
		String[] elementStrings=arg.split(" ");
		T[] elements=TypeUtil.cast(Array.newInstance(getType().getComponentType(),elementStrings.length));
		for(int i=0;i<elements.length;i++)
		{
			elements[i]=componentParser.parse(sender,elementStrings[i]);
		}
		return elements;
	}
	
	@Override
	public boolean checkFront(CommandSender sender,String arg,double min,double max)
	{
		return check(sender,arg,min,max);
	}
	
	@Override
	public String getTypeName(CommandSender player,double max,double min)
	{
		if(min<0)
			min=0;
		return StringUtil.replaceStrings(LangUtil.getTranslated(player,"mzlib.command.default.type.array"),new MapEntry<>("%\\{min\\}",Double.toString(min)),new MapEntry<>("%\\{max\\}",Double.toString(max)),new MapEntry<>("%\\{component\\}",getComponentParser().getTypeName(player,Double.MAX_VALUE,Double.MIN_VALUE)));
	}
	
	@Override
	public boolean canContainWhitespace()
	{
		return true;
	}
}
