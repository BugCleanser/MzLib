package mz.lib.minecraft.command.argparser;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.minecraft.bukkitlegacy.module.AbsModule;
import mz.lib.minecraft.bukkitlegacy.module.IRegistrar;
import mz.lib.minecraft.bukkitlegacy.module.RegistrarRegistrar;

public class ArgParserRegistrar extends AbsModule implements IRegistrar<IArgParser>
{
	public static ArgParserRegistrar instance=new ArgParserRegistrar();
	public ArgParserRegistrar()
	{
		super(MzLib.instance,RegistrarRegistrar.instance);
	}
	
	@Override
	public Class<IArgParser> getType()
	{
		return IArgParser.class;
	}
	@Override
	public boolean register(IArgParser obj)
	{
		IArgParser.setDefault(obj);
		return true;
	}
	@Override
	public void unregister(IArgParser obj)
	{
		IArgParser.defaultArgParsers.remove(obj.getType(),obj);
	}
	
	@Override
	public void onEnable()
	{
		reg(new BoolArgParser());
		reg(new ByteArgParser());
		reg(new CharArgParser());
		reg(new DoubleArgParser());
		reg(new FloatArgParser());
		reg(new IntArgParser());
		reg(new LongArgParser());
		reg(new ShortArgParser());
		reg(new StringArgParser());
		
		reg(new PrimitiveArrayArgParser(boolean[].class));
		reg(new PrimitiveArrayArgParser(byte[].class));
		reg(new PrimitiveArrayArgParser(char[].class));
		reg(new PrimitiveArrayArgParser(double[].class));
		reg(new PrimitiveArrayArgParser(float[].class));
		reg(new PrimitiveArrayArgParser(int[].class));
		reg(new PrimitiveArrayArgParser(long[].class));
		reg(new PrimitiveArrayArgParser(short[].class));
		
		reg(new OfflinePlayerArgParser());
		reg(new PlayerArgParser());
		reg(new ItemStackBuilderArgParser());
		reg(new EnchantmentArgParser());
	}
}
