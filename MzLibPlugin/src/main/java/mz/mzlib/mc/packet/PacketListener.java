package mz.mzlib.mc.packet;

import mz.mzlib.util.delegator.Delegator;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.util.function.BiConsumer;

public class PacketListener<T extends Packet>
{
    public final Class<T> packetClass;
    public final CallSite packetCreator;
    public float priority;
    public boolean sync;
    public BiConsumer<PacketEvent,T> handler;

    public PacketListener(Class<T> packetClass,float priority,boolean sync,BiConsumer<PacketEvent,T> handler)
    {
        this.packetClass=packetClass;
        this.packetCreator=Delegator.getConstructorCallSite(null,"create", MethodType.methodType(Packet.class,Object.class),packetClass);
        this.priority=priority;
        this.sync=sync;
        this.handler=handler;
    }
    public PacketListener(Class<T> packetClass,float priority,BiConsumer<PacketEvent,T> handler)
    {
        this(packetClass,priority,false,handler);
    }
}
