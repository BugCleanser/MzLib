package mz.mzlib.demo;

import mz.mzlib.MzLib;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.module.MzModule;

public class DemoReload extends MzModule
{
    public static DemoReload instance = new DemoReload();
    
    @Override
    public void onLoad()
    {
        this.register(new ChildCommandRegistration(Demo.instance.command, new Command("reload").setHandler(context->
        {
            if(context.getArgsReader().hasNext())
                context.successful = false;
            if(!context.successful)
                return;
            if(!context.doExecute)
                return;
            MzLib.instance.unregister(Demo.instance);
            MzLib.instance.register(Demo.instance);
        })));
    }
}
