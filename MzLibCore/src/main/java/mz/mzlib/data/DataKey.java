package mz.mzlib.data;

import mz.mzlib.util.Editor;

import java.util.ArrayList;
import java.util.List;

public class DataKey<H, T, R>
{
    String name;
    public DataKey(String name)
    {
        this.name = name;
    }
    List<DataHandler<H, T, R>> handlers = new ArrayList<>();
    DataHandler<H, T, R> handler;

    DataHandler<H, T, R> getHandler()
    {
        if(this.handler == null)
            throw new IllegalStateException("This key has no handler registered: " + this.name);
        return this.handler;
    }
    public boolean check(H holder)
    {
        return this.getHandler().check(holder);
    }
    public T get(H holder)
    {
        return this.getHandler().get(holder);
    }
    public void set(H holder, T value)
    {
        this.getHandler().set(holder, value);
    }
    public Editor<R> revise(H holder)
    {
        return this.getHandler().revise(holder);
    }
}
