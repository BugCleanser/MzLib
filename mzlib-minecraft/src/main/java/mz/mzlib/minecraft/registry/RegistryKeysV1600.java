package mz.mzlib.minecraft.registry;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.util.wrapper.WrapperObject;

public interface RegistryKeysV1600
{
    RegistryKeyV1600<Registry.Wrapper<Item>> ITEM = of("item");
    RegistryKeyV1600<Registry.Wrapper<RecipeMojang>> RECIPE = of("recipe");

    static <T extends WrapperObject> RegistryKeyV1600<Registry.Wrapper<T>> of(String id)
    {
        return RegistryKeyV1600.ofRegistry(Identifier.of(id));
    }
}
