package mz.mzlib.minecraft.network.packet;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PacketListenerRegistrar implements IRegistrar<PacketListener<?>>
{
    public static PacketListenerRegistrar instance=new PacketListenerRegistrar();

    public Class<PacketListener<?>> getType()
    {
        return RuntimeUtil.cast(PacketListener.class);
    }

    public Map<Class<?>, Set<PacketListener<?>>> listeners =new HashMap<>();
    @SuppressWarnings("deprecation")
    public synchronized void register(MzModule module, PacketListener<?> object)
    {
        Class<?> type = WrapperObject.getWrappedClass(object.packetClass);
        this.listeners.computeIfAbsent(type, k->new HashSet<>()).add(object);
        this.update(type);
    }
    @SuppressWarnings("deprecation")
    public synchronized void unregister(MzModule module, PacketListener<?> object)
    {
        Class<?> type = WrapperObject.getWrappedClass(object.packetClass);
        this.listeners.get(type).remove(object);
        this.update(type);
    }
    public Map<Class<?>, Boolean> sync=new ConcurrentHashMap<>();
    public Map<Class<?>, List<PacketListener<?>>> sortedListeners=new ConcurrentHashMap<>();
    public synchronized void update(Class<?> type)
    {
        sync.put(type,true);
        sortedListeners.put(type,listeners.get(type).stream().sorted((a, b)->Float.compare(b.priority,a.priority)).collect(Collectors.toList()));
        sync.put(type, listeners.get(type).stream().anyMatch(l->l.sync));
    }
}
