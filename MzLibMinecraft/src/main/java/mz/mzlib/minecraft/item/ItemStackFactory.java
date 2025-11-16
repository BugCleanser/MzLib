package mz.mzlib.minecraft.item;

import mz.mzlib.data.DataKey;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.RuntimeUtil;

import java.util.HashMap;
import java.util.Map;

public class ItemStackFactory implements Cloneable
{
    Item item;
    int amount = 1;
    Map<DataKey<ItemStack, ?, ?>, ?> data = new HashMap<>();

    public ItemStackFactory()
    {
    }
    protected ItemStackFactory(ItemStackFactory other)
    {
        this.item = other.item;
        this.amount = other.amount;
        this.data = new HashMap<>(other.data);
    }

    public ItemStackFactory item(Item value)
    {
        ItemStackFactory result = this.clone();
        result.item = value;
        return result;
    }
    public ItemStackFactory fromId(Identifier id)
    {
        return this.item(Item.fromId(id));
    }
    public ItemStackFactory fromId(String id)
    {
        return this.item(Item.fromId(id));
    }

    public V_1300 v_1300()
    {
        if(this instanceof V_1300)
            return (V_1300) this;
        return new V_1300(this);
    }

    public ItemStackFactory fromId(String idV_1300, int damageV_1300, String idV1300)
    {
        if(MinecraftPlatform.instance.getVersion() < 1300)
            return this.fromId(idV_1300).v_1300().damage(damageV_1300);
        else
            return this.fromId(idV1300);
    }

    public <T> ItemStackFactory data(DataKey<ItemStack, T, ?> key, T value)
    {
        ItemStackFactory result = this.clone();
        result.data.put(key, RuntimeUtil.cast(value));
        return result;
    }

    public ItemStackFactory playerHead()
    {
        return this.fromId("skull", 3, "player_head");
    }

    public Colored colored(String idV_1300, String baseIdV1300)
    {
        return new Colored(this, idV_1300, baseIdV1300);
    }
    public Colored colored(String baseId)
    {
        return new Colored(this, baseId);
    }

    public Colored dye()
    {
        return this.colored("dye").reversed();
    }
    public Colored wool()
    {
        return this.colored("wool");
    }
    public Colored stainedGlass()
    {
        return this.colored("stained_glass");
    }
    public Colored stainedGlassPane()
    {
        return this.colored("stained_glass_pane");
    }
    public Colored terracotta()
    {
        return this.colored("stained_hardened_clay", "terracotta");
    }
    public Colored carpet()
    {
        return this.colored("carpet");
    }
    public Colored banner()
    {
        return this.colored("banner").reversed();
    }
    public Colored shulkerBoxV1100()
    {
        return new ShulkerBoxV1100(this);
    }
    public Colored bedV1200()
    {
        return this.colored("bed");
    }
    public Colored concreteV1200()
    {
        return this.colored("concrete");
    }
    public Colored concretePowderV1200()
    {
        return this.colored("concrete_powder");
    }
    public Colored glazedTerracottaV1200()
    {
        return this.colored("glazed_terracotta");
    }

    public ItemStack build()
    {
        if(this.item == null)
            throw new IllegalStateException("Item is not set");
        ItemStack result = ItemStack.newInstance(this.item, this.amount);
        for(Map.Entry<DataKey<ItemStack, ?,?>, ?> entry : this.data.entrySet())
        {
            entry.getKey().set(result, RuntimeUtil.cast(entry.getValue()));
        }
        return result;
    }

    @Override
    public ItemStackFactory clone()
    {
        try
        {
            ItemStackFactory result = (ItemStackFactory) super.clone();
            result.data = new HashMap<>(result.data);
            return result;
        }
        catch (CloneNotSupportedException e)
        {
            throw new AssertionError(e);
        }
    }

    public static class V_1300 extends ItemStackFactory
    {
        int damage = 0;
        public V_1300(ItemStackFactory other)
        {
            super(other);
        }

        public V_1300 damage(int value)
        {
            V_1300 result = this.clone();
            result.damage = value;
            return result;
        }

        public ItemStack build()
        {
            ItemStack result = super.build();
            result.setDamageV_1300(this.damage);
            return result;
        }

