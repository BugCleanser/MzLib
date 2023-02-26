package mz.lib.minecraft;

public interface Identifier
{
	static Identifier newInstance(String string)
	{
		return Factory.instance.newIdentifier(string);
	}
	static Identifier newInstance(String namespace,String key)
	{
		return Factory.instance.newIdentifier(namespace,key);
	}
	
	String getNamespace();
	String getKey();
}
