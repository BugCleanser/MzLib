package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.command.CommandContext;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.util.RuntimeUtil;

public class ArgumentParserNbtCompound extends ArgumentParser<NbtCompound>
{
    public ArgumentParserNbtCompound(String name)
    {
        super(name);
    }
    public ArgumentParserNbtCompound()
    {
        this("nbt");
    }

    @Override
    public NbtCompound parse(CommandContext context)
    {
        StringBuilder result = new StringBuilder();
        do
        {
            result.append(context.argsReader.readString());
        } while(context.argsReader.hasNext() &&
            RuntimeUtil.runAndCatch(() -> NbtCompound.parse(result.toString())) != null);
        return NbtCompound.parse(result.toString());
    }
}
