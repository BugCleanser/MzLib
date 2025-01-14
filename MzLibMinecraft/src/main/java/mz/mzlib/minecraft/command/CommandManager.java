package mz.mzlib.minecraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.command.brigadier.CommandDispatcherV1300;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(@VersionName(name="net.minecraft.server.command.CommandManager"))
public interface CommandManager extends WrapperObject, IRegistrar<Command>
{
    CommandManager instance = RuntimeUtil.nul();
    
    @WrapMinecraftMethod(@VersionName(name="getDispatcher"))
    CommandDispatcher<?> getDispatcher();
    
    @WrapMinecraftMethod(@VersionName(name="sendCommandTree"))
    void sendCommandTree(EntityPlayer player);
    
    @Override
    default Class<Command> getType()
    {
        return Command.class;
    }
    
    default com.mojang.brigadier.Command<Object> executor(Command command)
    {
        return context->
        {
            command.execute(CommandSource.create(context.getSource()), context.getInput(), null);
            return 1;
        };
    }
    
    @Override
    default void register(MzModule module, Command command)
    {
        LiteralCommandNode<?> node = this.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(command.namespace!=null ? command.namespace+":"+command.name : command.name).executes(executor(command)).then(RequiredArgumentBuilder.argument("args", StringArgumentType.greedyString()).executes(context->
        {
            command.execute(CommandSource.create(context.getSource()), context.getInput().substring(0, context.getRange().getStart()-1), context.getInput().substring(context.getRange().getStart()));
            return 1;
        }).suggests((context, b)->
        {
            b = b.createOffset(context.getInput().lastIndexOf(' ')+1);
            String[] argv2 = context.getInput().substring(0, b.getStart()+b.getRemaining().length()).split(" ", 2);
            for(String s: command.suggest(CommandSource.create(context.getSource()), argv2[0], argv2[1]))
            {
                b.suggest(s, null);
            }
            return b.buildFuture();
        }).build())));
        if(command.namespace!=null)
            this.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(command.name).executes(executor(command)).redirect(RuntimeUtil.cast(node))));
        for(String alias: command.aliases)
        {
            this.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(alias).executes(executor(command)).redirect(RuntimeUtil.cast(node))));
            if(command.namespace!=null)
                this.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(command.namespace+":"+alias).executes(executor(command)).redirect(RuntimeUtil.cast(node))));
        }
        updateAll();
    }
    
    @Override
    default void unregister(MzModule module, Command command)
    {
        if(command.namespace!=null)
            CommandDispatcherV1300.create(this.getDispatcher()).getRoot().removeChild(command.namespace+":"+command.name);
        CommandDispatcherV1300.create(this.getDispatcher()).getRoot().removeChild(command.name);
        for(String alias: command.aliases)
        {
            CommandDispatcherV1300.create(this.getDispatcher()).getRoot().removeChild(alias);
            if(command.namespace!=null)
                CommandDispatcherV1300.create(this.getDispatcher()).getRoot().removeChild(command.namespace+":"+alias);
        }
        updateAll();
    }
    
    default void updateAll()
    {
        for(EntityPlayer player:MinecraftServer.instance.getPlayers())
            this.sendCommandTree(player);
    }
}
