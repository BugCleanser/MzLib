package mz.lib.minecraft.command;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import mz.lib.minecraft.command.argparser.ArgInfo;
import mz.lib.minecraft.command.argparser.IArgParser;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

import com.google.common.collect.Lists;

import io.github.karlatemp.unsafeaccessor.Root;
import mz.lib.ListUtil;
import mz.lib.MapEntry;
import mz.lib.StringUtil;
import mz.lib.TypeUtil;
import mz.lib.minecraft.bukkitlegacy.LangUtil;

public class FinalCommandExecutor
{
	public Class<? extends CommandSender> senderType;
	public String senderTypeError;
	public List<IArgParser<?>> argParsers;
	public MethodHandle method;
	public CommandHandler handler;
	public ArgInfo[] argInfos;
	public ILastCommandProcessor lsp;
	
	public FinalCommandExecutor(ILastCommandProcessor lsp,CommandHandler handler,MethodHandle method,Parameter[] args)
	{	
		this.lsp=lsp;
		this.senderType=TypeUtil.cast(args[0].getType());
		if(handler==null)
		{
			handler=new CommandHandler()
			{
				public Class<? extends Annotation> annotationType()
				{
					return CommandHandler.class;
				}
				public String effect()
				{
					return "";
				}
				public boolean mustOp()
				{
					return false;
				}
				public String permission()
				{
					return "";
				}
			};
		}
		this.handler=handler;
		this.method=method;
		this.argInfos=new ArgInfo[args.length-1];
		argParsers=new ArrayList<>(args.length-1);
		for(int i=1;i<args.length;i++)
		{	
			this.argInfos[i-1]=args[i].getDeclaredAnnotation(ArgInfo.class);
			if(this.argInfos[i-1]==null)
				this.argInfos[i-1]=new ArgInfo()
				{
					public Class<? extends Annotation> annotationType()
					{	
						return ArgInfo.class;
					}
					public String[] presets()
					{	
						return new String[0];
					}
					public String name()
					{	
						return "";
					}
					public boolean mustPreset()
					{	
						return false;
					}
					public String errMsg()
					{	
						return "";
					}
					public double min()
					{	
						return Double.NEGATIVE_INFINITY;
					}
					public Class<? extends IArgParser> parser()
					{
						return IArgParser.class;
					}
					public double max()
					{	
						return Double.POSITIVE_INFINITY;
					}
				};
			Type tt=args[i].getParameterizedType();
			if(tt instanceof ParameterizedType)
			{	
				ParameterizedType pt=(ParameterizedType) tt;
				if(pt.getRawType()==List.class)
				{	
					Class<?> tp = TypeUtil.cast(pt.getActualTypeArguments()[0]);
					if(List.class.isAssignableFrom(tp))
						throw new IllegalArgumentException("Arg of executor cannot be List<List<?>>");
					IArgParser<?> t = IArgParser.getDefault(tp);
					this.argParsers.add(new IArgParser<List<?>>()
					{	
						@Override
						public Class<List<?>> getType()
						{	
							return TypeUtil.cast(List.class);
						}
						@Override
						public String getErrMsg(CommandSender player,String name,double max, double min)
						{	
							return t.getErrMsg(player,name,max, min);
						}
						@Override
						public List<?> parse(CommandSender sender, String arg) throws Throwable
						{	
							String[] v = arg.split(" ");
							List<?> r=new ArrayList<>();
							for(String i:v)
							{	
								r.add(TypeUtil.cast(t.parse(sender, i)));
							}
							return r;
						}
						@Override
						public boolean checkFront(CommandSender sender, String arg,double min,double max)
						{	
							String[] v = arg.split(" ");
							for(int i=0;i<v.length;i++)
							{	
								if(!t.checkFront(sender, v[i],min,max))
									return false;
							}
							return true;
						}
						@Override
						public String getTypeName(CommandSender player,double max, double min)
						{	
							return t.getTypeName(player,max, min);
						}
						@Override
						public boolean canContainWhitespace()
						{	
							return true;
						}
					});
				}
			}
			this.argParsers.add(IArgParser.getDefault(args[i].getType()));
		}
	}
	private static MethodHandle unreflect(Method m)
	{
		try
		{
			return Root.getTrusted().unreflect(m);
		}
		catch(Throwable e)
		{
			throw TypeUtil.throwException(e);
		}
	}
	public FinalCommandExecutor(ILastCommandProcessor lsp,CommandHandler handler,Method method)
	{	
		this(lsp,handler,unreflect(method),method.getParameters());
	}
	
