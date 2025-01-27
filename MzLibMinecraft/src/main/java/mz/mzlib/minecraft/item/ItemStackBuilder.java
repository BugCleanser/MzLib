package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.text.Text;

import java.util.Arrays;

public class ItemStackBuilder
{
    public static Colored wool = new Colored("wool");
    public static Colored glass = new Colored("glass");
    public static Colored concrete = new Colored("concrete");
    public static Colored concretePowder = new Colored("concrete_powder");
    public static Colored stainedHardenedClay = new Colored("stained_hardened_clay");
    public static Colored stainedGlassPane = new Colored("stained_glass_pane");
    public static Colored carpet = new Colored("carpet");
    public static Colored shulkerBox = new Colored("shulker_box");
    public static Colored glazedTerracotta = new Colored("glazed_terracotta");
    public static Colored bed = new Colored("bed");
    public static Colored banner = new Colored("banner");
    public static Colored dye = new Colored("dye");
    
    public static ItemStackBuilder forFlattening(String idV_1300, int damageV_1300, String idV1300)
    {
        if(MinecraftPlatform.instance.getVersion()<1300)
            return new ItemStackBuilder(idV_1300).setDamageV_1300(damageV_1300);
        else
            return new ItemStackBuilder(idV1300);
    }
    public static ItemStackBuilder forColored(String baseId, String color)
    {
        if(MinecraftPlatform.instance.getVersion()<1300)
            return new ItemStackBuilder(baseId).setDamageV_1300(Item.damageForColorV_1300(color));
        else
            return new ItemStackBuilder(color+"_"+baseId);
    }
    
    public ItemStack result;
    public ItemStackBuilder(ItemStack itemStack)
    {
        this.result = itemStack;
    }
    public ItemStackBuilder(Item item, int count)
    {
        this.result = ItemStack.newInstance(item, count);
    }
    public ItemStackBuilder(Item item)
    {
        this(item, 1);
    }
    
    public ItemStackBuilder(Identifier item, int count)
    {
        this(Item.fromId(item), count);
    }
    public ItemStackBuilder(Identifier item)
    {
        this(item, 1);
    }
    
    public ItemStackBuilder(String item, int count)
    {
        this(Identifier.newInstance(item), count);
    }
    public ItemStackBuilder(String item)
    {
        this(item, 1);
    }
    
    public ItemStackBuilder setCount(int count)
    {
        this.result.setCount(count);
        return this;
    }
    
    public ItemStackBuilder setDamageV_1300(int damage)
    {
        this.result.setDamageV_1300(damage);
        return this;
    }
    
    public ItemStackBuilder setCustomName(Text value)
    {
        Item.setCustomName(this.result, value);
        return this;
    }
    
    public ItemStackBuilder setLore(Text... value)
    {
        Item.setLore(this.result, Arrays.asList(value));
        return this;
    }
    
    public ItemStack get()
    {
        return this.result;
    }
    
    public static class Colored
    {
        public String baseId;
        public Colored(String baseId)
        {
            this.baseId = baseId;
        }
        
        public ItemStackBuilder white()
        {
            return ItemStackBuilder.forColored(this.baseId, "white");
        }
        public ItemStackBuilder orange()
        {
            return ItemStackBuilder.forColored(this.baseId, "orange");
        }
        public ItemStackBuilder magenta()
        {
            return ItemStackBuilder.forColored(this.baseId, "magenta");
        }
        public ItemStackBuilder lightBlue()
        {
            return ItemStackBuilder.forColored(this.baseId, "light_blue");
        }
        public ItemStackBuilder yellow()
        {
            return ItemStackBuilder.forColored(this.baseId, "yellow");
        }
        public ItemStackBuilder lime()
        {
            return ItemStackBuilder.forColored(this.baseId, "lime");
        }
        public ItemStackBuilder pink()
        {
            return ItemStackBuilder.forColored(this.baseId, "pink");
        }
        public ItemStackBuilder gray()
        {
            return ItemStackBuilder.forColored(this.baseId, "gray");
        }
        public ItemStackBuilder lightGray()
        {
            return ItemStackBuilder.forColored(this.baseId, "light_gray");
        }
        public ItemStackBuilder cyan()
        {
            return ItemStackBuilder.forColored(this.baseId, "cyan");
        }
        public ItemStackBuilder purple()
        {
            return ItemStackBuilder.forColored(this.baseId, "purple");
        }
        public ItemStackBuilder blue()
        {
            return ItemStackBuilder.forColored(this.baseId, "blue");
        }
        public ItemStackBuilder brown()
        {
            return ItemStackBuilder.forColored(this.baseId, "brown");
        }
        public ItemStackBuilder green()
        {
            return ItemStackBuilder.forColored(this.baseId, "green");
        }
        public ItemStackBuilder red()
        {
            return ItemStackBuilder.forColored(this.baseId, "red");
        }
        public ItemStackBuilder black()
        {
            return ItemStackBuilder.forColored(this.baseId, "black");
        }
    }
}
