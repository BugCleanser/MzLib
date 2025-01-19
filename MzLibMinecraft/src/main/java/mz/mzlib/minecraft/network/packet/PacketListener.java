package mz.mzlib.minecraft.network.packet;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PacketListener<T extends Packet>
{
    public final Function<Object, T> wrapperCreator;
    public float priority;
    public BiConsumer<PacketEvent, T> handler;
    
    public PacketListener(Function<Object, T> wrapperCreator, float priority, BiConsumer<PacketEvent, T> handler)
    {
        this.wrapperCreator = wrapperCreator;
        this.priority = priority;
        this.handler = handler;
    }
    public PacketListener(Function<Object, T> wrapperCreator, BiConsumer<PacketEvent, T> handler)
    {
        this(wrapperCreator, 0.f, handler);
    }
    
    public void call(PacketEvent event, T packet)
    {
        this.handler.accept(event, packet);
    }
    public void call0(PacketEvent event, Packet packet)
    {
        this.handler.accept(event, packet.castTo(this.wrapperCreator));
    }
}
