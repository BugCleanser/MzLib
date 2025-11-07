package mz.mzlib.minecraft.event.player;

import mz.mzlib.event.Cancellable;
import mz.mzlib.minecraft.PlayerManager;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ClientConnectionDataV2002;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

public class EventPlayerJoin extends EventPlayer implements Cancellable
{
    public ClientConnection connection;
    public EventPlayerJoin(EntityPlayer player, ClientConnection connection)
    {
        super(player);
        this.connection = connection;
    }

    @Override
    public void call()
    {
        super.call();
    }

    public static class Module extends MzModule
    {
        @WrapSameClass(PlayerManager.class)
        public interface NothingPlayerManager extends Nothing, PlayerManager
        {
            @VersionRange(end = 2002)
            @NothingInject(wrapperMethodName = "addPlayerV_2002", wrapperMethodParams = {
                ClientConnection.class,
                EntityPlayer.class
            }, locateMethod = "", type = NothingInjectType.INSERT_BEFORE)
            default Wrapper_void addPlayerBeginV_2002(
                @CustomVar("eventJoin") WrapperObject.Generic<EventPlayerJoin> event,
                @LocalVar(1) ClientConnection connection,
                @LocalVar(2) EntityPlayer player)
            {
                event.setWrapped(new EventPlayerJoin(player, connection));
                event.getWrapped().call();
                if(event.getWrapped().isCancelled())
                {
                    event.getWrapped().finish();
                    return Wrapper_void.FACTORY.create(null);
                }
                return Nothing.notReturn();
            }

            @VersionRange(begin = 2002)
            @NothingInject(wrapperMethodName = "addPlayerV2002", wrapperMethodParams = {
                ClientConnection.class,
                EntityPlayer.class,
                ClientConnectionDataV2002.class
            }, locateMethod = "", type = NothingInjectType.INSERT_BEFORE)
            default Wrapper_void addPlayerBeginV2002(
                @CustomVar("eventJoin") WrapperObject.Generic<EventPlayerJoin> wrapperEvent,
                @LocalVar(1) ClientConnection connection,
                @LocalVar(2) EntityPlayer player)
            {
                return this.addPlayerBeginV_2002(wrapperEvent, connection, player);
            }

            static void addPlayerEndLocate(NothingInjectLocating locating)
            {
                locating.allLater(AsmUtil.insnReturn(void.class).getOpcode());
                assert !locating.locations.isEmpty();
            }

            @VersionRange(end = 2002)
            @NothingInject(wrapperMethodName = "addPlayerV_2002", wrapperMethodParams = {
                ClientConnection.class,
                EntityPlayer.class
            }, locateMethod = "addPlayerEndLocate", type = NothingInjectType.INSERT_BEFORE)
            default Wrapper_void addPlayerEndV_2002(@CustomVar("eventJoin") WrapperObject.Generic<EventPlayerJoin> wrapperEvent)
            {
                wrapperEvent.getWrapped().finish();
                return Nothing.notReturn();
            }

            @VersionRange(begin = 2002)
            @NothingInject(wrapperMethodName = "addPlayerV2002", wrapperMethodParams = {
                ClientConnection.class,
                EntityPlayer.class,
                ClientConnectionDataV2002.class
            }, locateMethod = "addPlayerEndLocate", type = NothingInjectType.INSERT_BEFORE)
            default Wrapper_void addPlayerEndV2002(@CustomVar("eventJoin") WrapperObject.Generic<EventPlayerJoin> wrapperEvent)
            {
                return this.addPlayerEndV_2002(wrapperEvent);
            }
        }

        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(EventPlayerJoin.class);
            this.register(NothingPlayerManager.class);
        }
    }
}
