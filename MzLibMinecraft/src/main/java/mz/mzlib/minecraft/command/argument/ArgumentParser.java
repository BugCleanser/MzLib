package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;

public abstract class ArgumentParser<T>
{
    public String name;
    public ArgumentParser(String name)
    {
        this.name=name;
    }
    
    public abstract T parse(CommandContext context);
    public T handle(CommandContext context)
    {
        context.argNames.add(this.name);
        if(!context.successful)
            return null;
        try
        {
            return this.parse(context);
        }
        catch(Throwable e)
        {
            context.successful=false;
            // TODO: i18n
            context.suggestions.add("§4"+e.getMessage());
            return null;
        }
    }
}
