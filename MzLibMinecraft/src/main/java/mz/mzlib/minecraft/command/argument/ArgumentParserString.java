package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.command.CommandSender;
import mz.mzlib.minecraft.i18n.I18nMinecraft;

import java.util.Arrays;

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
    public ArgumentParserString(CommandSender sender, boolean allowSpace, String... presets)
    {
        this(String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.arg.enum"), String.join(I18nMinecraft.getTranslation(sender, "mzlib.command.arg.enum.value.delimiter"), Arrays.stream(presets).map(i->String.format(I18nMinecraft.getTranslation(sender, "mzlib.command.arg.enum.value"), i)).toArray(String[]::new))), allowSpace, presets);
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