	public static List<FinalCommandExecutor> getCommandExecutors(ILastCommandProcessor lsp)
	{	
		List<FinalCommandExecutor> executors=new LinkedList<>();
		for(Method m:lsp.getClass().getDeclaredMethods())
		{	
			CommandHandler ann=m.getDeclaredAnnotation(CommandHandler.class);
			if(ann!=null)
			{	
				executors.add(new FinalCommandExecutor(lsp,ann,m));
			}
		}
		return executors;
	}

	public boolean checkArgs(CommandSender sender, String[] args)
	{	
		if(this.argInfos.length==0)
			return false;
		if(args.length>this.argInfos.length&&this.argParsers.get(this.argInfos.length-1).canContainWhitespace())
		{	
			args=StringUtil.mergeStrings(this.argInfos.length-1,this.argInfos.length,args);
		}
		int m = Math.min(args.length,this.argInfos.length);
		for(int i=0;i<m;i++)
		{	
			if(!this.argParsers.get(i).check(sender, args[i],Math.max(argInfos[i].min(),this.argParsers.get(i).getMin()),Math.min(argInfos[i].max(),this.argParsers.get(i).getMax())))
				return false;
		}
		return true;
	}
	public boolean canTabComplement(CommandSender sender, String[] args)
	{	
		IArgParser<?> argType;
		String arg;
		int index;
		if(args.length<=this.argInfos.length)
		{	
			index=args.length-1;
			argType=this.argParsers.get(index);
			arg=args[index];
		}
		else
		{	
			index=this.argInfos.length-1;
			if(index<0)
			{	
				return false;
			}
			argType=this.argParsers.get(index);
			arg=StringUtil.mergeStrings(index,this.argInfos.length,args)[index];
		}
		for(int i=0;i<index;i++)
		{	
			if(!this.argParsers.get(i).check(sender,args[i],Math.max(this.argParsers.get(i).getMin(),argInfos[i].min()),Math.min(this.argParsers.get(i).getMax(),argInfos[i].max())))
				return false;
		}
		return arg.length()==0||argType.checkFront(sender,arg,Math.max(argType.getMin(),argInfos[index].min()),Math.min(argType.getMax(),argInfos[index].max()));
	}
	
	public Collection<? extends String> onTabComplement(CommandSender sender,String[] args)
	{	
		IArgParser<?> argType;
		String[] presets;
		boolean mustPreset;
		String arg;
		int index;
		int wsn;
		if(args.length<=this.argInfos.length)
		{	
			index=args.length-1;
			argType=this.argParsers.get(index);
			presets=this.argInfos[index].presets();
			mustPreset=this.argInfos[index].mustPreset();
			arg=args[index];
			wsn=0;
		}
		else
		{	
			index=this.argInfos.length-1;
			if(index<0||!this.argParsers.get(index).canContainWhitespace())
			{	
				return new ArrayList<>();
			}
			wsn=args.length-this.argInfos.length;
			argType=this.argParsers.get(index);
			presets=this.argInfos[index].presets();
			mustPreset=this.argInfos[index].mustPreset();
			arg=StringUtil.mergeStrings(index,this.argInfos.length,args)[index];
		}
		if(((!mustPreset)||ListUtil.startWithIgnoreCase(Lists.newArrayList(presets),arg).size()>0)&&(arg.length()==0||argType.checkFront(sender,arg,Math.max(argType.getMin(),argInfos[index].min()),Math.min(argType.getMax(),argInfos[index].max()))))
		{	
			return ListUtil.startWithIgnoreCase(mustPreset?Lists.newArrayList(presets):ListUtil.mergeLists(Lists.newArrayList(presets),argType.getDefaultPreset(sender,this.argInfos[index].max(),this.argInfos[index].min())),arg).stream().map(s->
			{	
				if(s.contains(" "))
				{
					String[] ss = s.split(" ",wsn+1);
					return ss[ss.length-1];
				}
				else
					return s;
			}).collect(Collectors.toList());
		}
		else
			return Lists.newArrayList(this.argInfos[index].errMsg().equals("")?argType.getErrMsg(sender,this.argInfos[index].name().equals("")?null:this.argInfos[index].errMsg(),Math.min(this.argInfos[index].max(),this.argParsers.get(index).getMax()),Math.max(this.argInfos[index].min(),this.argParsers.get(index).getMin())):this.argInfos[index].errMsg());
	}
	
