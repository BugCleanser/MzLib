package mz.mzlib.minecraft.network.packet;

import mz.mzlib.util.wrapper.WrapperFactory;

import java.util.function.Consumer;

public class PacketListener<T extends Packet>
{
    public final WrapperFactory<T> factory;
    public float priority;
    public Consumer<PacketEvent.Specialized<T>> handler;

    public PacketListener(WrapperFactory<T> factory, float priority, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this.factory = factory;
        this.priority = priority;
        this.handler = handler;
    }
    public PacketListener(WrapperFactory<T> factory, Consumer<PacketEvent.Specialized<T>> handler)
    {
        this(factory, 0.f, handler);
    }

    public void call(PacketEvent event)
    {
        this.handler.accept(event.specialize(this.factory));
    }
}
