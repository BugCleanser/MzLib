package mz.mzlib.minecraft.network.packet;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RegistrarPacketListener implements IRegistrar<PacketListener<?>>
{
    public static RegistrarPacketListener instance = new RegistrarPacketListener();

    public Class<PacketListener<?>> getType()
    {
        return RuntimeUtil.cast(PacketListener.class);
    }

    public Map<Class<?>, Set<PacketListener<?>>> listeners = new HashMap<>();
    public synchronized void register(MzModule module, PacketListener<?> object)
    {
        Class<?> type = object.factory.getWrappedClass();
        this.listeners.computeIfAbsent(type, k -> new HashSet<>()).add(object);
        this.update(type);
    }
    public synchronized void unregister(MzModule module, PacketListener<?> object)
    {
        Class<?> type = object.factory.getWrappedClass();
        this.listeners.get(type).remove(object);
        this.update(type);
    }
    public Map<Class<?>, List<PacketListener<?>>> sortedListeners = new ConcurrentHashMap<>();
    public synchronized void update(Class<?> type)
    {
        sortedListeners.put(type, listeners.get(type).stream().sorted((a, b) -> Float.compare(b.priority, a.priority))
            .collect(Collectors.toList())
        );
    }
}
