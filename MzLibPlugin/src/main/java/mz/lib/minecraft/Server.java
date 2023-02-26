package mz.lib.minecraft;

import com.google.common.collect.*;
import mz.lib.minecraft.wrapper.*;
import mz.mzlib.wrapper.*;

import java.util.*;

public abstract class Server
{
	public static Server instance;
	
	public boolean v13;
	public boolean v17;
	public String MCVersion;
	public float version;
	
	public abstract String convertClassName(String clName);
	
	public String[] convertClassName(String[] clName)
	{
		List<String> r=new ArrayList<>();
		for(String c:clName)
			r.add(convertClassName(c));
		return r.toArray(new String[0]);
	}
	
	public boolean inVersion(VersionalName v)
	{
		return version>=v.minVer()&&version<v.maxVer();
	}
	public boolean inVersion(VersionalWrappedConstructor v)
	{
		return version>=v.minVer()&&version<v.maxVer();
	}
	public String[] inVersion(VersionalName[] name)
	{
		List<String> r=new ArrayList<>();
		for(VersionalName v:name)
		{
			if(inVersion(v))
				r.addAll(Lists.newArrayList(v.value()));
		}
		return r.toArray(new String[0]);
	}
}
