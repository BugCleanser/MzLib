package mz.mzlib.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface AutoCompletable<T> extends Iterable<T>
{
    T start();
    void complete();
    
    @Override
    default Iterator<T> iterator()
    {
        return new Itr<>(this);
    }
    
    class Itr<T> implements Iterator<T>
    {
        AutoCompletable<T> editor;
        public Itr(AutoCompletable<T> editor)
        {
            this.editor = editor;
        }
        boolean started = false;
        @Override
        public boolean hasNext()
        {
            if(this.started)
            {
                this.editor.complete();
                return false;
            }
            else
                return true;
        }
        @Override
        public T next()
        {
            if(this.started)
                throw new NoSuchElementException();
            this.started = true;
            return this.editor.start();
        }
    }
}
