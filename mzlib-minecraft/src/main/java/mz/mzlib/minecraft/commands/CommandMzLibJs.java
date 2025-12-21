package mz.mzlib.minecraft.commands;

import mz.mzlib.minecraft.MinecraftJsUtil;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.argument.ArgumentParserString;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.text.TextColor;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.JsUtil;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.RhinoException;

import java.util.ArrayList;
import java.util.Objects;

public class CommandMzLibJs extends MzModule
{
    public static CommandMzLibJs instance = new CommandMzLibJs();

    public Permission permission = new Permission("mzlib.command.mzlib.js");

    public Command command;

    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(new ChildCommandRegistration(
            MzLibMinecraft.instance.command,
            this.command = new Command("js").setPermissionChecker(Command.permissionChecker(this.permission))
                .setHandler(this::handle)
        ));
    }

    public Object scope = MinecraftJsUtil.initScope();

    public void handle(CommandContext context)
    {
        String code = new ArgumentParserString("code", true).handle(context);
        if(!context.successful)
            return;
        if(!context.doExecute)
            return;
        try
        {
            JsUtil.put(this.scope, "context", context);
            Object result = JsUtil.eval(this.scope, code);
            if(result instanceof NativeJavaObject)
                result = result.getClass().getSimpleName() + ": " + ((NativeJavaObject) result).unwrap();
            else if(result instanceof NativeArray)
                result = "NativeArray: " + new ArrayList<Object>((NativeArray) result);
            else if(result != null)
                result = result.getClass().getSimpleName() + ": " + result;
            context.getSource().sendMessage(Text.literal(Objects.toString(result)).setColor(TextColor.GREEN));
        }
        catch(RhinoException e)
        {
            context.getSource().sendMessage(Text.literal(e.toString()).setColor(TextColor.RED));
        }
    }
}
