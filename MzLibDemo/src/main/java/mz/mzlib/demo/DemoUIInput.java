package mz.mzlib.demo;

import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.window.UIWindowAnvilInput;
import mz.mzlib.module.MzModule;

public class DemoUIInput extends MzModule
{
    public static DemoUIInput instance = new DemoUIInput();
    
    @Override
    public void onLoad()
    {
        this.register(new ChildCommandRegistration(Demo.instance.command, new Command("input") //
                .setPermissionChecker(Command::checkPermissionSenderPlayer) //
                .setHandler(context->
                {
                    if(context.getArgsReader().hasNext())
                        context.successful = false;
                    if(!context.successful)
                        return;
                    if(!context.doExecute)
                        return;
                    UIWindowAnvilInput.invoke(context.getSource().getPlayer(), "", Text.literal("Title")).thenAccept(text->context.getSource().sendMessage(Text.literal(text)));
                })));
    }
}
