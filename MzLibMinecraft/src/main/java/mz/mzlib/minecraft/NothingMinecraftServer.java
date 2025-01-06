package mz.mzlib.minecraft;

import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

import java.util.Objects;

@WrapSameClass(MinecraftServer.class)
public interface NothingMinecraftServer extends WrapperObject, MinecraftServer, Nothing
{
    @VersionRange(end=1400)
    @NothingInject(wrapperMethod = "tickV_1400", locateMethod = "", type = NothingInjectType.INSERT_BEFORE)
    default Wrapper_void tickBeforeV_1400()
    {
        while(!waitingTasks.isEmpty() && Objects.requireNonNull(waitingTasks.peek()).first-tickNumber.get()<=0)
        {
            try
            {
                Objects.requireNonNull(waitingTasks.poll()).second.run();
            }
            catch (Throwable e)
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
            catch (Throwable e)
            {
                e.printStackTrace(System.err);
            }
        }
        tickNumber.target++;
        return Nothing.notReturn();
    }
    @VersionRange(begin=1400)
    @NothingInject(wrapperMethod = "tickV1400", locateMethod = "", type = NothingInjectType.INSERT_BEFORE)
    default Wrapper_void tickBeforeV1400()
    {
        return tickBeforeV_1400();
    }
}
