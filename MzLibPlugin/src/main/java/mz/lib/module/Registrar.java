package mz.lib.module;

import java.util.function.*;

public class Registrar<T> implements IRegistrar<T>
{
	public Class<T> type;
	public BiFunction<MzModule,T,Boolean> mReg;
	public BiConsumer<MzModule,T> mUnreg;
	public Registrar(Class<T> type,BiFunction<MzModule,T,Boolean> mReg,BiConsumer<MzModule,T> mUnreg)
	{
		this.type=type;
		this.mReg=mReg;
		this.mUnreg=mUnreg;
	}
	
	public Class<T> getType()
	{
		return type;
	}
	public boolean register(MzModule module,T obj)
	{
		return mReg.apply(module,obj);
	}
	public void unregister(MzModule module,T obj)
	{
		mUnreg.accept(module,obj);
	}
}
