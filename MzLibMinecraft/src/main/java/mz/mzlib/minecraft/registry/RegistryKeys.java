package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;

public interface RegistryKeys
{
    RegistryKeyV1600 RECIPE = of("recipe");

    static RegistryKeyV1600 of(String id)
    {
        return RegistryKeyV1600.ofRegistry(Identifier.newInstance(id));
    }
}
