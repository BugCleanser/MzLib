package mz.mzlib.minecraft.item;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.authlib.GameProfile;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.util.InvertibleMap;
import mz.mzlib.util.Option;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class ItemStackBuilder
{
    public static final Colored DYE = new ColoredReversed("dye");
    public static final Colored WOOL = new Colored("wool");
    public static final Colored STAINED_GLASS = new Colored("stained_glass");
    public static final Colored STAINED_GLASS_PANE = new Colored("stained_glass_pane");
    public static final Colored TERRACOTTA = new Colored("stained_hardened_clay", "terracotta");
    public static final Colored CARPET = new Colored("carpet");
    public static final Colored BANNER = new ColoredReversed("banner");
    public static final Colored SHULKER_BOX_V1100 = new ColoredShulkerBox();
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
    
    public static ItemStackBuilder playerSkull0(UUID uuid, String textures)
    {
        ItemStackBuilder result = forFlattening("skull", 3, "player_head");
        GameProfile owner = GameProfile.newInstance(Option.some(uuid), Option.none());
        owner.getProperties().put("textures", textures);
        ItemSkull.setOwner(result.result, owner);
        return result;
    }
    public static ItemStackBuilder playerSkull0(String textures)
    {
        return playerSkull0(UUID.nameUUIDFromBytes(textures.getBytes(StandardCharsets.UTF_8)), textures);
    }
    public static ItemStackBuilder playerSkull(UUID uuid, String url)
    {
        return playerSkull0(uuid, ItemSkull.texturesFromUrl(url));
    }
    public static ItemStackBuilder playerSkull(String url)
    {
        return playerSkull0(ItemSkull.texturesFromUrl(url));
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
    
    public ItemStackBuilder copy()
    {
        return new ItemStackBuilder(ItemStack.copy(this.result));
    }
    
    public static class Colored
    {
        InvertibleMap<String, Integer> colorDamages;
        public String idV_1300;
        public String baseIdV1300;
        public Colored(String idV_1300, String baseIdV1300)
        {
            this.idV_1300 = idV_1300;
            this.baseIdV1300 = baseIdV1300;
            colorDamages = new InvertibleMap<>();
            this.colorDamages.put("white", 0);
            this.colorDamages.put("orange", 1);
            this.colorDamages.put("magenta", 2);
            this.colorDamages.put("light_blue", 3);
            this.colorDamages.put("yellow", 4);
            this.colorDamages.put("lime", 5);
            this.colorDamages.put("pink", 6);
            this.colorDamages.put("gray", 7);
            this.colorDamages.put("light_gray", 8);
            this.colorDamages.put("cyan", 9);
            this.colorDamages.put("purple", 10);
            this.colorDamages.put("blue", 11);
            this.colorDamages.put("brown", 12);
            this.colorDamages.put("green", 13);
            this.colorDamages.put("red", 14);
            this.colorDamages.put("black", 15);
        }
        public Colored(String baseId)
        {
            this(baseId, baseId);
        }
        
        public ItemStackBuilder build(String color)
        {
            if(MinecraftPlatform.instance.getVersion()<1300)
                return new ItemStackBuilder(this.idV_1300).setDamageV_1300(this.colorDamages.get(color));
            else
                return new ItemStackBuilder(color+"_"+this.baseIdV1300);
        }
        public ItemStackBuilder white()
        {
            return this.build("white");
        }
        public ItemStackBuilder orange()
        {
            return this.build("orange");
        }
        public ItemStackBuilder magenta()
        {
            return this.build("magenta");
        }
        public ItemStackBuilder lightBlue()
        {
            return this.build("light_blue");
        }
        public ItemStackBuilder yellow()
        {
            return this.build("yellow");
        }
        public ItemStackBuilder lime()
        {
            return this.build("lime");
        }
        public ItemStackBuilder pink()
        {
            return this.build("pink");
        }
        public ItemStackBuilder gray()
        {
            return this.build("gray");
        }
        public ItemStackBuilder lightGray()
        {
            return this.build("light_gray");
        }
        public ItemStackBuilder cyan()
        {
            return this.build("cyan");
        }
        public ItemStackBuilder purple()
        {
            return this.build("purple");
        }
        public ItemStackBuilder blue()
        {
            return this.build("blue");
        }
        public ItemStackBuilder brown()
        {
            return this.build("brown");
        }
        public ItemStackBuilder green()
        {
            return this.build("green");
        }
        public ItemStackBuilder red()
        {
            return this.build("red");
        }
        public ItemStackBuilder black()
        {
            return this.build("black");
        }
    }
    
    public static class ColoredReversed extends Colored
    {
        public ColoredReversed(String idV_1300, String baseIdV1300)
        {
            super(idV_1300, baseIdV1300);
            this.colorDamages.replaceAll((k, v)->15-v);
        }
        public ColoredReversed(String baseId)
        {
            this(baseId, baseId);
        }
    }
    
    public static class ColoredShulkerBox extends Colored
    {
        public ColoredShulkerBox()
        {
            super("shulker_box");
        }
        @Override
        public ItemStackBuilder build(String color)
        {
            return new ItemStackBuilder(color+"_"+this.baseIdV1300);
        }
        @Override
        public ItemStackBuilder lightGray()
        {
            if(MinecraftPlatform.instance.getVersion()<1300)
                return this.build("silver");
            else
                return super.lightGray();
        }
    }
}
