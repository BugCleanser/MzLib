package mz.mzlib.data;

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

        DataHandler.Revisable<H, T, R> getHandlerRevisable()
        {
            DataHandler<H, T> handler = this.getHandler();
            if(!(handler instanceof DataHandler.Revisable))
                throw new IllegalStateException("This key (" + this.name + ") has non-revisable handler: " + handler);
            return (DataHandler.Revisable<H, T, R>) handler;
        }

        public Editor<R> revise(H holder)
        {
            return this.getHandlerRevisable().revise(holder);
        }
    }
}
