package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.CommandSource;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.util.MapBuilder;

public class ArgumentParserString extends ArgumentParser<String>
{
    public boolean allowSpace;
    String[] presets;
    
    public ArgumentParserString(String name, boolean allowSpace, String... presets)
    {
        super(name);
        this.allowSpace =allowSpace;
        this.presets=presets;
    }
    public ArgumentParserString(CommandSource source, boolean allowSpace, String... presets)
    {
        this(MinecraftI18n.resolve(source, "mzlib.command.arg.enum", MapBuilder.hashMap().put("enum", presets).get()), allowSpace, presets);
    }
    
    @Override
    public String parse(CommandContext context)
    {
        StringBuilder result = new StringBuilder();
        result.append(context.argsReader.readString());
        int spaces=0;
        while(this.allowSpace && context.argsReader.hasNext())
        {
            result.append(' ').append(context.argsReader.readString());
            spaces++;
        }
        if(!context.argsReader.hasNext())
        {
            for(String preset : this.presets)
            {
                if(preset.startsWith(result.toString()))
                    context.suggestions.add(preset.split(" ", spaces+1)[spaces]);
            }
        }
        return result.toString();
    }
}
