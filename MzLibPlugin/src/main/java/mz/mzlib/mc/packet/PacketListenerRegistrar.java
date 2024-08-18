package mz.mzlib.mc.packet;

import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.delegator.Delegator;

import java.util.*;
import java.util.stream.Collectors;

public class PacketListenerRegistrar implements IRegistrar<PacketListener<?>>
{
    public static PacketListenerRegistrar instance=new PacketListenerRegistrar();

    public Class<PacketListener<?>> getType()
    {
        return RuntimeUtil.cast(PacketListener.class);
    }

    public Map<Class<?>, Set<PacketListener<?>>> listener=new HashMap<>();
    @SuppressWarnings("deprecation")
    public synchronized void register(MzModule module, PacketListener<?> object)
    {
        this.listener.computeIfAbsent(Delegator.getDelegateClass(object.packetClass),k->new HashSet<>()).add(object);
        this.update();
    }
    @SuppressWarnings("deprecation")
    public synchronized void unregister(MzModule module, PacketListener<?> object)
    {
        this.listener.get(Delegator.getDelegateClass(object.packetClass)).remove(object);
        this.update();
    }
    public Map<Class<?>, List<PacketListener<?>>> sortedSyncListeners=new HashMap<>();
    public Map<Class<?>, List<PacketListener<?>>> sortedAsyncListeners=new HashMap<>();
    public synchronized void update()
    {
        this.sortedSyncListeners=listener.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,s->s.getValue().stream().filter(l->l.sync).sorted((a,b)->Float.compare(b.priority,a.priority)).collect(Collectors.toList())));
        this.sortedAsyncListeners=listener.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,s->s.getValue().stream().filter(l->!l.sync).sorted((a,b)->Float.compare(b.priority,a.priority)).collect(Collectors.toList())));
    }
}
