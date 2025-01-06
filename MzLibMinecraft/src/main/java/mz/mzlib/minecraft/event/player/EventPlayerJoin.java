package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.PlayerManager;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.minecraft.network.ClientConnectionData;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

public class EventPlayerJoin extends EventPlayer
{
    public ClientConnection connection;
    public ClientConnectionData connectionData;
    public EventPlayerJoin(EntityPlayer player, ClientConnection connection, ClientConnectionData connectionData)
    {
        super(player);
        this.connection = connection;
        this.connectionData = connectionData;
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
            @NothingInject(wrapperMethod="addPlayer", locateMethod="", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_void addPlayerBegin(@CustomVar("eventJoin") WrapperObject wrapperEvent, @LocalVar(1) ClientConnection connection, @LocalVar(2) EntityPlayer player, @LocalVar(3) ClientConnectionData connectionData)
            {
                EventPlayerJoin event = new EventPlayerJoin(player, connection, connectionData);
                event.call();
                if(event.isCancelled())
                {
                    event.finish();
                    return Wrapper_void.create(null);
                }
                wrapperEvent.setWrapped(event);
                return Nothing.notReturn();
            }
            
            static void addPlayerEndLocate(NothingInjectLocating locating)
            {
                locating.allAfter(AsmUtil.insnReturn(void.class).getOpcode());
                assert !locating.locations.isEmpty();
            }
            
            @NothingInject(wrapperMethod="addPlayer", locateMethod="addPlayerEndLocate", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_void addPlayerEnd(@CustomVar("eventJoin") WrapperObject wrapperEvent)
            {
                ((EventPlayerJoin)wrapperEvent.getWrapped()).finish();
                return Nothing.notReturn();
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
