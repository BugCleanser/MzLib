package mz.mzlib.minecraft.fabric;

import mz.mzlib.MzLib;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.ServerMetadata;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.permission.PermissionHelp;
import mz.mzlib.minecraft.vanilla.RegistrarCommandVanillaV1300;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.asm.AsmUtil;
import mz.mzlib.util.nothing.Nothing;
import mz.mzlib.util.nothing.NothingInject;
import mz.mzlib.util.nothing.NothingInjectLocating;
import mz.mzlib.util.nothing.NothingInjectType;
import mz.mzlib.util.wrapper.WrapSameClass;
import net.fabricmc.api.ModInitializer;

import java.util.concurrent.CompletableFuture;

public class MzLibFabric extends MzModule implements ModInitializer
{
    public static MzLibFabric instance;
    
    {
        instance = this;
    }
    
    public String MOD_ID = "mzlib";
    
    @Override
    public void onInitialize()
    {
        MzLib.instance.load();
        this.load();
    }
    
    public CompletableFuture<Void> futureServerStarted = new CompletableFuture<>();
    
    @Override
    public void onLoad()
    {
        this.register(MinecraftPlatformFabric.instance);
        this.register(NothingMinecraftServer.class);
        this.register(new PermissionHelp()
        {
            @Override
            public boolean check(CommandSource object, String permission)
            {
                return true;
            }
            @Override
            public boolean check(EntityPlayer player, String permission)
            {
                return true;
            }
            @Override
            public void register(MzModule module, Permission object)
            {
            
            }
            @Override
            public void unregister(MzModule module, Permission object)
            {
            
            }
        });
    }
    
    @WrapSameClass(MinecraftServer.class)
    public interface NothingMinecraftServer extends MinecraftServer, Nothing
    {
        static void locateRunBegin(NothingInjectLocating locating)
        {
            if(MinecraftPlatform.instance.getVersion()<1904)
            {
                locating.next(i->AsmUtil.isVisitingWrapped(locating.insns[i], MinecraftServer.class, "setFaviconV_1904", ServerMetadata.class));
                locating.offset(1);
            }
            else
            {
                locating.next(i->AsmUtil.isVisitingWrapped(locating.insns[i], MinecraftServer.class, "createMetadataV1904"));
                locating.offset(2);
            }
            assert locating.locations.size()==1;
        }
        
        @NothingInject(wrapperMethodName="run", wrapperMethodParams={}, locateMethod="locateRunBegin", type=NothingInjectType.INSERT_BEFORE)
        default void runBegin()
        {
            MzLibFabric.instance.register(this);
            MzLibFabric.instance.register(RegistrarCommandVanillaV1300.instance);
            MzLibFabric.instance.register(MzLibMinecraft.instance);
            MzLibFabric.instance.futureServerStarted.complete(null);
        }
    }
}
