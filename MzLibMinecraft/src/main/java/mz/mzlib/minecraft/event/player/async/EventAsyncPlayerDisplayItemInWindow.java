package mz.mzlib.minecraft.event.player.async;

import mz.mzlib.Priority;
import mz.mzlib.event.Cancellable;
import mz.mzlib.event.EventListener;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.fastutil.Int2ObjectMapV900;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.mzitem.MzItemIconPlaceholder;
import mz.mzlib.minecraft.network.packet.PacketEvent;
import mz.mzlib.minecraft.network.packet.PacketListener;
import mz.mzlib.minecraft.network.packet.c2s.play.PacketC2sWindowAction;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowItems;
import mz.mzlib.minecraft.network.packet.s2c.play.PacketS2cWindowSlotUpdate;
import mz.mzlib.minecraft.window.sync.ComponentChangesHashV2105;
import mz.mzlib.minecraft.window.sync.ItemStackHashV2105;
import mz.mzlib.minecraft.window.sync.TrackedSlotV2105;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.Option;
import mz.mzlib.util.Pair;
import mz.mzlib.util.proxy.MapProxy;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class EventAsyncPlayerDisplayItemInWindow extends EventAsyncPlayerDisplayItem
{
    public int slotIndex;
    public EventAsyncPlayerDisplayItemInWindow(
        EntityPlayer player,
        ItemStack original,
        int slotIndex)
    {
        super(player, original);
        this.slotIndex = slotIndex;
    }

    public abstract int getSyncId();
    /**
     * The slot index of the item
     * or -1 if it's the cursor V1701
     */
    public int getSlotIndex()
    {
        return this.slotIndex;
    }

    @Override
    public void call()
    {
        super.call();
    }

    public static class ByPacketS2cWindowSlotUpdate extends EventAsyncPlayerDisplayItemInWindow implements EventAsyncByPacket<PacketS2cWindowSlotUpdate>, EventAsyncByPacket.Cancellable
    {
        PacketEvent.Specialized<PacketS2cWindowSlotUpdate> packetEvent;
        public ByPacketS2cWindowSlotUpdate(
            PacketEvent.Specialized<PacketS2cWindowSlotUpdate> packetEvent,
            ItemStack original)
        {
            super(packetEvent.getPlayer().unwrap(), original, packetEvent.getPacket().getSlotIndex());
            this.packetEvent = packetEvent;
        }

        @Override
        public PacketEvent.Specialized<PacketS2cWindowSlotUpdate> getPacketEvent()
        {
            return this.packetEvent;
        }
        @Override
        public void sync(Runnable task)
        {
            EventAsyncByPacket.super.sync(task);
        }

        @Override
        public int getSyncId()
        {
            return this.getPacket().getSyncId();
        }
        @Override
        public ItemStack getItemStack()
        {
            return this.getPacket().getItemStack();
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.packetEvent.ensureCopied();
            this.getPacket().setItemStack(value);
        }
    }

    public static class ByPacketS2cWindowItems extends EventAsyncPlayerDisplayItemInWindow implements EventAsyncByPacket<PacketS2cWindowItems>
    {
        PacketEvent.Specialized<PacketS2cWindowItems> packetEvent;
        public ByPacketS2cWindowItems(
            PacketEvent.Specialized<PacketS2cWindowItems> packetEvent,
            ItemStack original,
            int slotIndex)
        {
            super(packetEvent.getPlayer().unwrap(), original, slotIndex);
            this.packetEvent = packetEvent;
        }

        @Override
        public PacketEvent.Specialized<PacketS2cWindowItems> getPacketEvent()
        {
            return this.packetEvent;
        }
        @Override
        public void sync(Runnable task)
        {
            EventAsyncByPacket.super.sync(task);
        }

        @Override
        public int getSyncId()
        {
            return this.getPacket().getSyncId();
        }
        @Override
        public ItemStack getItemStack()
        {
            if(this.getSlotIndex() == -1)
                return this.getPacket().getCursorV1701();
            else
                return this.getPacket().getContents().get(this.getSlotIndex());
        }
        @Override
        public void setItemStack(ItemStack value)
        {
            this.packetEvent.ensureCopied();
            if(this.getSlotIndex() == -1)
                this.getPacket().setCursorV1701(value);
            else
                this.getPacket().getContents().set(this.getSlotIndex(), value);
        }
    }

    public static class Module extends MzModule
    {
        public static Module instance = new Module();

        @Override
        public void onLoad()
        {
            this.register(EventAsyncPlayerDisplayItemInWindow.class);
            this.register(new PacketListener<>(
                PacketS2cWindowSlotUpdate.FACTORY,
                packetEvent -> new ByPacketS2cWindowSlotUpdate(
                    packetEvent, packetEvent.getPacket().getItemStack()
                ).call()
            ));
            this.register(new PacketListener<>(
                PacketS2cWindowItems.FACTORY,
                packetEvent ->
                {
                    for(int i = 0; i < packetEvent.getPacket().getContents().size(); i++)
                    {
                        new ByPacketS2cWindowItems(
                            packetEvent, packetEvent.getPacket().getContents().get(i),
                            i
                        ).call();
                    }
                }
            ));
            if(MinecraftPlatform.instance.getVersion() >= 1701)
                this.register(new PacketListener<>(
                    PacketS2cWindowItems.FACTORY,
                    packetEvent -> new ByPacketS2cWindowItems(
                        packetEvent, packetEvent.getPacket().getCursorV1701(), -1
                    ).call()
                ));

            if(MinecraftPlatform.instance.getVersion() >= 1700)
                this.register(ModuleSyncV1700.instance);
        }
    }

    public static class ModuleSyncV1700 extends MzModule
    {
        public static ModuleSyncV1700 instance = new ModuleSyncV1700();

        Map<EntityPlayer, Integer> cacheSyncId = new MapProxy<>(
            new WeakHashMap<>(), FunctionInvertible.wrapper(EntityPlayer.FACTORY), FunctionInvertible.identity());
        Map<EntityPlayer, Map<Integer, Pair</*original*/ItemStack, ItemStack>>> cache = new MapProxy<>(
            new WeakHashMap<>(), FunctionInvertible.wrapper(EntityPlayer.FACTORY), FunctionInvertible.identity());
        Map<EntityPlayer, Map<Integer, Pair</*original*/ItemStack, ItemStack>>> cachePlayer = new MapProxy<>(
            new WeakHashMap<>(), FunctionInvertible.wrapper(EntityPlayer.FACTORY), FunctionInvertible.identity());

        @Override
        public void onLoad()
        {
            this.register(new EventListener<>(
                EventAsyncPlayerDisplayItemInWindow.class, Priority.LOWEST,
                event ->
                {
                    if(Option.some(event).filter(Cancellable.class).map(Cancellable::isCancelled).unwrapOr(false))
                        return;
                    synchronized(ModuleSyncV1700.this)
                    {
                        if(event.getSyncId() > 0)
                            if(!Objects.equals(
                                this.cacheSyncId.put(event.getPlayer(), event.getSyncId()), event.getSyncId()))
                                this.cache.remove(event.getPlayer());
                        (event.getSyncId() > 0 ? this.cache : this.cachePlayer).computeIfAbsent(
                                event.getPlayer(), k -> new HashMap<>())
                            .put(event.getSlotIndex(), Pair.of(event.getOriginal(), event.getItemStack().clone0()));
                    }
                }
            ));
            // TODO
            abstract class Handler
            {
                abstract WrapperObject identifier(ItemStack itemStack);
                abstract WrapperObject identifierNone();
                abstract boolean identify(WrapperObject identifier, ItemStack itemStack);
            }
            Function<EntityPlayer, Handler> handlerFactory;
            if(MinecraftPlatform.instance.getVersion() < 2105)
                handlerFactory = player -> new Handler()
                {
                    @Override
                    ItemStack identifier(ItemStack itemStack)
                    {
                        return itemStack;
                    }
                    @Override
                    WrapperObject identifierNone()
                    {
                        return MzItemIconPlaceholder.instance; // TODO
                    }
                    @Override
                    boolean identify(WrapperObject identifier, ItemStack itemStack)
                    {
                        return itemStack.equals0(identifier);
                    }
                };
            else
                handlerFactory = player -> new Handler()
                {
                    final ComponentChangesHashV2105.ComponentHasher hasher = player
                        .getWindowSyncHandlerV1700()
                        .createTrackedSlotV2105().as(TrackedSlotV2105.Impl.FACTORY).getHasher();
                    final ItemStackHashV2105 identifier = ItemStackHashV2105.of(
                        MzItemIconPlaceholder.instance, this.hasher); // FIXME
                    @Override
                    ItemStackHashV2105 identifier(ItemStack itemStack)
                    {
                        return ItemStackHashV2105.of(itemStack, this.hasher);
                    }
                    @Override
                    public ItemStackHashV2105 identifierNone()
                    {
                        return this.identifier;
                    }
                    @Override
                    boolean identify(WrapperObject identifier, ItemStack itemStack)
                    {
                        return ((ItemStackHashV2105) identifier).hashEquals(itemStack, this.hasher);
                    }
                };
            this.register(new PacketListener<>(
                PacketC2sWindowAction.FACTORY, Priority.VERY_VERY_HIGH,
                packetEvent ->
                {
                    synchronized(ModuleSyncV1700.this)
                    {
                        if(packetEvent.getPacket().getSyncId() > 0)
                            if(!Objects.equals(
                                this.cacheSyncId.get(packetEvent.getPlayer().unwrap()),
                                packetEvent.getPacket().getSyncId()
                            ))
                                return;
                        Handler handler = handlerFactory.apply(packetEvent.getPlayer().unwrap());
                        Map<Integer, Pair<ItemStack, ItemStack>> map = (packetEvent.getPacket().getSyncId() > 0 ?
                            this.cache :
                            this.cachePlayer).get(packetEvent.getPlayer().unwrap());
                        if(map == null)
                            return;
                        BiFunction<Integer, WrapperObject, WrapperObject> compute = (i, value) ->
                        {
                            Pair<ItemStack, ItemStack> pair = map.get(i);
                            // FIXME: always sync
//                            if(pair != null && handler.identify(value, pair.getSecond()))
//                                return handler.identifier(pair.getFirst());
//                            else
                                return handler.identifierNone();
                        };
                        Int2ObjectMapV900<Object> modified0 = Int2ObjectMapV900.openHash();
                        Map<Integer, WrapperObject> modified = new MapProxy<>(
                            modified0.getWrapped(), FunctionInvertible.identity(),
                            FunctionInvertible.wrapper(WrapperObject.FACTORY)
                        );
                        for(Map.Entry<Integer, ? extends WrapperObject> entry : packetEvent.getPacket()
                            .getModifiedV1700()
                            .entrySet())
                        {
                            modified.put(entry.getKey(), compute.apply(entry.getKey(), entry.getValue()));
                        }
                        WrapperObject cursor = compute.apply(-1, packetEvent.getPacket().getCursorV1700());
                        packetEvent.setPacket(PacketC2sWindowAction.builder().from(packetEvent.getPacket())
                            .modified0V1700(modified0)
                            .cursorV1700(cursor)
                            .build());
                    }
                }
            ));
        }
    }
}