	boolean hasPermission(CommandSender sender)
	{
		if(handler.mustOp()&&!sender.isOp())
			return false;
		return handler.permission()==null||handler.permission().length()==0||sender.hasPermission(handler.permission());
	}
	public boolean canExecute(CommandSender sender,String[] args)
	{
		if(!this.hasPermission(sender))
			return false;
		if(!this.argParsers.isEmpty())
			args=StringUtil.mergeStrings(this.argParsers.size()-1,this.argParsers.size(),args);
		if(args.length<this.argParsers.size()|| this.argInfos.length==0&&args.length>0)
			return false;
		for(int i=0;i<args.length;i++)
		{	
			if(this.argInfos[i].mustPreset())
			{	
				if(!Lists.newArrayList(this.argInfos[i].presets()).contains(args[i]))
					return false;
			}
			if(!this.argParsers.get(i).check(sender,args[i],Math.max(this.argParsers.get(i).getMin(),argInfos[i].min()),Math.min(this.argParsers.get(i).getMax(),argInfos[i].max())))
				return false;
		}
		return true;
	}
	
	public static Map<Class<? extends CommandSender>,String> defaultSenderTypeErrors=new HashMap<>();
	static
	{
		defaultSenderTypeErrors.put(Player.class,"mzlib.command.default.sender.player.error");
		defaultSenderTypeErrors.put(ConsoleCommandSender.class,"mzlib.command.default.sender.console.error");
		defaultSenderTypeErrors.put(BlockCommandSender.class,"mzlib.command.default.sender.commandBlock.error");
		defaultSenderTypeErrors.put(CommandMinecart.class,"mzlib.command.default.sender.commandBlockMinecart.error");
		defaultSenderTypeErrors.put(Entity.class,"mzlib.command.default.sender.entity.error");
	}
	public static String getDefaultSenderTypeErrors(Class<? extends CommandSender> type)
	{
		String r=defaultSenderTypeErrors.get(type);
		if(r!=null)
			return r;
		return "mzlib.command.default.sender.default.error";
	}
	public void execute(CommandSender sender,String[] args)
	{	
		if(!this.senderType.isAssignableFrom(sender.getClass()))
		{
			sender.sendMessage(StringUtil.replaceStrings(LangUtil.getTranslated(sender,senderTypeError),ListUtil.toMap(Lists.newArrayList(new MapEntry<>("%\\{type\\}",this.senderType.getSimpleName())))));
			return;
		}
		args=StringUtil.mergeStrings(this.argParsers.size()-1,this.argParsers.size(),args);
		List<Object> as=new ArrayList<>(args.length+1);
		try
		{	
			as.add(sender);
			for(int i=0;i<args.length;i++)
			{	
				as.add(this.argParsers.get(i).parse(sender, args[i]));
			}
			if(this.lsp!=null)
				as.add(0,lsp);
			this.method.invokeWithArguments(as.toArray());
		}
		catch(Throwable e)
		{	
			throw TypeUtil.throwException(e);
		}
	}
	
	public String getUsage(CommandSender player)
	{	
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<this.argInfos.length;i++)
		{	
			if(sb.length()>0)
				sb.append(' ');
			sb.append('<');
			if(this.argInfos[i].name().equals(""))
				sb.append(this.argParsers.get(i).getTypeName(player,Math.min(this.argInfos[i].max(),this.argParsers.get(i).getMax()),Math.max(this.argInfos[i].min(),this.argParsers.get(i).getMin())));
			else
				sb.append(LangUtil.getTranslated(player,this.argInfos[i].name()));
			sb.append('>');
		}
		return sb.toString();
	}
}
