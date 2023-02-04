package mz.lib.minecraft.bukkit.command.argparser;

import com.google.common.collect.Lists;
import mz.lib.*;
import mz.lib.minecraft.bukkit.LangUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IArgParser<T>
{
	Map<Class<?>,IArgParser<?>> defaultArgParsers=new HashMap<Class<?>,IArgParser<?>>();
	static void setDefault(IArgParser<?> parser)
	{
		setDefault(parser.getType(),parser);
		if(parser instanceof AbsPrimitiveArgParser)
			setDefault(TypeUtil.toPrimitive(parser.getType()),parser);
	}
	static void setDefault(Class<?> type,IArgParser<?> parser)
	{
		defaultArgParsers.put(type,parser);
	}
	static <R> IArgParser<R> getDefault(Class<R> type)
	{
		if(type.isArray()&&!type.getComponentType().isPrimitive())
		{
			return TypeUtil.cast(new ObjectArrayArgParser<>(type.getComponentType()));
		}
		return TypeUtil.cast(defaultArgParsers.get(type));
	}
	
	Class<T> getType();
	
	default String getErrMsg(CommandSender player,String name,double max,double min)
	{
		if(name!=null)
			return StringUtil.replaceStrings(LangUtil.getTranslated(LangUtil.getLang(player),"mzlib.command.default.errMsgWithName"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{name\\}",LangUtil.getTranslated(player,name)),new MapEntry<>("%\\{type\\}",getTypeName(player,max,min)))));
		return StringUtil.replaceStrings(LangUtil.getTranslated(LangUtil.getLang(player),"mzlib.command.default.errMsg"),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{type\\}",getTypeName(player,max,min)))));
	}
	
	T parse(CommandSender sender, String arg) throws Throwable;
	
	default boolean check(CommandSender sender,String arg,double min,double max)
	{
		try
		{
			return parse(sender,arg)!=null;
		}
		catch(Throwable e)
		{
		}
		return false;
	}
	
	boolean checkFront(CommandSender sender,String arg,double min,double max);
	
	default double getMin()
	{
		return Double.NEGATIVE_INFINITY;
	}
	default double getMax()
	{
		return Double.POSITIVE_INFINITY;
	}
	
	default boolean canContainWhitespace()
	{
		return false;
	}
	
	String getTypeName(CommandSender player,double max,double min);
	
	default List<String> getDefaultPreset(CommandSender player,double max,double min)
	{
		return new ArrayList<>();
	}
}
