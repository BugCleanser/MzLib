package mz.mzlib.demo.games.ticactoe;

import mz.mzlib.demo.Demo;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.inventory.Inventory;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.WindowSlotButton;
import mz.mzlib.minecraft.ui.window.WindowUIWindow;
import mz.mzlib.minecraft.window.UnionWindowType;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowSlot;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.compound.Compound;
import mz.mzlib.util.compound.CompoundOverride;
import mz.mzlib.util.wrapper.WrapConstructor;
import mz.mzlib.util.wrapper.WrapperCreator;
import mz.mzlib.util.wrapper.WrapperObject;

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
            if(!context.successful || !context.doExecute)
                return;
            UIStack.get(context.sender.castTo(EntityPlayer::create)).start(new UITictactoe());
        }));
    }
    
    @Override
    public void onUnload()
    {
        Demo.instance.command.removeChild(this.command);
    }
    
    public static class UITictactoe extends UIWindow
    {
        @Compound
        public interface WindowSlotReward extends WindowSlot
        {
            @WrapperCreator
            static WindowSlotReward create(Object wrapped)
            {
                return WrapperObject.create(WindowSlotReward.class, wrapped);
            }
            
            static WindowSlot newInstance(Inventory inventory, int index)
            {
                return newInstance(inventory, index, -1, -1);
            }
            static WindowSlot newInstance(Inventory inventory, int index, int x, int y)
            {
                return create(null).staticNewInstance(inventory, index, x, y);
            }
            @WrapConstructor
            WindowSlot staticNewInstance(Inventory inventory, int index, int x, int y);
            
            @CompoundOverride(parent=WindowSlot.class, method="canPlace")
            @Override
            default boolean canPlace(ItemStack itemStack)
            {
                return false;
            }
        }
        
        public static ItemStack PLAYER=new ItemStackBuilder("structure_void").setDisplayName(Text.literal("§b你的棋子")).build();
        public static ItemStack AI=new ItemStackBuilder("barrier").setDisplayName(Text.literal("§c对手的棋子")).build();
        
        public UITictactoe()
        {
            super(UnionWindowType.CRAFTING, 10);
            
            this.setSlot(0, WindowSlotReward::newInstance);
            for(int i = 0; i<9; i++)
                this.setSlot(1+i, WindowSlotButton::newInstance);
        }
        
        public boolean finished=false;
        
        public boolean checkWin()
        {
            if(!this.inventory.getItemStack(1+4).isEmpty())
            {
                if(this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1)) && this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+8)))
                    return true;
                if(this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+2)) && this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+6)))
                    return true;
            }
            for(int i=0; i<3; i++)
            {
                if(!this.inventory.getItemStack(1+i*3).isEmpty())
                {
                    if(this.inventory.getItemStack(1+i*3).equals(this.inventory.getItemStack(1+i*3+1)) && this.inventory.getItemStack(1+i*3).equals(this.inventory.getItemStack(1+i*3+2)))
                        return true;
                }
                if(!this.inventory.getItemStack(1+i).isEmpty())
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
            this.finished=false;
        }
        
        public void aiDrop()
        {
            // 能赢直接下
            for(int i=0; i<9; i++)
            {
                if(this.inventory.getItemStack(1+i).isEmpty())
                {
                    this.inventory.setItemStack(1+i, AI);
                    if(checkWin())
                        return;
                    this.inventory.setItemStack(1+i, ItemStack.empty());
                }
            }
            for(int i=0; i<9; i++)
            {
                if(this.inventory.getItemStack(1+i).isEmpty())
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
            if(this.inventory.getItemStack(1+4).isEmpty())
            {
                this.inventory.setItemStack(1+4, AI);
                return;
            }
            List<Integer> list=new ArrayList<>();
            for(int i=0; i<9; i++)
                list.add(i);
            Collections.shuffle(list);
            for(int i: list)
            {
                if(this.inventory.getItemStack(1+i).isEmpty())
                {
                    this.inventory.setItemStack(1+i, AI);
                    return;
                }
            }
        }
        
        public boolean isFull()
        {
            for(int i=0; i<9; i++)
            {
                if(this.inventory.getItemStack(1+i).isEmpty())
                    return false;
            }
            return true;
        }
        
        @Override
        public ItemStack onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, EntityPlayer player)
        {
            ItemStack result = super.onAction(window, index, data, actionType, player);
            if(this.inventory.getItemStack(0).isEmpty() && (this.finished || this.isFull()))
                this.restart();
            else if(actionType.equals(WindowActionType.click()) || actionType.equals(WindowActionType.shiftClick()))
                if(index>=1 && index<10 && this.inventory.getItemStack(index).isEmpty())
            {
                this.inventory.setItemStack(index, PLAYER);
                if(checkWin())
                {
                    this.finished=true;
                    this.inventory.setItemStack(0, new ItemStackBuilder("apple").build()); // reward
                }
                else
                {
                    aiDrop();
                    if(this.checkWin())
                        this.finished=true;
                }
            }
            return result;
        }
        
        @Override
        public Text getTitle(EntityPlayer player)
        {
            return Text.literal("Tictactoe");
        }
    }
}
