package mz.mzlib.minecraft.recipe;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.VersionName;
import mz.mzlib.minecraft.VersionRange;
import mz.mzlib.minecraft.incomprehensible.context.MojangContextV2102;
import mz.mzlib.minecraft.incomprehensible.context.SlotDisplayContextsV2102;
import mz.mzlib.minecraft.incomprehensible.registry.RegistryManagerV1602;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryCrafting;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.recipe.display.RecipeDisplayV2102;
import mz.mzlib.minecraft.recipe.input.RecipeInputV2100;
import mz.mzlib.minecraft.registry.entry.RegistryEntryLookupV1903;
import mz.mzlib.minecraft.world.World;
import mz.mzlib.minecraft.wrapper.WrapMinecraftClass;
import mz.mzlib.minecraft.wrapper.WrapMinecraftMethod;
import mz.mzlib.util.FunctionInvertible;
import mz.mzlib.util.proxy.ListProxy;
import mz.mzlib.util.wrapper.SpecificImpl;
import mz.mzlib.util.wrapper.WrapperFactory;
import mz.mzlib.util.wrapper.WrapperObject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    RecipeVanilla autoCast();

    @Override
    List<ItemStack> getIcons();

    @SpecificImpl("getIcons")
    @VersionRange(begin = 2102)
    default List<ItemStack> getIconsV2102()
    {
        MojangContextV2102 context = SlotDisplayContextsV2102.create();
        return this.getDisplaysV2102().stream().map(RecipeDisplayV2102::getResult)
            .map(it -> it.getItemStacks(context)).flatMap(List::stream).collect(Collectors.toList());
    }
    default List<RecipeDisplayV2102> getDisplaysV2102()
    {
        return new ListProxy<>(this.getDisplays0V2102(), FunctionInvertible.wrapper(RecipeDisplayV2102.FACTORY));
    }
    @VersionRange(begin = 2102)
    @WrapMinecraftMethod(@VersionName(name = "getDisplays"))
    List<Object> getDisplays0V2102();

    @VersionRange(end = 1200)
    default Identifier calcIdV_1200() // TODO
    {
        return Identifier.minecraft(
            this.getWrapped().getClass().getSimpleName().replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase());
    }


    @SpecificImpl("getType")
    @VersionRange(end = 1300)
    default RecipeType getTypeV_1300()
    {
        return RecipeType.CRAFTING;
    }
    @SpecificImpl("getType")
    @VersionRange(begin = 1300, end = 1400)
    default RecipeType getTypeV1300_1400()
    {
        if(this.is(RecipeVanillaSmeltingV1300.FACTORY))
            return RecipeType.SMELTING;
        return RecipeType.CRAFTING;
    }
    @SpecificImpl("getType")
    @VersionRange(begin = 1400)
    @WrapMinecraftMethod(@VersionName(name = "getType"))
    RecipeTypeV1400 getTypeV1400();

    // TODO
    @SpecificImpl("autoCast")
    @VersionRange(end = 1300)
    default RecipeVanilla autoCastV_1300()
    {
        if(this.is(RecipeVanillaShaped.FACTORY))
            return this.as(RecipeVanillaShaped.FACTORY);
        if(this.is(RecipeVanillaShapeless.FACTORY))
            return this.as(RecipeVanillaShapeless.FACTORY);
        return this;
    }
    @SpecificImpl("autoCast")
    @VersionRange(begin = 1300, end = 1400)
    default RecipeVanilla autoCastV1300_1400()
    {
        if(this.is(RecipeVanillaSmeltingV1300.FACTORY))
            return this.as(RecipeVanillaSmeltingV1300.FACTORY);
        return this.autoCastV_1300();
    }
    @SpecificImpl("autoCast")
    @VersionRange(begin = 1400)
    default RecipeVanilla autoCastV1400()
    {
        RecipeVanilla result = this;
        if(result.is(RecipeVanillaCraftingV1400.FACTORY))
            result = result.as(RecipeVanillaCraftingV1400.FACTORY);
        return result.autoCastV1300_1400();
    }

    @SpecificImpl("getIcons")
    @VersionRange(end = 2102)
    default List<ItemStack> getIconsV_2102()
    {
        return Collections.singletonList(this.getIconV_2102());
    }
    @VersionRange(end = 2102)
    ItemStack getIconV_2102();
    @SpecificImpl("getIconV_2102")
    @VersionRange(end = 1904)
    @WrapMinecraftMethod(
        {
            @VersionName(name = "getOutput", end = 1400),
            @VersionName(name = "method_8110", begin = 1400)
        }
    )
    ItemStack getIconV_1904();
    @SpecificImpl("getIconV_2102")
    @VersionRange(begin = 1904, end = 2005)
    default ItemStack getIconV1904_2005()
    {
        return this.getIconV1904_2005(MinecraftServer.instance.getRegistriesV1602());
    }
    @VersionRange(begin = 1904, end = 2005)
    @WrapMinecraftMethod(@VersionName(name = "method_8110"))
    ItemStack getIconV1904_2005(RegistryManagerV1602 registryManager);
    @SpecificImpl("getIconV_2102")
    @VersionRange(begin = 2005, end = 2102)
    default ItemStack getIconV2005_2102()
    {
        return this.getIconV2005_2102(
            MinecraftServer.instance.getRegistriesV1602().as(RegistryEntryLookupV1903.class_7874.FACTORY));
    }
    @VersionRange(begin = 2005, end = 2102)
    @WrapMinecraftMethod(@VersionName(name = "method_8110"))
    ItemStack getIconV2005_2102(RegistryEntryLookupV1903.class_7874 registriesLookup);
}
