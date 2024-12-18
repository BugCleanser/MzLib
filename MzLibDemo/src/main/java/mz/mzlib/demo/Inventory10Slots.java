package mz.mzlib.demo;

import mz.mzlib.minecraft.entity.player.AbstractEntityPlayer;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.nbt.NbtInt;
import mz.mzlib.minecraft.nbt.NbtString;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.WindowUIWindow;
import mz.mzlib.minecraft.window.UnionWindowType;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.module.MzModule;

public class Inventory10Slots extends MzModule
{
    public static Inventory10Slots instance = new Inventory10Slots();
    
    @Override
    public void onLoad()
    {
        Inventory10SlotsUI ui = new Inventory10SlotsUI();
        Demo.instance.commandDemo.addChild(new CommandBuilder("inventory10slots").addExecutor((sender, command, args)->
        {
            if(sender.isInstanceOf(EntityPlayer::create))
                ui.open(sender.castTo(EntityPlayer::create));
            return null;
        }).build());
    }
    
    public static class Inventory10SlotsUI extends UIWindow
    {
        public Inventory10SlotsUI()
        {
            super(UnionWindowType.CRAFTING, 10);
            NbtCompound item = NbtCompound.newInstance();
            item.put("id", NbtString.newInstance("minecraft:nether_star"));
            item.put("count", NbtInt.newInstance(1));
            NbtCompound components=NbtCompound.newInstance();
            item.put("components",components);
            NbtCompound enchantments=NbtCompound.newInstance();
            components.put("minecraft:enchantments", enchantments);
            NbtCompound levels=NbtCompound.newInstance();
            enchantments.put("levels", levels);
            levels.put("minecraft:smite", NbtInt.newInstance(5));
            this.inventory.setItemStack(0, ItemStack.decode(item));
        }
        
        @Override
        public ItemStack quickMove(WindowUIWindow window, AbstractEntityPlayer player, int index)
        {
            WindowSlot slot = window.getSlot(index);
            if(!slot.isPresent() || slot.getItemStack().isEmpty())
                return ItemStack.empty();
            ItemStack is = slot.getItemStack();
            ItemStack result = is.copy();
            int upperSize = window.getSlots().size()-36;
            if(index<upperSize)
            {
                if(!window.placeIn(is, upperSize, window.getSlots().size(), index==0))
                    result = ItemStack.empty();
            }
            else
            {
                if(!window.placeIn(is, 1, upperSize, false))
                    result = ItemStack.empty();
                if(!window.placeIn(is, 0, 1, false))
                    result = ItemStack.empty();
            }
            if(!result.isEmpty())
            {
                if(is.isEmpty())
                    slot.setItemStackByPlayer(ItemStack.empty());
                else
                    slot.markDirty();
            }
            return result;
        }
        
        @Override
        public ItemStack onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, AbstractEntityPlayer player)
        {
            player.sendMessage(Text.literal("Action "+actionType+" "+actionType.getWrapped()));
            return super.onAction(window, index, data, actionType, player);
        }
        @Override
        public Text getTitle(AbstractEntityPlayer player)
        {
            return Text.literal("10个格子的物品栏");
        }
    }
}
