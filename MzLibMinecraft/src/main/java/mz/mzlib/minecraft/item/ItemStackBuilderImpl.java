package mz.mzlib.minecraft.item;

import mz.mzlib.data.DataKey;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.util.RuntimeUtil;

import java.util.HashMap;
import java.util.Map;

class ItemStackBuilderImpl implements ItemStack.Builder
{
    Item item;
    int amount = 1;
    Map<DataKey<ItemStack, ?, ?>, ?> data = new HashMap<>();

    @Override
    public ItemStackBuilderImpl item(Item value)
    {
        this.item = value;
        return this;
    }
    @Override
    public ItemStackBuilderImpl fromId(Identifier id)
    {
        return this.item(Item.fromId(id));
    }
    @Override
    public ItemStackBuilderImpl fromId(String id)
    {
        return this.item(Item.fromId(id));
    }

    @Override
    public ItemStackBuilderImpl damageV_1300(int value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStackBuilderImpl fromId(String idV_1300, int damageV_1300, String idV1300)
    {
        return this.fromId(idV1300);
    }

    @Override
    public <T> ItemStackBuilderImpl data(DataKey<ItemStack, T, ?> key, T value)
    {
        this.data.put(key, RuntimeUtil.cast(value));
        return this;
    }

    @Override
    public ItemStackBuilderImpl playerHead()
    {
        return this.fromId("skull", 3, "player_head");
    }

    @Override
    public StepColor colored(String idV_1300, String baseIdV1300)
    {
        return new StepColor(this, idV_1300, baseIdV1300);
    }
    @Override
    public StepColor colored(String baseId)
    {
        return new StepColor(this, baseId);
    }

    @Override
    public StepColor dye()
    {
        return this.colored("dye").reversed();
    }
    @Override
    public StepColor wool()
    {
        return this.colored("wool");
    }
    @Override
    public StepColor stainedGlass()
    {
        return this.colored("stained_glass");
    }
    @Override
    public StepColor stainedGlassPane()
    {
        return this.colored("stained_glass_pane");
    }
    @Override
    public StepColor terracotta()
    {
        return this.colored("stained_hardened_clay", "terracotta");
    }
    @Override
    public StepColor carpet()
    {
        return this.colored("carpet");
    }
    @Override
    public StepColor banner()
    {
        return this.colored("banner").reversed();
    }
    @Override
    public StepColor shulkerBoxV1100()
    {
        return new ShulkerBoxV1100(this);
    }
    @Override
    public StepColor bedV1200()
    {
        return this.colored("bed");
    }
    @Override
    public StepColor concreteV1200()
    {
        return this.colored("concrete");
    }
    @Override
    public StepColor concretePowderV1200()
    {
        return this.colored("concrete_powder");
    }
    @Override
    public StepColor glazedTerracottaV1200()
    {
        return this.colored("glazed_terracotta");
    }

    @Override
    public ItemStack build()
    {
        if(this.item == null)
            throw new IllegalStateException("item is not set");
        ItemStack result = ItemStack.newInstance(this.item, this.amount);
        for(Map.Entry<DataKey<ItemStack, ?,?>, ?> entry : this.data.entrySet())
        {
            entry.getKey().set(result, RuntimeUtil.cast(entry.getValue()));
        }
        return result;
    }

    static class V_1300 extends ItemStackBuilderImpl
    {
        int damage = 0;

        @Override
        public ItemStackBuilderImpl damageV_1300(int value)
        {
            this.damage = value;
            return this;
        }

        @Override
        public ItemStackBuilderImpl fromId(String idV_1300, int damageV_1300, String idV1300)
        {
            return this.fromId(idV_1300).damageV_1300(damageV_1300);
        }

        @Override
        public ItemStack build()
        {
            ItemStack result = super.build();
            result.setDamageV_1300(this.damage);
            return result;
        }
    }

    static class StepColor implements ItemStack.Builder.StepColor
    {
        ItemStackBuilderImpl base;
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

        public StepColor(ItemStackBuilderImpl base, String idV_1300, String baseIdV1300)
        {
            this.base = base;
            this.idV_1300 = idV_1300;
            this.baseIdV1300 = baseIdV1300;
        }
        public StepColor(ItemStackBuilderImpl base, String baseId)
        {
            this(base, baseId, baseId);
        }

        @Override
        public StepColor reversed()
        {
            StepColor result = new StepColor(this.base, this.idV_1300, this.baseIdV1300);
            result.damages = DAMAGES_REVERSED;
            return result;
        }

        @Override
        public ItemStackBuilderImpl color(String value)
        {
            return this.base.fromId(this.idV_1300, this.damages.get(value), value + "_" + this.baseIdV1300);
        }
    }

    static class ShulkerBoxV1100 extends StepColor
    {
        public ShulkerBoxV1100(ItemStackBuilderImpl base)
        {
            super(base, "shulker_box");
        }

        @Override
        public ItemStackBuilderImpl color(String value)
        {
            if(MinecraftPlatform.instance.getVersion() < 1300 && "light_gray".equals(value))
                value = "silver";
            return this.base.fromId(value + "_" + this.baseIdV1300);
        }
    }
}
