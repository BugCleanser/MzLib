package mz.mzlib.demo.game.tictactoe;

import mz.mzlib.demo.Demo;
import mz.mzlib.minecraft.MinecraftPlatform;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.i18n.MinecraftI18n;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.WindowSlotButton;
import mz.mzlib.minecraft.ui.window.WindowUIWindow;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowSlotOutput;
import mz.mzlib.minecraft.window.WindowType;
import mz.mzlib.module.MzModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tictactoe extends MzModule
{
    public static Tictactoe instance = new Tictactoe();
    
    public Command command;
    
    @Override
    public void onLoad()
    {
        Demo.instance.command.addChild(this.command = new Command("tictactoe").setPermissionChecker(Command::checkPermissionSenderPlayer).setHandler(context->
        {
            if(context.argsReader.hasNext())
                context.successful = false;
            if(!context.successful || !context.doExecute)
                return;
            UIStack.get(context.getSource().getPlayer().unwrap()).start(new UITictactoe(context.getSource().getPlayer().unwrap()));
        }));
    }
    
    @Override
    public void onUnload()
    {
        Demo.instance.command.removeChild(this.command);
    }
    
    public static class UITictactoe extends UIWindow
    {
        public ItemStack PLAYER;
        public ItemStack AI;
        
        public UITictactoe(EntityPlayer player)
        {
            super(WindowType.CRAFTING, 10);
            
            PLAYER = new ItemStackBuilder(MinecraftPlatform.instance.getVersion()<1000 ? "ender_eye" : "structure_void").setCustomName(MinecraftI18n.resolveText(player.getLanguage(), "mzlibdemo.game.tictactoe.piece.player")).get();
            AI = new ItemStackBuilder("barrier").setCustomName(MinecraftI18n.resolveText(player.getLanguage(), "mzlibdemo.game.tictactoe.piece.ai")).get();
            
            this.putSlot(0, WindowSlotOutput::newInstance);
            for(int i = 0; i<9; i++)
                this.putSlot(1+i, WindowSlotButton::newInstance);
        }
        
        public boolean finished = false;
        
        public boolean checkWin()
        {
            if(!ItemStack.isEmpty(this.inventory.getItemStack(1+4)))
            {
                if(this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1)) && this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+8)))
                    return true;
                if(this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+2)) && this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+6)))
                    return true;
            }
            for(int i = 0; i<3; i++)
            {
                if(!ItemStack.isEmpty(this.inventory.getItemStack(1+i*3)))
                {
                    if(this.inventory.getItemStack(1+i*3).equals(this.inventory.getItemStack(1+i*3+1)) && this.inventory.getItemStack(1+i*3).equals(this.inventory.getItemStack(1+i*3+2)))
                        return true;
                }
                if(!ItemStack.isEmpty(this.inventory.getItemStack(1+i)))
                {
                    if(this.inventory.getItemStack(1+i).equals(this.inventory.getItemStack(1+3+i)) && this.inventory.getItemStack(1+i).equals(this.inventory.getItemStack(1+6+i)))
                        return true;
                }
            }
            return false;
        }
        
        public void restart()
        {
            this.inventory.clear();
            this.finished = false;
        }
        
        public void aiDrop()
        {
            // 能赢直接下
            for(int i = 0; i<9; i++)
            {
                if(ItemStack.isEmpty(this.inventory.getItemStack(1+i)))
                {
                    this.inventory.setItemStack(1+i, AI);
                    if(checkWin())
                        return;
                    this.inventory.setItemStack(1+i, ItemStack.empty());
                }
            }
            for(int i = 0; i<9; i++)
            {
                if(ItemStack.isEmpty(this.inventory.getItemStack(1+i)))
                {
                    this.inventory.setItemStack(1+i, PLAYER);
                    if(checkWin())
                    {
                        this.inventory.setItemStack(1+i, AI);
                        return;
                    }
                    this.inventory.setItemStack(1+i, ItemStack.empty());
                }
            }
            // 抢中心位
            if(ItemStack.isEmpty(this.inventory.getItemStack(1+4)))
            {
                this.inventory.setItemStack(1+4, AI);
                return;
            }
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i<9; i++)
                list.add(i);
            Collections.shuffle(list);
            for(int i: list)
            {
                if(ItemStack.isEmpty(this.inventory.getItemStack(1+i)))
                {
                    this.inventory.setItemStack(1+i, AI);
                    return;
                }
            }
        }
        
        public boolean isFull()
        {
            for(int i = 0; i<9; i++)
            {
                if(ItemStack.isEmpty(this.inventory.getItemStack(1+i)))
                    return false;
            }
            return true;
        }
        
        @Override
        public void onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, EntityPlayer player)
        {
            super.onAction(window, index, data, actionType, player);
            if(ItemStack.isEmpty(this.inventory.getItemStack(0)) && (this.finished || this.isFull()))
            {
                this.restart();
                if(index>=0 && index<this.inventory.size())
                    window.sendSlotUpdate(player, index);
            }
            else if(!this.finished && (actionType.equals(WindowActionType.click()) || actionType.equals(WindowActionType.shiftClick())) && index>=1 && index<10 && ItemStack.isEmpty(this.inventory.getItemStack(index)))
            {
                this.inventory.setItemStack(index, PLAYER);
                if(checkWin())
                {
                    this.finished = true;
                    this.inventory.setItemStack(0, ItemStack.copy((ItemStack)Demo.instance.config.get("game.tictactoe.reward")));
                    player.closeInterface();
                    this.open(player);
                }
                else
                {
                    aiDrop();
                    if(this.checkWin())
                        this.finished = true;
                }
            }
        }
        
        @Override
        public Text getTitle(EntityPlayer player)
        {
            return MinecraftI18n.resolveText(player.getLanguage(), "mzlibdemo.game.tictactoe.title");
        }
    }
}
