package mz.mzlib.minecraft;

import mz.mzlib.minecraft.command.CommandManager;
import mz.mzlib.minecraft.command.CommandOutput;
import mz.mzlib.minecraft.datafixer.DataFixerV1300;
import mz.mzlib.minecraft.datafixer.DataUpdaterV_1300;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.incomprehensible.registry.RegistryManagerV1602;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftFieldAccessor;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.Instance;
import mz.mzlib.util.Pair;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.StrongRef;
import mz.mzlib.util.async.AsyncFunctionRunner;
import mz.mzlib.util.async.BasicAwait;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.BooleanSupplier;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.MinecraftServer"))
public interface MinecraftServer extends WrapperObject, CommandOutput, Instance, AsyncFunctionRunner
{
    @WrapperCreator
    static MinecraftServer create(Object wrapped)
    {
        return WrapperObject.create(MinecraftServer.class, wrapped);
    }
    
    MinecraftServer instance = RuntimeUtil.nul();
    
    @WrapMinecraftFieldAccessor(@VersionName(name="serverThread"))
    Thread getThread();
    
    default List<EntityPlayer> getPlayers()
    {
        return this.getPlayerManager().getPlayers();
    }
    
    @WrapMinecraftMethod(@VersionName(name="getPlayerManager"))
    PlayerManager getPlayerManager();
    
    @WrapMinecraftMethod({@VersionName(name="getCommandManager", end=1300), @VersionName(name="method_2971", begin=1300, end=1400), @VersionName(name="getCommandManager", begin=1400)})
    CommandManager getCommandManager();
    
    @VersionRange(end=1300)
    @WrapMinecraftMethod(@VersionName(name="setupWorld"))
    void tickV_1300();
    
    @VersionRange(begin=1300)
    @WrapMinecraftMethod({@VersionName(name="method_20324", end=1400), @VersionName(name="tick", begin=1400)})
    void tickV1300(BooleanSupplier booleanSupplier);
    
    Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    
    @Override
    default void schedule(Runnable task)
    {
        tasks.add(task);
    }
    
    StrongRef<Long> tickNumber = new StrongRef<>(0L);
    Queue<Pair<Long, Runnable>> waitingTasks = new PriorityBlockingQueue<>(11, Collections.reverseOrder(Pair.comparingByFirst()));
    
    @Override
    default void schedule(Runnable task, BasicAwait await)
    {
        if(await instanceof SleepTicks)
        {
            if(((SleepTicks)await).ticks==0)
                this.schedule(task);
            else
                waitingTasks.add(new Pair<>(tickNumber.get()+((SleepTicks)await).ticks, task));
        }
        else
            throw new UnsupportedOperationException();
    }
    
    @VersionRange(end=1300)
    @WrapMinecraftFieldAccessor(@VersionName(name="dataFixer"))
    DataUpdaterV_1300 getDataUpdaterV_1300();
    
    @VersionRange(begin=1300)
    @WrapMinecraftFieldAccessor({@VersionName(name="field_21612", end=1400), @VersionName(name="dataFixer", begin=1400)})
    DataFixerV1300 getDataUpdaterV1300();
    
    /**
     * 1.12.2: 1343
     * 1.13.2: 1631
     * 1.14: 1952
     * 1.19: 3105
     * 1.20.1: 3465
     * 1.20.5: 3837
     */
    int getDataVersion();
    
    @SpecificImpl("getDataVersion")
    @VersionRange(end=1300)
    default int getDataVersionV_1300()
    {
        return this.getDataUpdaterV_1300().getDataVersion();
    }
    
    @SpecificImpl("getDataVersion")
    @VersionRange(begin=1300, end=1302)
    default int getDataVersionUnsupported()
    {
        throw new UnsupportedOperationException();
    }
    @SpecificImpl("getDataVersion")
    @VersionRange(begin=1302, end=1400)
    default int getDataVersionV1302_1400()
    {
        return 1631;
    }
    
    @SpecificImpl("getDataVersion")
    @VersionRange(begin=1400, end=1800)
    default int getDataVersionV1400_1800()
    {
        return GlobalConstants.getMinecraftVersionV1400_1800().getDataVersion();
    }
    
    @SpecificImpl("getDataVersion")
    @VersionRange(begin=1800)
    default int getDataVersionV1800()
    {
        return GlobalConstants.getMinecraftVersionV1800().getDataVersion().getNumber();
    }
    
    @VersionRange(begin=1602)
    RegistryManagerV1602 getRegistriesV1602();
    
    @SpecificImpl("getRegistriesV1602")
    @WrapMinecraftMethod(@VersionName(name="getRegistryManager", begin=1602, end=1802))
    RegistryManagerV1602 getRegistriesV1602_1802();
    
    @SpecificImpl("getRegistriesV1602")
    @WrapMinecraftMethod(@VersionName(name="getRegistryManager", begin=1802))
    RegistryManagerV1602.Immutable getRegistriesV1802();
    
    @WrapMinecraftMethod({@VersionName(name="run", end=1400), @VersionName(name="method_29741", begin=1400)})
    void run();
    
    @VersionRange(end=1904)
    @WrapMinecraftMethod({@VersionName(name="setServerMeta", end=1400), @VersionName(name="setFavicon", begin=1400)})
    void setFaviconV_1904(ServerMetadata metadata);
    
    @VersionRange(begin=1904)
    @WrapMinecraftMethod(@VersionName(name="createMetadata"))
    ServerMetadata createMetadataV1904();
    
    @WrapMinecraftMethod({@VersionName(name="stopServer", end=1400), @VersionName(name="shutdown", begin=1400)})
    void onStop();
}
