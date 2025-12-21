package mz.mzlib.minecraft;

import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;

import java.util.Objects;
import java.util.function.BooleanSupplier;

@WrapSameClass(MinecraftServer.class)
public interface NothingMinecraftServer extends MinecraftServer, Nothing
{
    @VersionRange(end = 1300)
    @NothingInject(wrapperMethodName = "tickV_1300", wrapperMethodParams = {}, locateMethod = "locateAllReturn", type = NothingInjectType.INSERT_BEFORE)
    default void tickEndV_1300()
    {
        while(!waitingTasks.isEmpty() && Objects.requireNonNull(waitingTasks.peek()).getFirst() - tickNumber.get() <= 0)
        {
            try
            {
                Objects.requireNonNull(waitingTasks.poll()).getSecond().run();
            }
            catch(Throwable e)
            {
                e.printStackTrace(System.err);
            }
        }
        while(!tasks.isEmpty())
        {
            try
            {
                Objects.requireNonNull(tasks.poll()).run();
            }
            catch(Throwable e)
            {
                e.printStackTrace(System.err);
            }
        }
        tickNumber.target++;
    }

    @VersionRange(begin = 1300)
    @NothingInject(wrapperMethodName = "tickV1300", wrapperMethodParams = { BooleanSupplier.class }, locateMethod = "locateAllReturn", type = NothingInjectType.INSERT_BEFORE)
    default void tickEndV1300()
    {
        tickEndV_1300();
    }
}
