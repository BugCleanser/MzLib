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
    public T process(CommandContext context)
    {
        context.argNames.add(this.name);
        if(!context.successful)
            return null;
        return this.parse(context);
    }
}
