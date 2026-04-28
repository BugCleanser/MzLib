package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Collections;

public abstract class ArgumentParser<T extends @Nullable Object>
{
    public String name;
    public ArgumentParser(String name)
    {
        this.name = name;
    }

    public abstract T parse(CommandContext context) throws Throwable;
    public @UnknownNullability T handle(CommandContext context)
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
            context.addArgError(MinecraftI18n.resolveText(
                context.source, "mzlib.command.arg.error",
                Collections.singletonMap("msg", e.getMessage())
            ));
            return null;
        }
    }
}
