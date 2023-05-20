package mz.mzlib.javautil;

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
}
