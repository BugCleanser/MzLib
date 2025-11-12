package mz.mzlib.minecraft.data;

import mz.mzlib.util.Editor;

import java.util.ArrayList;
import java.util.List;

public class DataKey<H, T>
{
    String name;
    public DataKey(String name)
    {
        this.name = name;
    }
    List<DataHandler<H, T>> handlers = new ArrayList<>();
    DataHandler<H, T> handler;

    DataHandler<H, T> getHandler()
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

    public static class Revisable<H, T, R> extends DataKey<H, T>
    {
        public Revisable(String name)
        {
            super(name);
        }

        public Editor<R> revise(H holder)
        {
            return ((DataHandler.Revisable<H, T, R>) this.getHandler()).revise(holder);
        }
    }
}
