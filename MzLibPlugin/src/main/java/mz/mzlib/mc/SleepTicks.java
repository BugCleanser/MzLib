package mz.mzlib.mc;

import mz.mzlib.util.async.BasicAwait;

public class SleepTicks implements BasicAwait
{
    public int ticks;

    public SleepTicks(int ticks)
    {
        this.ticks = ticks;
    }
}
