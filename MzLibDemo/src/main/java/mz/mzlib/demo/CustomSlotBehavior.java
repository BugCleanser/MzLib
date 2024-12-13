package mz.mzlib.demo;

import mz.mzlib.minecraft.command.CommandBuilder;
import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.inventory.InventorySimple;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtByte;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtShort;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.WindowUIWindow;
import mz.mzlib.minecraft.window.UnionWindowType;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowFactorySimple;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

public class CustomSlotBehavior extends MzModule
{
    public static CustomSlotBehavior instance = new CustomSlotBehavior();
    
    @Override
    public void onLoad()
    {
        TestWindow test = new TestWindow();
        this.register(new CommandBuilder("mzlibdemo", "mzd").addChild(new CommandBuilder("test", "t").addExecutor((sender, command, args)->
        {
            if(sender.isInstanceOf(EntityPlayer::create))
                test.open(sender.castTo(EntityPlayer::create));
            return Text.literal("Hello World!");
        }).build()).build());
    }
    
    public static class TestWindow extends UIWindow
    {
        public TestWindow()
        {
            super(UnionWindowType.GENERIC_9x2, 9*2);
            
            this.setSlot(0, TestSlot::newInstance);
        }
        
        @Override
        public ItemStack onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
        {
            player.sendMessage(Text.literal("Action "+index+" "+data+" "+actionType));
            return super.onAction(window, index, data, actionType, player);
        }
        
        @Override
        public Text getTitle(EntityPlayer player)
        {
            return Text.literal("Test title "+player);
        }
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
        
        @CompoundOverride(parent=WindowSlot.class, method="canPlace")
        default boolean canPlace(ItemStack itemStack)
        {
            return itemStack.getCount()%2==0;
        }
        
        @CompoundOverride(parent=WindowSlot.class, method="onTake")
        default void onTake(AbstractEntityPlayer player, ItemStack itemStack)
        {
            player.sendMessage(Text.literal(itemStack.encode().toString()));
        }
    }
}
