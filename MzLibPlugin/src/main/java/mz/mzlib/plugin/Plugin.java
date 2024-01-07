package mz.mzlib.plugin;

public interface Plugin
{
	String getName();
	
	default void onLoad()
	{
	}
	default void onUnload()
	{
	}
	default void onEnable()
	{
	}
	default void onDisable()
	{
	}
}
