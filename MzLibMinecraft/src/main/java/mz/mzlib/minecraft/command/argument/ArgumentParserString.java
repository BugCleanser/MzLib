package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;

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
    
    @Override
    public String parse(CommandContext context)
    {
        StringBuilder result = new StringBuilder();
        if(context.argsReader.hasNext())
            result.append(context.argsReader.readString());
        else
            context.successful=false;
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
