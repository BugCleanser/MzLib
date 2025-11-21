package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.registry.RegistryManagerV1602;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryCrafting;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.input.RecipeInputV2100;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.world.World;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

@WrapMinecraftClass(
    {
        @VersionName(name = "net.minecraft.recipe.RecipeType", end = 1400),
        @VersionName(name = "net.minecraft.recipe.Recipe", begin = 1400)
    }
)
public interface RecipeVanilla extends WrapperObject, Recipe
{
    WrapperFactory<RecipeVanilla> FACTORY = WrapperFactory.of(RecipeVanilla.class);

    @VersionRange(end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "matches"))
    boolean matchesV_1300(InventoryCrafting inventory, World world);
    @VersionRange(begin = 1300, end = 2100)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "method_3500", end = 1400),
            @VersionName(name = "matches", begin = 1400)
        }
    )
    boolean matchesV1300_2100(Inventory inventory, World world);
    @VersionRange(begin = 2100)
    @WrapMinecraftMethod(@VersionName(name = "matches"))
    boolean matchesV2100(RecipeInputV2100 input, World world);

    @VersionRange(end = 1300)
    @WrapMinecraftMethod(@VersionName(name = "getResult"))
    ItemStack getResultV_1300(InventoryCrafting inventory);
    @VersionRange(begin = 1300, end = 1904)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "method_16201", end = 1400),
            @VersionName(name = "craft", begin = 1400)
        }
    )
    ItemStack getResultV1300_1904(Inventory inventory);
    @VersionRange(begin = 1904, end = 2005)
    @WrapMinecraftMethod(@VersionName(name = "craft"))
    ItemStack getResultV1904_2005(Inventory inventory, RegistryManagerV1602 registryManager);
    @VersionRange(begin = 2005, end = 2100)
    @WrapMinecraftMethod(@VersionName(name = "craft"))
    ItemStack getResultV2005_2100(Inventory inventory, RegistryEntryLookupV1903.class_7874 lookup);
    @VersionRange(begin = 2100)
    @WrapMinecraftMethod(@VersionName(name = "craft"))
    ItemStack getResultV2100(RecipeInputV2100 input, RegistryEntryLookupV1903.class_7874 lookup);


    @Override
    RecipeType getType();
    @SpecificImpl("getType")
    @VersionRange(end = 1300)
    default RecipeType getTypeV_1300()
    {
        return RecipeType.V_1400.CRAFTING;
    }
    @SpecificImpl("getType")
    @VersionRange(begin = 1300, end = 1400)
    default RecipeType getTypeV1300_1400()
    {
        if(this.is(RecipeVanillaCraftingV1300.FACTORY))
            return RecipeType.V_1400.CRAFTING;
        if(this.is(RecipeVanillaSmeltingV1300.FACTORY))
            return RecipeType.V_1400.SMELTING;
        throw new UnsupportedOperationException("Unsupported recipe type: " + this);
    }
    @SpecificImpl("getType")
    @VersionRange(begin = 1400)
    @WrapMinecraftMethod(@VersionName(name = "getType"))
    RecipeTypeV1400 getTypeV1400();

    default RecipeVanilla autoCast()
    {
        if(this.is(RecipeVanillaShaped.FACTORY))
            return this.as(RecipeVanillaShaped.FACTORY);
        if(this.is(RecipeVanillaShapeless.FACTORY))
            return this.as(RecipeVanillaShapeless.FACTORY);
        // TODO
        return this;
    }

    @VersionRange(end = 1200)
    default Identifier calcIdV_1200() // TODO
    {
        return Identifier.minecraft(
            this.getWrapped().getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase());
    }
}
