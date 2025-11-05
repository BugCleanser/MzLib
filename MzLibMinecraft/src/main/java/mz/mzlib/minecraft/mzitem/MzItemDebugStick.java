package mz.mzlib.minecraft.mzitem;

import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MzLibMinecraft;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.entity.player.Hand;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.text.Text;

@MzItemClass
public interface MzItemDebugStick extends MzItem, MzItemUsable
{
    @Override
    default ItemStack static$vanilla()
    {
        return ItemStack.newInstance(Item.fromId(Identifier.minecraft("stick")));
    }
    
    @Override
    default Identifier static$getMzId()
    {
        return Identifier.newInstance(MzLibMinecraft.instance.MOD_ID, "debug_stick");
    }
    
    @Override
    default boolean use(EntityPlayer player, Hand hand)
    {
        player.sendMessage(Text.literal("pong"));
        return true;
    }
}
