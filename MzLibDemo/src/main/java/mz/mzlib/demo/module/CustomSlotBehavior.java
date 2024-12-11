package mz.mzlib.demo.module;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.command.CommandBuilder;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventoryCustom;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.window.WindowFactorySimple;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

public class CustomSlotBehavior extends MzModule {
    public static CustomSlotBehavior instance = new CustomSlotBehavior();

    @Override
    public void onLoad() {
        InventoryCustom testInv = InventoryCustom.newInstance(9*3);
        testInv.setItemStack(1, ItemStack.newInstance(Item.fromId(Identifier.ofMinecraft("stick"))));
        WindowFactorySimple test = WindowFactorySimple.generic9x(Text.literal("test title"), testInv, 3, window->window.getSlots().set(0, TestSlot.newInstance(testInv, 0)));
        this.register(new CommandBuilder("mzlibdemo", "mzd").addChild(new CommandBuilder("test", "t").addExecutor((sender, command, args)->
        {
            if(sender.isInstanceOf(EntityPlayer::create))
                sender.castTo(EntityPlayer::create).openWindow(test);
            return Text.literal("Hello World!");
        }).build()).build());
    }

    @Compound
    public interface TestSlot extends WindowSlot
    {
        @WrapperCreator
        static TestSlot create(Object wrapped)
        {
            return WrapperObject.create(TestSlot.class, wrapped);
        }

        @WrapConstructor
        WindowSlot staticNewInstance(Inventory inventory, int index, int x, int y);
        static WindowSlot newInstance(Inventory inventory, int index)
        {
            return create(null).staticNewInstance(inventory, index, 0, 0);
        }

        @CompoundOverride("canPlace")
        default boolean canPlace(ItemStack itemStack)
        {
            return itemStack.getCount()%2==0;
        }

        @CompoundOverride("onTake")
        default void onTake(AbstractEntityPlayer player, ItemStack itemStack)
        {
            player.sendMessage(Text.literal(itemStack.encode().toString()));
        }
    }
}
