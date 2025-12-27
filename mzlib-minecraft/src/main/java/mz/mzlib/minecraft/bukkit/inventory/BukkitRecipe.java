package mz.mzlib.minecraft.bukkit.inventory;

import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.recipe.RecipeMojang;
import mz.mzlib.util.wrapper.WrapClassForName;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@MinecraftPlatform.Enabled(MinecraftPlatform.Tag.BUKKIT)
@WrapClassForName("org.bukkit.inventory.Recipe")
public interface BukkitRecipe extends WrapperObject
{
    WrapperFactory<BukkitRecipe> FACTORY = WrapperFactory.of(BukkitRecipe.class);

    static BukkitRecipe of(RecipeMojang recipe)
    {
        return FACTORY.create(new Delegator(recipe));
    }

    class Delegator implements org.bukkit.inventory.Recipe
    {
        RecipeMojang delegate;
        public Delegator(RecipeMojang delegate)
        {
            this.delegate = delegate;
        }
        public RecipeMojang getDelegate()
        {
            return this.delegate;
        }
        @Override
        public org.bukkit.inventory.ItemStack getResult()
        {
            return new org.bukkit.inventory.ItemStack(org.bukkit.Material.AIR);
        }
    }
}
