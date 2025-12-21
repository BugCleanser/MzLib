package mz.mzlib.demo;

import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.module.MzModule;

public class DemoTest extends MzModule
{
    public static DemoTest instance = new DemoTest();

    @Override
    public void onLoad()
    {
        this.register(new ChildCommandRegistration(
            Demo.instance.command,
            new Command("test").setPermissionChecker(Command::checkPermissionSenderPlayer).setHandler(context ->
            {
                if(!context.successful)
                    return;
                if(!context.doExecute)
                    return;
                EntityPlayer player = context.getSource().getPlayer().unwrap();
                player.give(ItemStack.builder().playerHead().texturesUrl("https://textures.minecraft.net/texture/dddacc418df7e30db188be7f3865495b2c8f7c9963bd9e1b9ed8d28d45cf3460").build());
            })
        ));
    }
}
