package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.WindowUIWindow;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.minecraft.window.WindowType;
import mz.mzlib.module.MzModule;

public class Inventory10Slots extends MzModule
{
    public static Inventory10Slots instance = new Inventory10Slots();
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        UIInventory10Slots ui = new UIInventory10Slots();
        Demo.instance.command.addChild(this.command = new Command("inventory10slots").setPermissionChecker(Command::checkPermissionSenderPlayer).setHandler(context->
        {
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful)
                return;
            if(context.doExecute)
            {
                ui.open(context.getSource().getPlayer().unwrap());
            }
        }));
    }
    
    @Override
    public void onUnload()
    {
        Demo.instance.command.removeChild(this.command);
    }
    
    public static class UIInventory10Slots extends UIWindow
    {
        public UIInventory10Slots()
        {
            super(WindowType.CRAFTING, 10);
        }
        
        @Override
        public ItemStack quickMove(WindowUIWindow window, EntityPlayer player, int index)
        {
            WindowSlot slot = window.getSlot(index);
            ItemStack is;
            if(!slot.isPresent() || ItemStack.isEmpty(is = slot.getItemStack()))
                return ItemStack.empty();
            ItemStack copy = ItemStack.copy(is);
            ItemStack result = ItemStack.empty();
            int upperSize = window.getSlots().size()-36;
            if(index<upperSize)
            {
                if(window.placeIn(is, upperSize, window.getSlots().size(), index==0))
                    result = copy;
            }
            else
            {
                if(window.placeIn(is, 1, upperSize, false))
                    result = copy;
                if(window.placeIn(is, 0, 1, false))
                    result = copy;
            }
            if(!ItemStack.isEmpty(result))
            {
                if(ItemStack.isEmpty(is))
                    slot.setItemStackByPlayer(ItemStack.empty());
                else
                    slot.markDirty();
            }
            return result;
        }
        
        @Override
        public void onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, EntityPlayer player)
        {
            super.onAction(window, index, data, actionType, player);
            window.sendSlotUpdate(player, 0);
        }
        
        @Override
        public Text getTitle(EntityPlayer player)
        {
            return MinecraftI18n.resolveText(player, "mzlibdemo.inventory10slots.title");
        }
    }
}
