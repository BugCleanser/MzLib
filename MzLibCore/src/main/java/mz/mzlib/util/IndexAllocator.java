package mz.mzlib.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class IndexAllocator<T>
{
    public List<T> list;
    public Queue<Integer> bin;

    public IndexAllocator()
    {
        this.list = new ArrayList<>();
        this.bin = new ArrayDeque<>();
    }

    public synchronized int alloc()
    {
        if (!bin.isEmpty())
            return bin.poll();
        list.add(null);
        return list.size() - 1;
    }

    public synchronized void free(int index)
    {
        bin.add(index);
    }

    public T get(int index)
    {
        return list.get(index);
    }

    public void set(int index, T value)
    {
        list.set(index, value);
    }
}
