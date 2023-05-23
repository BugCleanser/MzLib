package mz.mzlib.javautil;

import javax.annotation.Nonnull;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CopyOnWriteSet<T> extends AbstractSet<T>
{
	public Function<Set<T>,Set<T>> copier;
	public volatile Set<T> delegate;
	
	public CopyOnWriteSet(Function<Set<T>,Set<T>> copier,Set<T> delegate)
	{
		this.copier=copier;
		this.delegate=delegate;
	}
	public CopyOnWriteSet()
	{
		this(HashSet::new,new HashSet<>());
	}
	
	@Override
	public int size()
	{
		return delegate.size();
	}
	@Override
	public boolean isEmpty()
	{
		return delegate.isEmpty();
	}
	@Override
	public boolean contains(Object o)
	{
		return delegate.contains(o);
	}
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			final Iterator<T> iterator=delegate.iterator();
			@Override
			public boolean hasNext()
			{
				return iterator.hasNext();
			}
			@Override
			public T next()
			{
				return iterator.next();
			}
		};
	}
	@Override
	public Object[] toArray()
	{
		return delegate.toArray();
	}
	@Override
	public <T1> T1[] toArray(T1[] a)
	{
		return delegate.toArray(a);
	}
	@Override
	public synchronized boolean add(T t)
	{
		Set<T> next=copier.apply(delegate);
		boolean result=next.add(t);
		delegate=next;
		return result;
	}
	@Override
	public synchronized boolean remove(Object o)
	{
		Set<T> next=copier.apply(delegate);
		boolean result=next.remove(o);
		delegate=next;
		return result;
	}
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return delegate.containsAll(c);
	}
	@Override
	public synchronized boolean addAll(Collection<? extends T> c)
	{
		Set<T> next=copier.apply(delegate);
		boolean result=next.addAll(c);
		delegate=next;
		return result;
	}
	@Override
	public synchronized boolean retainAll(Collection<?> c)
	{
		Set<T> next=copier.apply(delegate);
		boolean result=next.retainAll(c);
		delegate=next;
		return result;
	}
	@Override
	public boolean removeAll(Collection<?> c)
	{
		Set<T> next=copier.apply(delegate);
		boolean result=next.removeAll(c);
		delegate=next;
		return result;
	}
	@Override
	public synchronized void clear()
	{
		Set<T> next=copier.apply(delegate);
		next.clear();
		delegate=next;
	}
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof CopyOnWriteSet)
			return delegate.equals(((CopyOnWriteSet<?>)o).delegate);
		else
			return delegate.equals(o);
	}
	@Override
	public int hashCode()
	{
		return delegate.hashCode();
	}
	@Override
	public Spliterator<T> spliterator()
	{
		return delegate.spliterator();
	}
	@Override
	public synchronized boolean removeIf(Predicate<? super T> filter)
	{
		Set<T> next=copier.apply(delegate);
		boolean result=next.removeIf(filter);
		delegate=next;
		return result;
	}
	@Override
	public Stream<T> stream()
	{
		return delegate.stream();
	}
	@Override
	public Stream<T> parallelStream()
	{
		return delegate.parallelStream();
	}
	@Override
	public void forEach(Consumer<? super T> action)
	{
		delegate.forEach(action);
	}
}
