package mz.lib.minecraft.command.argparser;

import mz.lib.minecraft.bukkitlegacy.MzLib;
import mz.lib.module.MzModule;
import mz.lib.module.IRegistrar;
import mz.lib.module.RegistrarRegistrar;

public class ArgParserRegistrar extends MzModule implements IRegistrar<IArgParser>
{
	public static ArgParserRegistrar instance=new ArgParserRegistrar();
	
	@Override
	public Class<IArgParser> getType()
	{
		return IArgParser.class;
	}
	@Override
	public boolean register(MzModule module,IArgParser obj)
	{
		IArgParser.setDefault(obj);
		return true;
	}
	@Override
	public void unregister(MzModule module,IArgParser obj)
	{
		IArgParser.defaultArgParsers.remove(obj.getType(),obj);
	}
	
	@Override
	public void onLoad()
	{
		depend(RegistrarRegistrar.instance);
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
