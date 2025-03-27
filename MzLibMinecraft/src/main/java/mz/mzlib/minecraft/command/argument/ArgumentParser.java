package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.i18n.MinecraftI18n;

import java.util.Collections;

public abstract class ArgumentParser<T>
{
    public String name;
    public ArgumentParser(String name)
    {
        this.name = name;
    }
    
    public abstract T parse(CommandContext context);
    public T handle(CommandContext context)
    {
        context.argNames.add(this.name);
        if(!context.argsReader.hasNext())
            context.successful = false;
        if(!context.successful)
            return null;
        context.suggestions.clear();
        try
        {
            return this.parse(context);
        }
        catch(Throwable e)
        {
            context.addArgError(MinecraftI18n.resolveText(context.source, "mzlib.command.arg.error", Collections.singletonMap("msg", e.getMessage())));
            return null;
        }
    }
}