        @Override
        public V_1300 clone()
        {
            return (V_1300) super.clone();
        }
    }

    public static class Colored
    {
        ItemStackFactory base;
        String idV_1300;
        String baseIdV1300;

        Map<String, Integer> damages = DAMAGES_DEFAULT;

        static Map<String, Integer> DAMAGES_DEFAULT;
        static Map<String, Integer> DAMAGES_REVERSED;
        static
        {
            DAMAGES_DEFAULT = new HashMap<>();
            DAMAGES_DEFAULT.put("white", 0);
            DAMAGES_DEFAULT.put("orange", 1);
            DAMAGES_DEFAULT.put("magenta", 2);
            DAMAGES_DEFAULT.put("light_blue", 3);
            DAMAGES_DEFAULT.put("yellow", 4);
            DAMAGES_DEFAULT.put("lime", 5);
            DAMAGES_DEFAULT.put("pink", 6);
            DAMAGES_DEFAULT.put("gray", 7);
            DAMAGES_DEFAULT.put("light_gray", 8);
            DAMAGES_DEFAULT.put("cyan", 9);
            DAMAGES_DEFAULT.put("purple", 10);
            DAMAGES_DEFAULT.put("blue", 11);
            DAMAGES_DEFAULT.put("brown", 12);
            DAMAGES_DEFAULT.put("green", 13);
            DAMAGES_DEFAULT.put("red", 14);
            DAMAGES_DEFAULT.put("black", 15);

            DAMAGES_REVERSED = new HashMap<>(DAMAGES_DEFAULT);
            DAMAGES_REVERSED.replaceAll((k, v) -> 15 - v);
        }

        public Colored(ItemStackFactory base, String idV_1300, String baseIdV1300)
        {
            this.base = base;
            this.idV_1300 = idV_1300;
            this.baseIdV1300 = baseIdV1300;
        }
        public Colored(ItemStackFactory base, String baseId)
        {
            this(base, baseId, baseId);
        }

        public Colored reversed()
        {
            Colored result = new Colored(this.base, this.idV_1300, this.baseIdV1300);
            result.damages = DAMAGES_REVERSED;
            return result;
        }

        public ItemStackFactory color(String value)
        {
            if(MinecraftPlatform.instance.getVersion() < 1300)
                return this.base.fromId(this.idV_1300).v_1300().damage(this.damages.get(value));
            else
                return this.base.fromId(value + "_" + this.baseIdV1300);
        }

        public ItemStackFactory white()
        {
            return this.color("white");
        }
        public ItemStackFactory orange()
        {
            return this.color("orange");
        }
        public ItemStackFactory magenta()
        {
            return this.color("magenta");
        }
        public ItemStackFactory lightBlue()
        {
            return this.color("light_blue");
        }
        public ItemStackFactory yellow()
        {
            return this.color("yellow");
        }
        public ItemStackFactory lime()
        {
            return this.color("lime");
        }
        public ItemStackFactory pink()
        {
            return this.color("pink");
        }
        public ItemStackFactory gray()
        {
            return this.color("gray");
        }
        public ItemStackFactory lightGray()
        {
            return this.color("light_gray");
        }
        public ItemStackFactory cyan()
        {
            return this.color("cyan");
        }
        public ItemStackFactory purple()
        {
            return this.color("purple");
        }
        public ItemStackFactory blue()
        {
            return this.color("blue");
        }
        public ItemStackFactory brown()
        {
            return this.color("brown");
        }
        public ItemStackFactory green()
        {
            return this.color("green");
        }
        public ItemStackFactory red()
        {
            return this.color("red");
        }
        public ItemStackFactory black()
        {
            return this.color("black");
        }
    }

    public static class ShulkerBoxV1100 extends Colored
    {
        public ShulkerBoxV1100(ItemStackFactory base)
        {
            super(base, "shulker_box");
        }

        @Override
        public ItemStackFactory color(String value)
        {
            if(MinecraftPlatform.instance.getVersion() < 1300 && "light_gray".equals(value))
                value = "silver";
            return this.base.fromId(value + "_" + this.baseIdV1300);
        }
    }
}
