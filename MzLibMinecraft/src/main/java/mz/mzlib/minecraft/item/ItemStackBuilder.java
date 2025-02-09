package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.text.Text;

import java.util.Arrays;

public class ItemStackBuilder
{
    public static final Colored DYE = new Colored("dye");
    public static final Colored WOOL = new Colored("wool");
    public static final Colored STAINED_GLASS = new Colored("stained_glass");
    public static final Colored STAINED_GLASS_PANE = new Colored("stained_glass_pane");
    public static final Colored TERRACOTTA = new Colored("stained_hardened_clay", "terracotta");
    public static final Colored CARPET = new Colored("carpet");
    public static final Colored BANNER = new Colored("banner");
    public static final Colored SHULKER_BOX_V1100 = new Colored("shulker_box");
    public static final Colored BED_V1200 = new Colored("bed");
    public static final Colored CONCRETE_V1200 = new Colored("concrete");
    public static final Colored CONCRETE_POWDER_V1200 = new Colored("concrete_powder");
    public static final Colored GLAZED_TERRACOTTA_V1200 = new Colored("glazed_terracotta");
    
    public static ItemStackBuilder forFlattening(String idV_1300, int damageV_1300, String idV1300)
    {
        if(MinecraftPlatform.instance.getVersion()<1300)
            return new ItemStackBuilder(idV_1300).setDamageV_1300(damageV_1300);
        else
            return new ItemStackBuilder(idV1300);
    }
    public static ItemStackBuilder forColored(String baseId, String color)
    {
        return forColored(baseId, baseId, color);
    }
    public static ItemStackBuilder forColored(String idV_1300, String baseIdV1300, String color)
    {
        if(MinecraftPlatform.instance.getVersion()<1300)
            return new ItemStackBuilder(idV_1300).setDamageV_1300(Item.damageForColorV_1300(color));
        else
            return new ItemStackBuilder(color+"_"+baseIdV1300);
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
        public String idV_1300;
        public String baseIdV1300;
        public Colored(String idV_1300, String baseIdV1300)
        {
            this.idV_1300 = idV_1300;
            this.baseIdV1300 = baseIdV1300;
        }
        public Colored(String baseId)
        {
            this(baseId, baseId);
        }
        
        public ItemStackBuilder white()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "white");
        }
        public ItemStackBuilder orange()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "orange");
        }
        public ItemStackBuilder magenta()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "magenta");
        }
        public ItemStackBuilder lightBlue()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "light_blue");
        }
        public ItemStackBuilder yellow()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "yellow");
        }
        public ItemStackBuilder lime()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "lime");
        }
        public ItemStackBuilder pink()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "pink");
        }
        public ItemStackBuilder gray()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "gray");
        }
        public ItemStackBuilder lightGray()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "light_gray");
        }
        public ItemStackBuilder cyan()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "cyan");
        }
        public ItemStackBuilder purple()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "purple");
        }
        public ItemStackBuilder blue()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "blue");
        }
        public ItemStackBuilder brown()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "brown");
        }
        public ItemStackBuilder green()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "green");
        }
        public ItemStackBuilder red()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "red");
        }
        public ItemStackBuilder black()
        {
            return ItemStackBuilder.forColored(this.idV_1300, this.baseIdV1300, "black");
        }
    }
}
