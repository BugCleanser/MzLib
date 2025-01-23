package mz.mzlib.minecraft.vanilla;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandManager;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.command.brigadier.CommandDispatcherV1300;
import mz.mzlib.module.IRegistrar;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.RuntimeUtil;

public class RegistrarCommandVanillaV1300 implements IRegistrar<Command>
{
    @Override
    public Class<Command> getType()
    {
        return Command.class;
    }
    
    public com.mojang.brigadier.Command<Object> executor(Command command)
    {
        return context->
        {
            command.execute(CommandSource.create(context.getSource()), context.getInput(), null);
            return 1;
        };
    }
    
    @Override
    public void register(MzModule module, Command command)
    {
        LiteralCommandNode<?> node = CommandManager.instance.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(command.namespace!=null ? command.namespace+":"+command.name : command.name).executes(executor(command)).then(RequiredArgumentBuilder.argument("args", StringArgumentType.greedyString()).executes(context->
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
            CommandManager.instance.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(command.name).executes(executor(command)).redirect(RuntimeUtil.cast(node))));
        for(String alias: command.aliases)
        {
            CommandManager.instance.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(alias).executes(executor(command)).redirect(RuntimeUtil.cast(node))));
            if(command.namespace!=null)
                CommandManager.instance.getDispatcher().register(RuntimeUtil.cast(LiteralArgumentBuilder.literal(command.namespace+":"+alias).executes(executor(command)).redirect(RuntimeUtil.cast(node))));
        }
        CommandManager.instance.updateAll();
    }
    
    @Override
    public void unregister(MzModule module, Command command)
    {
        if(command.namespace!=null)
            CommandDispatcherV1300.create(CommandManager.instance.getDispatcher()).getRoot().removeChild(command.namespace+":"+command.name);
        CommandDispatcherV1300.create(CommandManager.instance.getDispatcher()).getRoot().removeChild(command.name);
        for(String alias: command.aliases)
        {
            CommandDispatcherV1300.create(CommandManager.instance.getDispatcher()).getRoot().removeChild(alias);
            if(command.namespace!=null)
                CommandDispatcherV1300.create(CommandManager.instance.getDispatcher()).getRoot().removeChild(command.namespace+":"+alias);
        }
        CommandManager.instance.updateAll();
    }
}
