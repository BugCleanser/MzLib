package mz.mzlib.util;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class IndexAllocator<T extends @Nullable Object>
{
    List<T> list;
    Queue<Integer> bin;

    public IndexAllocator()
    {
        this.list = new ArrayList<>();
        this.bin = new ArrayDeque<>();
    }

    public int alloc()
    {
        return this.alloc(null);
    }
    public synchronized int alloc(T value)
    {
        if(!this.bin.isEmpty())
        {
            int result = this.bin.poll();
            this.list.set(result, value);
            return result;
        }
        this.list.add(value);
        return this.list.size() - 1;
    }

    public synchronized void free(int index)
    {
        this.bin.add(index);
    }

    public T get(int index)
    {
        return this.list.get(index);
    }

    public void set(int index, T value)
    {
        this.list.set(index, value);
    }

    public int size()
    {
        return this.list.size() - this.bin.size();
    }
}
