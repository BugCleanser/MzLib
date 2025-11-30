package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.CollectionUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@VersionRange(begin = 1800, end = 2005)
public class RegistrarRecipeV1800_2005 extends RegistrarRecipeV1400_2005
{
    public static RegistrarRecipeV1800_2005 instance;

    @Override
    public synchronized void flush()
    {
        super.flush();
        Map<Object, Object> result = new HashMap<>();
        for(Map.Entry<Identifier, Recipe> entry : CollectionUtil.asIterable(
            this.getEnabledRecipes().values().stream().map(Map::entrySet).flatMap(Set::stream).iterator()))
        {
            result.put(entry.getKey().getWrapped(), toData.apply(RecipeRegistration.of(entry.getKey(), entry.getValue())).getWrapped());
        }
        MinecraftServer.instance.getRecipeManagerV1300().setIdRecipes0V1800_2102(Collections.unmodifiableMap(result));
    }
}
