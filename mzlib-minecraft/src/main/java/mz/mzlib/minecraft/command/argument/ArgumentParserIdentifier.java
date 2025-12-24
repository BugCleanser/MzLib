package mz.mzlib.minecraft.command.argument;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.command.CommandContext;

import java.util.Set;
import java.util.stream.Collectors;

public class ArgumentParserIdentifier extends ArgumentParser<Identifier>
{
    Set<Identifier> presets;
    public ArgumentParserIdentifier(String name, Set<Identifier> presets)
    {
        super(name);
        this.presets = presets;
    }
    public ArgumentParserIdentifier(Set<Identifier> presets)
    {
        this("id", presets);
    }

    @Override
    public Identifier parse(CommandContext context)
    {
        String str = context.argsReader.readString();
        if(str.contains(":"))
            this.presets.stream().map(Identifier::toString).filter(i -> i.startsWith(str))
                .forEach(context.suggestions::add);
        else
        {
            for(Identifier i : this.presets)
            {
                if(!i.isMinecraft())
                    continue;
                if(i.getName().startsWith(str))
                    context.suggestions.add(i.getName());
            }
            for(String namespace : this.presets.stream().map(Identifier::getNamespace).collect(Collectors.toSet()))
            {
                if(namespace.startsWith(str))
                    context.suggestions.add(namespace + ":");
            }
            for(Identifier i : this.presets)
            {
                if(i.isMinecraft())
                    continue;
                if(i.getName().startsWith(str))
                    context.suggestions.add(i.toString());
            }
        }
        return Identifier.of(str);
    }
}
