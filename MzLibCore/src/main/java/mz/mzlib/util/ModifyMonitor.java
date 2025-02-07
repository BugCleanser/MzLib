package mz.mzlib.util;

public interface ModifyMonitor
{
    default void onModify()
    {
    }
    default void markDirty()
    {
    }
    
    class Empty implements ModifyMonitor
    {
        public static Empty instance = new Empty();
    }
    class Simple implements ModifyMonitor
    {
        Runnable onModify, markDirty;
        public Simple(Runnable onModify, Runnable markDirty)
        {
            this.onModify = onModify;
            this.markDirty = markDirty;
        }
        @Override
        public void onModify()
        {
            this.onModify.run();
        }
        @Override
        public void markDirty()
        {
            this.markDirty.run();
        }
    }
}
