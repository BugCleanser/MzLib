package mz.mzlib.demo;

import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.entity.player.EntityPlayer;
import mz.mzlib.minecraft.item.ItemStackBuilder;
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
                player.give(ItemStackBuilder.playerHead(
                        "http://textures.minecraft.net/texture/dddacc418df7e30db188be7f3865495b2c8f7c9963bd9e1b9ed8d28d45cf3460")
                    .get());
                player.give(ItemStackBuilder.playerHead(
                        "http://textures.minecraft.net/texture/37eeb49335e03fe7911295075d6ae1e8a9b091fc6c4896fd2ccec8359c10b006")
                    .get());
            })
        ));
    }
}
