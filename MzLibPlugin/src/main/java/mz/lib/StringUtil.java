package mz.lib;

import com.google.common.collect.Lists;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public final class StringUtil
{
	public static Charset UTF8=StandardCharsets.UTF_8;
	
	public @Deprecated
	StringUtil()
	{
	}
	
	public static String[] split(String string,String regex)
	{
		if(string.length()==0)
			return new String[0];
		return string.split(regex);
	}
	
	public static boolean startsWithIgnoreCase(String a,String b)
	{
		if(a==null||b==null)
			return a==null&&b==null;
		return a.toLowerCase().startsWith(b.toLowerCase());
	}
	
	public static boolean endsWithIgnoreCase(String a,String b)
	{
		if(a==null||b==null)
			return a==null&&b==null;
		return a.toLowerCase().endsWith(b.toLowerCase());
	}
	
	/**
	 * 合并一些字符串
	 *
	 * @param mergeIndex 可以合并的字符串的索引
	 * @param stringsNum 合并后的字符串数量
	 * @param strings    一些字符串
	 * @return 合并后的字符串，合并的用空格分开
	 */
	public static String[] mergeStrings(int mergeIndex,int stringsNum,String... strings)
	{
		if(strings.length<stringsNum)
		{
			return strings;
		}
		else if(strings.length==stringsNum)
		{
			return strings;
		}
		List<String> rl=new ArrayList<>();
		for(int i=0;i<mergeIndex;i++)
		{
			rl.add(strings[i]);
		}
		StringBuilder merge=new StringBuilder(strings[mergeIndex]);
		//2 3 +
		for(int i=mergeIndex+1;i<=mergeIndex+strings.length-stringsNum;i++)
		{
			merge.append(" "+strings[i]);
		}
		rl.add(merge.toString());
		for(int i=mergeIndex+strings.length-stringsNum+1;i<strings.length;i++)
		{
			rl.add(strings[i]);
		}
		return rl.toArray(new String[rl.size()]);
	}
	public static String mergeStrings(String ...strings)
	{
		StringBuilder sb=new StringBuilder();
		for(String s: strings)
		{
			sb.append(s);
		}
		return sb.toString();
	}
	public static String mergeStrings(String separator,String[] strings)
	{
		if(strings.length==0)
			return "";
		StringBuilder sb=new StringBuilder(strings[0]);
		for(int i=1;i<strings.length;i++)
		{
			sb.append(separator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}
	
	@SafeVarargs
	public static String replaceStrings(String raw,Map.Entry<String,String>... beforeAndAfter)
	{
		return replaceStrings(raw,ListUtil.toMap(Lists.newArrayList(beforeAndAfter)));
	}
	public static String replaceStrings(String raw,Map<String,String> beforeAndAfter)
	{
		String[] ss={raw};
		for(Entry<String,String> t: beforeAndAfter.entrySet())
		{
			List<String> ts=new LinkedList<>();
			for(String s: ss)
			{
				boolean c=true;
				for(String k: beforeAndAfter.keySet())
				{
					if(t.getValue()==k)
						break;
					if(s==k)
					{
						c=false;
						break;
					}
				}
				if(c)
				{
					String[] tts=(" "+s+" ").split(t.getKey());
					tts[0]=tts[0].substring(1);
					tts[tts.length-1]=tts[tts.length-1].substring(0,tts[tts.length-1].length()-1);
					for(String d: tts)
					{
						ts.add(d);
						ts.add(t.getValue());
					}
					if(ts.size()>0)
						ts.remove(ts.size()-1);
				}
				else
					ts.add(s);
			}
			ss=ts.toArray(ss);
		}
		return mergeStrings(ss);
	}
	
	public static String codeString(String str)
	{
		StringBuilder sb=new StringBuilder();
		for(char c: str.toCharArray())
		{
			switch(c)
			{
				case '\r':
					sb.append("\\r");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\0':
					sb.append("\\0");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\'':
					sb.append("\\'");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return '\"'+sb.toString()+'\"';
	}
	public static boolean containsIgnoreCase(List<String> list,String str)
	{
		for(String s: list)
		{
			if(s.equalsIgnoreCase(str))
				return true;
		}
		return false;
	}
	public static boolean containsAny(String str,List<String> ss)
	{
		for(String s: ss)
		{
			if(str.contains(s))
				return true;
		}
		return false;
	}
	public static boolean containsAnyIgnoreCase(String str,List<String> ss)
	{
		str=str.toLowerCase();
		for(String s: ss)
		{
			if(str.contains(s.toLowerCase()))
				return true;
		}
		return false;
	}
	public static boolean startsWithAny(String str,Collection<String> ss)
	{
		for(String s: ss)
		{
			if(str.startsWith(s))
				return true;
		}
		return false;
	}
	public static boolean startsWithAnyIgnoreCase(String str,Collection<String> ss)
	{
		for(String s: ss)
		{
			if(StringUtil.startsWithIgnoreCase(str,s))
				return true;
		}
		return false;
	}
	public static int sum(String str,String s)
	{
		int num=0;
		for(int i=0;i<str.length();i++)
		{
			if(str.substring(i).startsWith(s))
				num++;
		}
		return num;
	}
	public static int sumIgnoreCase(String str,String s)
	{
		int num=0;
		for(int i=0;i<str.length();i++)
		{
			if(startsWithIgnoreCase(str.substring(i),s))
				num++;
		}
		return num;
	}
	public static int sum(String str,List<String> ss)
	{
		int num=0;
		for(String s: ss)
		{
			num+=sum(str,s);
		}
		return num;
	}
	public static int sumIgnoreCase(String str,List<String> ss)
	{
		int num=0;
		for(String s: ss)
		{
			num+=sumIgnoreCase(str,s);
		}
		return num;
	}
}
