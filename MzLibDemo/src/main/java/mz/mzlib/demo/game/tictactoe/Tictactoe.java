package mz.mzlib.demo.game.tictactoe;

import mz.mzlib.demo.Demo;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.item.ItemStackBuilder;
import mz.mzlib.minecraft.nbt.NbtCompound;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UIStack;
import mz.mzlib.minecraft.ui.window.UIWindow;
import mz.mzlib.minecraft.ui.window.WindowSlotButton;
import mz.mzlib.minecraft.ui.window.WindowUIWindow;
import mz.mzlib.minecraft.window.UnionWindowType;
import mz.mzlib.minecraft.window.WindowActionType;
import mz.mzlib.minecraft.window.WindowSlotOutput;
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
            UIStack.get(context.sender.castTo(EntityPlayer::create)).start(new UITictactoe(context.sender.castTo(EntityPlayer::create)));
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
            super(UnionWindowType.CRAFTING, 10);
            
            PLAYER = new ItemStackBuilder("structure_void").setDisplayName(Text.literal(I18n.getTranslation(player.getLanguage(), "mzlibdemo.game.tictactoe.piece.player"))).build();
            AI = new ItemStackBuilder("barrier").setDisplayName(Text.literal(I18n.getTranslation(player.getLanguage(), "mzlibdemo.game.tictactoe.piece.ai"))).build();
            
            this.putSlot(0, WindowSlotOutput::newInstance);
            for(int i = 0; i<9; i++)
                this.putSlot(1+i, WindowSlotButton::newInstance);
        }
        
        public boolean finished = false;
        
        public boolean checkWin()
        {
            if(!this.inventory.getItemStack(1+4).isEmpty())
            {
                if(this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1)) && this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+8)))
                    return true;
                if(this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+2)) && this.inventory.getItemStack(1+4).equals(this.inventory.getItemStack(1+6)))
                    return true;
            }
            for(int i = 0; i<3; i++)
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
            this.finished = false;
        }
        
        public void aiDrop()
        {
            // 能赢直接下
            for(int i = 0; i<9; i++)
            {
                if(this.inventory.getItemStack(1+i).isEmpty())
                {
                    this.inventory.setItemStack(1+i, AI);
                    if(checkWin())
                        return;
                    this.inventory.setItemStack(1+i, ItemStack.empty());
                }
            }
            for(int i = 0; i<9; i++)
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
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i<9; i++)
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
            for(int i = 0; i<9; i++)
            {
                if(this.inventory.getItemStack(1+i).isEmpty())
                    return false;
            }
            return true;
        }
        
        @Override
        public void onAction(WindowUIWindow window, int index, int data, WindowActionType actionType, EntityPlayer player)
        {
            super.onAction(window, index, data, actionType, player);
            if(this.inventory.getItemStack(0).isEmpty() && (this.finished || this.isFull()))
            {
                this.restart();
                if(index>=0 && index<this.inventory.size())
                    window.sendSlotUpdate(player, index);
            }
            else if(!this.finished && (actionType.equals(WindowActionType.click()) || actionType.equals(WindowActionType.shiftClick())) && index>=1 && index<10 && this.inventory.getItemStack(index).isEmpty())
            {
                this.inventory.setItemStack(index, PLAYER);
                if(checkWin())
                {
                    this.finished = true;
                    this.inventory.setItemStack(0, ItemStack.decode(NbtCompound.parse(Demo.instance.config.getString("game.tictactoe.reward"))));
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
            return Text.literal(I18n.getTranslation(player.getLanguage(), "mzlibdemo.game.tictactoe.title"));
        }
    }
}
