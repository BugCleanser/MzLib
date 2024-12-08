package mz.mzlib.minecraft;

import mz.mzlib.util.async.BasicAwait;

public class SleepTicks implements BasicAwait
{
    public long ticks;

    public SleepTicks(long ticks)
    {
        this.ticks = ticks;
    }
}
