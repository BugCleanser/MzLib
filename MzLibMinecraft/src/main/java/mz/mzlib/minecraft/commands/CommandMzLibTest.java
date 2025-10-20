package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.TesterContextPlayer;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParserInt;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.module.MzModule;
import mz.mzlib.tester.Tester;
import mz.mzlib.tester.TesterContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandMzLibTest extends MzModule
{
    public static CommandMzLibTest instance = new CommandMzLibTest();
    
    public Permission permission = new Permission("mzlib.command.mzlib.test");
    
    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(new ChildCommandRegistration(MzLibMinecraft.instance.command, //
                new Command("test").setPermissionChecker(Command.permissionChecker(this.permission)).setHandler(this::handle)));
    }
    
    public void handle(CommandContext context)
    {
        CommandContext fork = context.fork();
        Integer level;
        if(fork.argsReader.hasNext())
            fork.successful = false;
        if(fork.successful)
        {
            if(!fork.doExecute)
                return;
            level = Integer.MAX_VALUE;
        }
        else
        {
            level = new ArgumentParserInt("level", 0, 1, 2).handle(context);
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful)
                return;
            if(!context.doExecute)
                return;
        }
        TesterContext testerContext;
        if(context.getSource().getPlayer().isSome())
        {
            testerContext = new TesterContextPlayer(level, context.getSource().getPlayer().unwrap());
            context.getSource().sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.commands.mzlib.test.begin.player"));
        }
        else
        {
            testerContext = new TesterContext(level);
            context.getSource().sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.commands.mzlib.test.begin.non_player"));
        }
        Tester.testAll(testerContext).whenComplete((r, e)->
        {
            if(e!=null)
            {
                e.printStackTrace(System.err);
                return;
            }
            for(Map.Entry<Tester<?>, List<Throwable>> entry: r.entrySet())
            {
                System.err.println(entry.getKey().getName());
                for(Throwable t: entry.getValue())
                {
                    t.printStackTrace(System.err);
                }
            }
            MinecraftServer.instance.schedule(()->
            {
                if(r.isEmpty())
                    context.getSource().sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.commands.mzlib.test.success"));
                else
                    context.getSource().sendMessage(MinecraftI18n.resolveText(context.getSource(), "mzlib.commands.mzlib.test.failure", Collections.singletonMap("num", r.size())));
            });
        });
    }
}
