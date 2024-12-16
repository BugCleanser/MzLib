package mz.mzlib.minecraft.network.packet;

import mz.mzlib.util.wrapper.WrapperObject;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.util.function.BiConsumer;

public class PacketListener<T extends Packet>
{
    public final Class<T> packetClass;
    public final CallSite packetCreator;
    public float priority;
    public BiConsumer<PacketEvent, T> handler;
    
    public PacketListener(Class<T> packetClass, float priority, BiConsumer<PacketEvent, T> handler)
    {
        this.packetClass = packetClass;
        this.packetCreator = WrapperObject.getConstructorCallSite(null, "create", MethodType.methodType(Packet.class, Object.class), packetClass);
        this.priority = priority;
        this.handler = handler;
    }
    public PacketListener(Class<T> packetClass, BiConsumer<PacketEvent, T> handler)
    {
        this(packetClass, 0.f, handler);
    }
}
