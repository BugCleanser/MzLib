package mz.mzlib.minecraft.event.player;

import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.network.ClientConnection;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

public class EventPlayerQuit extends EventPlayer
{
    public EventPlayerQuit(EntityPlayer player)
    {
        super(player);
    }
    
    @Override
    public void call()
    {
        super.call();
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventPlayerQuit.class);
            this.register(NothingClientConnection.class);
        }
        
        @WrapSameClass(ClientConnection.class)
        public interface NothingClientConnection extends ClientConnection, Nothing
        {
            @NothingInject(wrapperMethodName="handleDisconnection", wrapperMethodParams={}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_void handleDisconnectionBegin()
            {
                if(this.getChannel()==null || this.getChannel().isOpen())
                    return Nothing.notReturn();
                for(EntityPlayer player: this.getPlayer())
                {
                    new EventPlayerQuit(player).call();
                }
                return Nothing.notReturn();
            }
        }
    }
}
