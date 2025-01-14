package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.i18n.I18nMinecraft;
import mz.mzlib.minecraft.text.Text;

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
            context.addArgError(Text.literal(String.format(I18nMinecraft.getTranslation(context.source, "mzlib.command.arg.error"), e.getMessage())));
            return null;
        }
    }
}
