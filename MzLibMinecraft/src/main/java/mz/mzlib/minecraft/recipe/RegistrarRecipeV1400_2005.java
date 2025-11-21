package mz.mzlib.minecraft.recipe;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@VersionRange(begin = 1400, end = 2005)
public class RegistrarRecipeV1400_2005 extends RegistrarRecipe
{
    public static RegistrarRecipeV1400_2005 instance;

    Map<Object, Map<Object, Object>> rawRecipes0;

    Function<RecipeRegistration, WrapperObject> toData = MinecraftPlatform.instance.getVersion() < 2002 ?
        RecipeRegistration::getRecipeV1300 :
        RecipeEntryV2002::of;
    Function<WrapperObject, RecipeVanilla> toRecipe = MinecraftPlatform.instance.getVersion() < 2002 ?
        o -> o.as(RecipeVanilla.FACTORY) :
        o -> o.as(RecipeEntryV2002.FACTORY).getValue();

    @Override
    protected void updateOriginal()
    {
        this.rawRecipes0 = MinecraftServer.instance.getRecipeManagerV1300().getRecipes0V1400_2005();
        this.originalRecipes = Collections.unmodifiableMap(this.rawRecipes0.entrySet().stream()
            .collect(Collectors.toMap(
                entry -> RecipeTypeV1400.FACTORY.create(entry.getKey()), entry -> Collections.unmodifiableMap(
                    entry.getValue().entrySet().stream().collect(Collectors.toMap(
                        e -> Identifier.FACTORY.create(e.getKey()),
                        e -> toRecipe.apply(WrapperObject.FACTORY.create(e.getValue())).autoCast()
                    )))
            )));
    }

    @Override
    public synchronized void flush()
    {
        super.flush();
        Map<Object, Map<Object, Object>> result = new HashMap<>(this.rawRecipes0);
        for(Map.Entry<Object, Map<Object, Object>> entry : result.entrySet())
        {
            entry.setValue(new Object2ObjectLinkedOpenHashMap<>(entry.getValue())); // adapt for Bukkit
        }
        for(RecipeRegistration recipe : this.recipeRegistrations)
        {
            result.computeIfAbsent(recipe.getRecipeV1300().getTypeV1400().getWrapped(), k -> new HashMap<>())
                .put(recipe.getId().getWrapped(), toData.apply(recipe).getWrapped());
        }
        MinecraftServer.instance.getRecipeManagerV1300().setRecipes0V1400_2005(Collections.unmodifiableMap(result));
    }
}
