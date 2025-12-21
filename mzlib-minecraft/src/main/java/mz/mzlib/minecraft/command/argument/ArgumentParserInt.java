package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;

import java.util.Arrays;

public class ArgumentParserInt extends ArgumentParser<Integer>
{
    int[] presets;
    public ArgumentParserInt(String name, int... presets)
    {
        super(name);
        this.presets = presets;
    }

    @Override
    public Integer parse(CommandContext context)
    {
        return Integer.parseInt(new ArgumentParserString(
            this.name, false,
            Arrays.stream(this.presets).mapToObj(Integer::toString).toArray(String[]::new)
        ).parse(context));
    }
}
