package mz.mzlib.javautil;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class Pair<First,Second> implements Comparable<Pair<First,Second>>
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
	public Map.Entry<First,Second> toMapEntry()
	{
		return new AbstractMap.SimpleEntry<>(first,second);
	}
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Pair&& Objects.equals(first,((Pair<?,?>)o).first)&&Objects.equals(second,((Pair<?,?>)o).second);
	}
	@Override
	public int hashCode()
	{
		return Objects.hashCode(first)^Objects.hashCode(second);
	}
}
