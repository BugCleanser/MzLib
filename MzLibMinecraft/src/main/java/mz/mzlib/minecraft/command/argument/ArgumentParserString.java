package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;

public class ArgumentParserString extends ArgumentParser<String>
{
    public boolean withSpace;
    String[] presets;
    
    public ArgumentParserString(String name, boolean withSpace, String... presets)
    {
        super(name);
        this.withSpace=withSpace;
        this.presets=presets;
    }
    
    @Override
    public String parse(CommandContext context)
    {
        StringBuilder result = new StringBuilder();
        if(context.argsReader.hasNext())
            result.append(context.argsReader.next());
        int spaces=0;
        while(this.withSpace && context.argsReader.hasNext())
        {
            result.append(' ').append(context.argsReader.next());
            spaces++;
        }
        if(!context.argsReader.hasNext())
        {
            context.successful=false;
            for(String preset : this.presets)
            {
                if(preset.startsWith(result.toString()))
                    context.suggestions.add(preset.split(" ", spaces+1)[spaces]);
            }
        }
        return result.toString();
    }
}
