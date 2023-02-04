package mz.lib;

import java.lang.reflect.Array;
import java.util.*;

public class ViewSlice<T> implements List<T>
{
	protected T[] array;
	protected int begin;
	protected int end;
	public ViewSlice(T[] array,int begin)
	{
		this(array,begin,array.length);
	}
	public ViewSlice(T[] array,int begin,int end)
	{
		if(begin<0 || end>array.length)
			throw new IllegalArgumentException();
		this.array=array;
		this.begin=begin;
		this.end=end;
	}
	public T[] duplicate()
	{
		return array.clone();
	}
	public int getBegin()
	{
		return begin;
	}
	public int getEnd()
	{
		return end;
	}
	@Override
	public int size()
	{
		return end-begin;
	}
	@Override
	public boolean isEmpty()
	{
		return size()==0;
	}
	@Override
	public boolean contains(Object o)
	{
		return indexOf(o)!=-1;
	}
	@NotNull
	@Override
	public Iterator<T> iterator()
	{
		return listIterator();
	}
	@NotNull
	@Override
	public Object[] toArray()
	{
		return toArray(new Object[size()]);
	}
	@NotNull
	@Override
	public <T1> T1[] toArray(@NotNull T1[] a)
	{
		int size=this.size();
		if(a.length<size)
			a=(T1[]) Array.newInstance(a.getClass().getComponentType(),size);
		for(int i=0;i<size;i++)
			a[i]=(T1) get(i);
		return a;
	}
	@Override
	public boolean add(T t)
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean containsAll(@NotNull Collection<?> c)
	{
		for(Object e:c)
		{
			if(!contains(e))
				return false;
		}
		return true;
	}
	@Override
	public boolean addAll(@NotNull Collection<? extends T> c)
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean addAll(int index,@NotNull Collection<? extends T> c)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeAll(@NotNull Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean retainAll(@NotNull Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public void clear()
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public T get(int index)
	{
		if(begin+index<end)
			return array[begin+index];
		throw new IndexOutOfBoundsException("index: "+index+" size: "+size());
	}
	@Override
	public T set(int index,T element)
	{
		T r=array[begin+index];
		array[begin+index]=element;
		return r;
	}
	@Override
	public void add(int index,T element)
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public T remove(int index)
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public int indexOf(Object o)
	{
		for(int i=begin;i<end;i++)
			if(Objects.equals(array[i],o))
				return i-begin;
		return -1;
	}
	@Override
	public int lastIndexOf(Object o)
	{
		for(int i=end-1;i>=begin;i--)
			if(Objects.equals(array[i],o))
				return i-begin;
		return -1;
	}
	@NotNull
	@Override
	public ListIterator<T> listIterator()
	{
		return listIterator(0);
	}
	@NotNull
	@Override
	public ListIterator<T> listIterator(int index)
	{
		return new ListIterator<T>()
		{
			private volatile int i=index;
			@Override
			public boolean hasNext()
			{
				return i<size();
			}
			
			@Override
			public T next()
			{
				return get(i++);
			}
			
			@Override
			public boolean hasPrevious()
			{
				return i>1;
			}
			
			@Override
			public T previous()
			{
				if(hasPrevious())
				  return get(--i-1);
				else
					throw new NoSuchElementException();
			}
			
			@Override
			public int nextIndex()
			{
				return i;
			}
			
			@Override
			public int previousIndex()
			{
				return i-2;
			}
			
			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void set(T t)
			{
				ViewSlice.this.set(i-1,t);
			}
			
			@Override
			public void add(T t)
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	@NotNull
	@Override
	public List<T> subList(int fromIndex,int toIndex)
	{
		return new ViewSlice<>(array,this.begin+fromIndex,this.begin+toIndex);
	}
}
