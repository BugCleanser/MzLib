package mz.mzlib.javautil;

import java.util.Map;
import java.util.Objects;

public class Pair<First,Second> implements Map.Entry<First,Second>,Comparable<Pair<First,Second>>
{
	public First first;
	public Second second;
	public Pair(First first,Second second)
	{
		this.first=first;
		this.second=second;
	}
	
	@Override
	public int compareTo(Pair<First,Second> o)
	{
		int resultFirst=RuntimeUtil.<Comparable<First>>forceCast(first).compareTo(o.first);
		if(resultFirst!=0)
			return resultFirst;
		return RuntimeUtil.<Comparable<Second>>forceCast(second).compareTo(o.second);
	}
	@Override
	public First getKey()
	{
		return first;
	}
	@Override
	public Second getValue()
	{
		return second;
	}
	@Override
	public Second setValue(Second value)
	{
		Second last=second;
		second=value;
		return last;
	}
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Map.Entry&&Objects.equals(first,((Map.Entry<?,?>)o).getKey())&&Objects.equals(second,((Map.Entry<?,?>)o).getValue());
	}
	@Override
	public int hashCode()
	{
		return Objects.hashCode(first)^Objects.hashCode(second);
	}
}
