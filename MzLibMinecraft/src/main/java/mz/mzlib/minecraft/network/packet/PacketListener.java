package mz.mzlib.minecraft.network.packet;

import java.util.function.Consumer;
import java.util.function.Function;

public class PacketListener<T extends Packet>
{
    public final Function<Object, T> packetCreator;
    public float priority;
    public Consumer<PacketEvent.Specialized<T>> handler;
    
    public PacketListener(Function<Object, T> packetCreator, float priority, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this.packetCreator = packetCreator;
        this.priority = priority;
        this.handler = handler;
    }
    public PacketListener(Function<Object, T> packetCreator, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this(packetCreator, 0.f, handler);
    }
    
    public void call(PacketEvent event)
    {
        this.handler.accept(event.specialized(this.packetCreator));
    }
}
