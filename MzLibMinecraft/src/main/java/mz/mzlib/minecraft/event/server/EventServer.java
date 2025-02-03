package mz.mzlib.minecraft.event.server;

import mz.mzlib.event.Event;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.ServerMetadata;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.*;
import mz.mzlib.util.wrapper.WrapSameClass;
import mz.mzlib.util.wrapper.WrapperObject;
import mz.mzlib.util.wrapper.basic.Wrapper_void;

public class EventServer extends Event
{
    public MinecraftServer server;
    public EventServer(MinecraftServer server)
    {
        this.server = server;
    }
    
    @Override
    public void call()
    {
    }
    
    public static class Module extends MzModule
    {
        public static Module instance = new Module();
        
        @Override
        public void onLoad()
        {
            this.register(EventServer.class);
            
            this.register(EventServerStart.class);
            this.register(EventServerStop.class);
            
            this.register(NothingMinecraftServer.class);
        }
        
        @WrapSameClass(MinecraftServer.class)
        public interface NothingMinecraftServer extends MinecraftServer, Nothing
        {
            @NothingInject(wrapperMethodName="run", wrapperMethodParams={}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
            default Wrapper_void runBegin(@CustomVar("eventStart") WrapperObject eventStart)
            {
                EventServerStart event = new EventServerStart(this);
                event.call();
                if(event.isCancelled())
                {
                    event.successful = false;
                    event.finish();
                    return Wrapper_void.create(null);
                }
                eventStart.setWrapped(event);
                return Nothing.notReturn();
            }
            
            static void locateRunSuccess(NothingInjectLocating locating)
            {
                if(MinecraftPlatform.instance.getVersion()<1904)
                {
                    locating.next(i->AsmUtil.isVisitingWrapped(locating.insns[i], MinecraftServer.class, "setFaviconV_1904", ServerMetadata.class));
                    locating.offset(1);
                }
                else
                {
                    locating.next(i->AsmUtil.isVisitingWrapped(locating.insns[i], MinecraftServer.class, "createMetadataV1904"));
                    locating.offset(2);
                }
                assert locating.locations.size()==1;
            }
            
            @NothingInject(wrapperMethodName="run", wrapperMethodParams={}, locateMethod="locateRunSuccess", type=NothingInjectType.INSERT_BEFORE)
            default void runSuccess(@CustomVar("eventStart") WrapperObject eventStart)
            {
                ((EventServerStart)eventStart.getWrapped()).finish();
            }
            
            @NothingInject(wrapperMethodName="run", wrapperMethodParams={}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
            default void runEnd(@CustomVar("eventStart") WrapperObject eventStart)
            {
                EventServerStart event = (EventServerStart)eventStart.getWrapped();
                if(!event.isFinished())
                {
                    event.successful = false;
                    event.finish();
                }
            }
            
            @NothingInject(wrapperMethodName="onStop", wrapperMethodParams={}, locateMethod="", type=NothingInjectType.INSERT_BEFORE)
            default void onStopBegin(@CustomVar("eventStop") WrapperObject eventStop)
            {
                EventServerStop event = new EventServerStop(this);
                event.call();
            }
            
            @NothingInject(wrapperMethodName="onStop", wrapperMethodParams={}, locateMethod="locateAllReturn", type=NothingInjectType.INSERT_BEFORE)
            default void onStopEnd(@CustomVar("eventStop") WrapperObject eventStop)
            {
                ((EventServerStop)eventStop.getWrapped()).finish();
            }
        }
    }
}
